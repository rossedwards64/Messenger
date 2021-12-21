package com.rossedwards.nsdassignment;

import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String username;
    private final Socket socket;
    PrintWriter writer;
    Scanner reader;
    BufferedReader in;

    public Client(Socket socket, String username) throws IOException {
        this.username = username;
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new Scanner(socket.getInputStream());
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setRequestLogin(String username) throws IOException {
        reader = new Scanner(username);
        Request request = new LoginRequest(reader.nextLine());
        processRequest(request);
    }

    public void setRequestPost(String message) throws IOException {
        reader = new Scanner(message);
        Request request = new PostRequest(reader.nextLine());
        processRequest(request);
    }

    public void setRequestRead() throws IOException {
        Request request = new ReadRequest();
        processRequest(request);
    }

    public void setRequestQuit() throws IOException {
        Request request = new QuitRequest();
        processRequest(request);
    }

    public void closeSocket() throws IOException {
        socket.close();
    }

    public void processRequest(Request request) throws IOException {
        writer.println(request);
        String serverResponse;
        if ((serverResponse = in.readLine()) == null)
            return;

        Object json = JSONValue.parse(serverResponse);
        Response response;

        if (SuccessResponse.fromJSON(json) == null)
            return;

        if ((response = MessageListResponse.fromJSON(json)) != null) {
            for (Message message : ((MessageListResponse) response).getMessages()) {
                System.out.println(message);
            }
        }

        if ((response = ErrorResponse.fromJSON(json)) != null) {
            System.out.println(((ErrorResponse) response).getError());
        }
    }
}
