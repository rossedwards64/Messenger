package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    public ToggleGroup channelGroup;
    public TextField sendImageBox;
    public Button sendMessageButton1;
    public Button updateChat1;
    public ImageView imageView;
    public ToggleButton generalButton;
    public ToggleButton musicButton;
    public ToggleButton softwareButton;
    public ToggleButton gamingButton;
    public ToggleButton beeButton;
    public ToggleButton litButton;

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
        if (socket.isConnected()) {
            isConnectedLabel.setText("Connected.");
        } else {
            isConnectedLabel.setText("Disconnected.");
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
    protected void sendMessage() throws IOException {
        String message;
        if ((message = sendMessageBox.getText()) != null) {
            client.setRequestPost(message);
            client.writer.println(message);
            display.appendText(client.getUsername() + ": " + message + "\n");
            sendMessageBox.clear();
        }
    }

    @FXML
    protected void readChat() throws IOException {
        client.setRequestRead();
        for (Message message : Server.ClientHandler.getMessageBoard()) {
            display.appendText(message + "\n");
        }
    }


    @FXML
    protected void subscribeGeneral() {
        if (subscribeButton.isSelected()) {
            display.appendText("Subscribed to the general channel." + "\n");
            subscribeButton.setText("Unsubscribe");
        } else {
            display.appendText("Unsubscribed from the general channel." + "\n");
            subscribeButton.setText("Subscribe");
        }
    }

    @FXML
    protected void subscribeMusic() {
        if (subscribeButton2.isSelected()) {
            display.appendText("Subscribed to the music channel." + "\n");
            subscribeButton2.setText("Unsubscribe");
        } else {
            display.appendText("Unsubscribed from the music channel." + "\n");
            subscribeButton2.setText("Subscribe");
        }
    }

    @FXML
    protected void subscribeSoftware() {
        if (subscribeButton3.isSelected()) {
            display.appendText("Subscribed to the software engineering channel." + "\n");
            subscribeButton3.setText("Unsubscribe");
        } else {
            display.appendText("Unsubscribed from the software channel." + "\n");
            subscribeButton3.setText("Subscribe");
        }
    }

    @FXML
    protected void subscribeGaming() {
        if (subscribeButton4.isSelected()) {
            display.appendText("Subscribed to the gaming channel." + "\n");
            subscribeButton4.setText("Unsubscribe");
        } else {
            display.appendText("Unsubscribed from the gaming channel." + "\n");
            subscribeButton4.setText("Subscribe");
        }
    }

    @FXML
    protected void subscribeBees() {
        if (subscribeButton5.isSelected()) {
            display.appendText("Subscribed to the beekeeping channel." + "\n");
            subscribeButton5.setText("Unsubscribe");
        } else {
            display.appendText("Unsubscribed from the beekeeping channel." + "\n");
            subscribeButton5.setText("Subscribe");
        }
    }

    @FXML
    protected void subscribeLit() {
        if (subscribeButton6.isSelected()) {
            display.appendText("Subscribed to the literature channel." + "\n");
            subscribeButton6.setText("Unsubscribe");
        } else {
            display.appendText("Unsubscribed from the literature channel." + "\n");
            subscribeButton6.setText("Subscribe");
        }
    }

    @FXML
    protected void moveChannel() {
        Toggle selectedToggle = channelGroup.getSelectedToggle();
        if (generalButton.equals(selectedToggle)) {
            display.appendText("Welcome to the general channel." + "\n");
            display.clear();
        } else if (musicButton.equals(selectedToggle)) {
            display.appendText("Welcome to the music channel." + "\n");
            display.clear();
        } else if (softwareButton.equals(selectedToggle)) {
            display.appendText("Welcome to the software engineering channel." + "\n");
            display.clear();
        } else if (gamingButton.equals(selectedToggle)) {
            display.appendText("Welcome to the gaming channel." + "\n");
            display.clear();
        } else if (beeButton.equals(selectedToggle)) {
            display.appendText("Welcome to the beekeeping channel." + "\n");
            display.clear();
        } else if (litButton.equals(selectedToggle)) {
            display.appendText("Welcome to the literature channel." + "\n");
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