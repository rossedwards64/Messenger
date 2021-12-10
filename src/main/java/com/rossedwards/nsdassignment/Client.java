package com.rossedwards.nsdassignment;

import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Socket socket;
    PrintWriter out;
    BufferedReader reader;
    Request request;
    Response response;
    String serverResponse;
    Object json;

    Client() throws IOException {
        String hostName = "localhost";
        int portNumber = 12345;
        socket = new Socket(hostName, portNumber);
        out = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setRequestLogin() throws IOException {
        if((serverResponse = reader.readLine()) == null)
        request = new LoginRequest(reader.readLine());
        processRequest();
    }

    public void setRequestPost() throws IOException {
        request = new PostRequest(reader.readLine());
        processRequest();
    }

    public void setRequestRead() throws IOException {
        request = new ReadRequest();
        processRequest();
    }

    public void setRequestQuit() throws IOException {
        request = new QuitRequest();
        processRequest();
    }

    public void processRequest() throws IOException {
        out.println(request);
        if((serverResponse = reader.readLine()) == null)
            return;

        json = JSONValue.parse(serverResponse);

        if(SuccessResponse.fromJSON(json) == null)
            return;

        if((response = MessageListResponse.fromJSON(json)) != null) {
            for(Message message : ((MessageListResponse) response).getMessages())
                System.out.println(message);
        }

        if((response = ErrorResponse.fromJSON(json)) != null) {
            System.out.println(((ErrorResponse) response).getError());
        }
    }

//                String serverResponse;
//                if((serverResponse = in.readLine()) == null)
//                    break;
//
//                Object json = JSONValue.parse(serverResponse);
//                Response response;
//
//                if(SuccessResponse.fromJSON(json) != null)
//                    continue;
//
//                if((response = MessageListResponse.fromJSON(json)) != null) {
//                    for (Message message : ((MessageListResponse) response).getMessages())
//                        System.out.println(message);
//                    continue;
//                }
//
//                if((response = ErrorResponse.fromJSON(json)) != null) {
//                    System.out.println(((ErrorResponse) response).getError());
//                    continue;
//                }
//
//                // No response
//                System.out.println("PANIC: " + serverResponse + " parsed as " + json);

}
