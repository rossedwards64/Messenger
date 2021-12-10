package com.rossedwards.nsdassignment;

import org.json.simple.JSONValue;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static class Clock {
        private long time;

        public Clock() {
            time = 0;
        }

        public synchronized long getCurrentTime() {
            return ++time;
        }
    }

    static class ClientHandler extends Thread {
        private static final List<Message> messageBoard = new ArrayList<>();
        private static final Clock time = new Clock();
        // messages that have been opened are added to this counter
        private int read;
        // login credentials
        private String login;

        private Socket client;
        private final PrintWriter out;
        private final BufferedReader in;

        // class constructor to initialise socket and so client can send and receive messages
        public ClientHandler(Socket socket) throws IOException {
            client = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            read = 0;
            // set to null until a login account has been made
            login = null;
        }

        public void run() {
            try {
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    long currentTime = time.getCurrentTime();

                    if(login != null) {
                        System.out.println(login + ": " + inputLine);
                    } else {
                        System.out.println(inputLine);
                    }

                    Object json = JSONValue.parse(inputLine);
                    Request request;

                    // LOGIN REQUEST
                    // can only log in if not already logged in
                    if(login == null && (request = LoginRequest.fromJSON(json)) != null) {
                        login = ((LoginRequest)request).getUsername();
                        // let the user know the login request was received
                        out.println(new SuccessResponse());
                    }

                    // POST REQUEST
                    // can only make a post if they are already logged in
                    if(login != null && (request = PostRequest.fromJSON(json)) != null) {
                        String message = ((PostRequest)request).getMessage();
                        // make sure users can access message board concurrently
                        // synchronized to avoid deadlocks
                        synchronized (ClientHandler.class) {
                            // construct message
                            messageBoard.add(new Message(message, login, currentTime));
                        }
                        // let user know that the post request was received
                        out.println(new SuccessResponse());
                        continue;
                    }

                    // READ REQUEST
                    // can only read a message if they are already logged in
                    if(login != null && ReadRequest.fromJSON(json) != null) {
                        List<Message> messages;
                        synchronized (ClientHandler.class) {
                            messages = messageBoard.subList(read, messageBoard.size());
                        }
                        // set number of unread messages
                        read = messageBoard.size();
                        // respond with a list of unread messages
                        out.println(new MessageListResponse(messages));
                        continue;
                    }

                    // QUIT REQUEST
                    // can only quit if they are already logged in
                    if(login != null & QuitRequest.fromJSON(json) != null) {
                        in.close();
                        out.close();
                        return;
                    }

                    out.println(new ErrorResponse("ILLEGAL REQUEST"));
                }
            } catch (IOException e) {
                System.out.println("An error occurred. Client disconnected.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int portNumber = 12345;

        try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while(true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }

        } catch (IOException e) {
            System.out.println("Exception listening for connection on port " +
                    portNumber);
            e.printStackTrace();
        }
    }
}
