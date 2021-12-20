package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public ToggleButton subscribeButton;
    public ToggleButton subscribeButton2;
    public ToggleButton subscribeButton3;
    public ToggleButton subscribeButton4;
    public ToggleButton subscribeButton5;
    public ToggleButton subscribeButton6;
    public Button generalChannelButton;
    public Button musicChannelButton;
    public Button softwareChannelButton;
    public Button gamingChannelButton;
    public Button beekeepingChannelButton;
    public Button literatureChannelButton;

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
        for (Message message : Server.ClientHandler.messageBoard) {
            display.appendText(message + "\n");
        }
    }

    @FXML
    protected void setUsernameLabelText() throws IOException {
        String username;
        if ((username = setUsernameField.getText()) != null) {
            Database.logIn(username);
            client.setRequestLogin(username);
            client.setUsername(username);
            usernameLabel.setText(username);
            setUsernameField.clear();
            display.appendText(client.getUsername() + " has entered the chat." + "\n");
        }
    }

    @FXML
    protected void subscribe() {
        if (subscribeButton.isSelected()) {
            subscribeButton.setText("Unsubscribe");
            display.appendText("Welcome to the General Channel." + "\n");
        } else {
            subscribeButton.setText("Subscribe");
            display.clear();
        }

        if (subscribeButton2.isSelected()) {
            subscribeButton2.setText("Unsubscribe");
            display.appendText("Welcome to the Music Channel." + "\n");
        } else {
            subscribeButton2.setText("Subscribe");
            display.clear();
        }

        if (subscribeButton3.isSelected()) {
            subscribeButton3.setText("Unsubscribe");
            display.appendText("Welcome to the Software Engineering Channel." + "\n");
        } else {
            subscribeButton3.setText("Subscribe");
            display.clear();
        }

        if (subscribeButton4.isSelected()) {
            subscribeButton4.setText("Unsubscribe");
            display.appendText("Welcome to the Gaming Channel." + "\n");
        } else {
            subscribeButton4.setText("Subscribe");
            display.clear();
        }

        if (subscribeButton5.isSelected()) {
            subscribeButton5.setText("Unsubscribe");
            display.appendText("Welcome to the Beekeeping Channel." + "\n");
        } else {
            subscribeButton5.setText("Subscribe");
            display.clear();
        }

        if (subscribeButton6.isSelected()) {
            subscribeButton6.setText("Unsubscribe");
            display.appendText("Welcome to the Literature Channel." + "\n");
        } else {
            subscribeButton6.setText("Subscribe");
            display.clear();
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