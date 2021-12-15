package com.rossedwards.nsdassignment;

import org.json.simple.JSONValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String username;
    private final Socket socket;
    PrintWriter writer;
    Scanner reader;
    BufferedReader in;
    BufferedReader stdIn;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new Scanner(socket.getInputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.stdIn = new BufferedReader(new InputStreamReader(System.in));
    }

    public void clientFunction(String userInput) {
        try {
            while((userInput = stdIn.readLine()) != null) {
                Request request;
                Scanner scan = new Scanner(userInput);
                try {
                    switch (scan.next()) {
                        // client features going to be made console based
                        // for testing until gui is ready to use
                        case "login":
                            request = new LoginRequest(scan.skip(" ").nextLine());
                            break;
                        case "post":
                            request = new PostRequest(scan.skip(" ").nextLine());
                            break;
                        case "read":
                            request = new ReadRequest();
                            break;
                        case "quit":
                            request = new QuitRequest();
                            break;
                        default:
                            System.out.println("ILLEGAL COMMAND");
                            continue;
                    }
                } catch(NoSuchElementException e) {
                    System.out.println("ILLEGAL COMMAND");
                    continue;
                }

                writer.println(request);

                // disconnects client if there is no response from the server
                String serverResponse;
                if((serverResponse = in.readLine()) == null)
                    break;

                Object json = JSONValue.parse(serverResponse);
                Response response;

                if(SuccessResponse.fromJSON(json) != null)
                    continue;

                if((response = MessageListResponse.fromJSON(json)) != null) {
                    for (Message message : ((MessageListResponse) response).getMessages())
                        System.out.println(message);
                    continue;
                }

                if((response = ErrorResponse.fromJSON(json)) != null) {
                    System.out.println(((ErrorResponse) response).getError());
                    continue;
                }

                // No response
                System.out.println("PANIC: " + serverResponse + " parsed as " + json);
            }
        } catch (IOException e) {
            System.err.println("Couldn't get input/output for the connection to " + socket.getInetAddress());
        }

    }
}
