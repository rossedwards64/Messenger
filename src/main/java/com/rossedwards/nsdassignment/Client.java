package com.rossedwards.nsdassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 12345;

        try(
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
                ) {
            String userInput;
            while((userInput = stdIn.readLine()) != null) {
                Request request;
                Scanner scan = new Scanner(userInput);
                try {
                    switch(scan.next()) {
                        // client features going to be made console based
                        // for testing until gui is ready to use
                        case "login":
                            break;
                        case "post":
                            break;
                        case "read":
                            break;
                        case "quit":
                            break;
                        default:
                            System.out.println("ILLEGAL COMMAND");
                            continue;
                    }
                } catch(NoSuchElementException e) {
                    System.out.println("ILLEGAL COMMAND");
                    continue;
                }

                //out.println(request);

                // disconnects client if there is not response from the server
                String serverResponse;
                if((serverResponse = in.readLine()) == null) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
