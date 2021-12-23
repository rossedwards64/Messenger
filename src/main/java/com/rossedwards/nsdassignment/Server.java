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

    // abstract all server responses to enable returning values
    static class ClientHandler extends Thread {
        private static final List<Message> messageBoard = new ArrayList<>();
        private static final List<Message> imageBoard = new ArrayList<>();
        private static final Clock time = new Clock();
        // messages that have been opened are added to this counter
        private int read;
        private final ObjectOutputStream objOut;
        // login credentials
        private String login;

        private final PrintWriter out;
        private int imagesRead;
        private final BufferedReader in;

        // class constructor to initialise socket and so client can send and receive messages
        private ClientHandler(Socket socket) throws IOException {
            out = new PrintWriter(socket.getOutputStream(), true);
            objOut = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            read = 0;
            // set to null until a login account has been made
            login = null;
        }

        private void loginResponse(Request request) {
            login = ((LoginRequest) request).getUsername();
            // let the user know the login request was received
            out.println(new SuccessResponse());
            out.flush();
        }

        private void postResponse(Request request, long currentTime) {
            String message = ((PostRequest) request).getMessage();
            // make sure users can access message board concurrently
            // synchronized to avoid deadlocks
            synchronized (ClientHandler.class) {
                // construct message
                Message sentMessage = new Message(message, login, currentTime);
                messageBoard.add(sentMessage);
                out.println(sentMessage + "\n");
            }
            // let user know that the post request was received
            out.println(new SuccessResponse());
            out.flush();
        }

        private void postImageResponse(Request request, long currentTime) {
            String image = ((PostImageRequest) request).getImage();
            synchronized (ClientHandler.class) {
                Message sentImage = new Message(image, login, currentTime);
                imageBoard.add(sentImage);
                out.println(sentImage);
            }

            out.println(new SuccessResponse());
            out.flush();
        }

        private void readResponse() {
            List<Message> messages;
            synchronized (ClientHandler.class) {
                messages = messageBoard.subList(read, messageBoard.size());
            }
            // set number of unread messages
            read = messageBoard.size();
            // respond with a list of unread messages
            out.println(new MessageListResponse(messages));
            out.flush();
        }

        private void readImageResponse() {
            List<Message> images;
            synchronized (ClientHandler.class) {
                images = imageBoard.subList(imagesRead, imageBoard.size());
            }
            imagesRead = imageBoard.size();
            out.println(new ImageListResponse(images));
            out.flush();
        }

        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    long currentTime = time.getCurrentTime();

                    if (login != null) {
                        System.out.println(login + ": " + inputLine);
                    } else {
                        System.out.println(inputLine);
                    }

                    Object json = JSONValue.parse(inputLine);
                    Request request;

                    // LOGIN REQUEST
                    // can only log in if not already logged in
                    if (login == null && (request = LoginRequest.fromJSON(json)) != null) {
                        loginResponse(request);
                        continue;
                    }

                    // POST REQUEST
                    // can only make a post if they are already logged in
                    if (login != null && (request = PostRequest.fromJSON(json)) != null) {
                        postResponse(request, currentTime);
                        continue;
                    }

                    if (login != null && (request = PostImageRequest.fromJSON(json)) != null) {
                        postImageResponse(request, currentTime);
                        continue;
                    }

                    // READ REQUEST
                    // can only read a message if they are already logged in
                    if (login != null && ReadRequest.fromJSON(json) != null) {
                        readResponse();
                        continue;
                    }

                    if (login != null && ReadImageRequest.fromJSON(json) != null) {
                        readImageResponse();
                        continue;
                    }

                    // QUIT REQUEST
                    // can only quit if they are already logged in
                    if (login != null & QuitRequest.fromJSON(json) != null) {
                        in.close();
                        out.close();
                        return;
                    }

                    out.println(new ErrorResponse("ILLEGAL REQUEST"));
                    out.flush();
                }
            } catch (IOException e) {
                System.err.println("Client disconnected.");
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
            System.err.println("Exception listening for connection on port " +
                    portNumber);
        }
    }
}
