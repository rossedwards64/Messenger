package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    public Button quitButton;
    static Client client;
    static Socket socket;
    public TextField setUsernameField;
    public Label isConnectedLabel;
    public Button updateChat;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField sendMessageBox;

    @FXML
    protected void startClient() throws IOException {
        socket = new Socket("localhost", 12345);
        client = new Client(socket, "User");
    }

    @FXML
    protected void connectedState() {
        if(socket.isConnected()) {
            isConnectedLabel.setText("Connected.");
        } else {
            isConnectedLabel.setText("Disconnected.");
        }
    }

    @FXML
    protected void sendMessage() throws IOException {
        String message;
        if((message = sendMessageBox.getText()) != null) {
            client.writer.println(message);
            client.setRequestPost(message);
            display.appendText(client.getUsername() + ": " + message + "\n");
            sendMessageBox.clear();
        }
    }

    @FXML
    protected void readChat() throws IOException {
        client.setRequestRead();
    }

    @FXML
    protected void setUsernameLabelText() throws IOException {
        String username;
        if((username = setUsernameField.getText()) != null) {
            client.setRequestLogin(username);
            client.setUsername(username);
            usernameLabel.setText(username);
            setUsernameField.clear();
            display.appendText(client.getUsername() + " has entered the chat." + "\n");
        }
    }

    @FXML
    protected void quit() throws IOException {
        client.setRequestQuit();
        client.closeSocket();
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}