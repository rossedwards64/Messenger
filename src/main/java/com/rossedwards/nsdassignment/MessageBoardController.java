package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.Socket;

public class MessageBoardController {

    // channels could be things like music, software engineering, gaming, etc.

    public Font x1;
    public Color x2;
    public Font x11;
    public Color x21;
    public Font x3;
    public Color x4;
    public TextArea display;
    public Button sendMessageButton;
    public Button setUsernameButton;
    static Client client;
    static Socket socket;

    @FXML
    private Label username;

    @FXML
    private TextField sendMessageBox;

    @FXML
    protected static void startClient() throws IOException {
        socket = new Socket("localhost", 12345);
        client = new Client(socket);
    }

    @FXML
    protected void sendMessage() {
        String message;
        if((message = sendMessageBox.getText()) != null) {
            client.writer.println(message);
            display.appendText(message + "\n");
            int n = client.reader.nextInt();
            sendMessageBox.setText("");

            for(int i = 0; i < n; i++) {
                display.appendText(client.reader.nextLine() + "\n");
            }
        }
    }

    @FXML
    protected void setUsernameLabelText() {

    }
}