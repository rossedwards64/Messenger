package com.rossedwards.nsdassignment;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static class Clock {
        private long time;

        public synchronized long tick() {
            return ++time;
        }
    }

    static class ClientHandler extends Thread {
        private static List<Message> messageBoard = new ArrayList<>();
        private static Clock time = new Clock();
        // messages that have been opened are added to this counter
        private int read;
        // login credentials
        private String login;

        private Socket client;
        private PrintWriter out;
        private BufferedReader in;

        // class constructor to initialise socket and so client can send and receive messages
        public ClientHandler(Socket socket) throws IOException {
            client = socket;
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            read = 0;
            // set to null until a login account has been made
            login = null;
        }

        public void run() {
            try {
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    long currentTime = time.tick();

                    if(login != null) {
                        System.out.println(login + ": " + inputLine);
                    } else {
                        System.out.println(inputLine);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred. Client disconnected.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int portNumber = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

        } catch (IOException e) {
            System.out.println("Exception listening for connection on port " +
                    portNumber);
            e.printStackTrace();
        }
    }
}
