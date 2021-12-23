package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

public class MessageBoardController {

    // channels could be things like music, software engineering, gaming, etc.

    public Font x1;
    public Color x2;
    public Font x11;
    public Color x21;
    public Font x3;
    public Color x4;
    public Font x111;
    public Color x211;
    public TextArea display;
    public Button sendMessageButton;
    public Button setUsernameButton;
    public Button quitButton;
    public TextField setUsernameField;
    public Label isConnectedLabel;
    public ToggleButton subscribeButton;
    public ToggleButton subscribeButton2;
    public ToggleButton subscribeButton3;
    public ToggleButton subscribeButton4;
    public ToggleButton subscribeButton5;
    public ToggleButton subscribeButton6;
    public ToggleGroup channelGroup;
    public Label imageSection;
    public ImageView imageView;
    public Button attachImageButton;
    public Button updateImage;
    public ToggleButton generalButton;
    public ToggleButton musicButton;
    public ToggleButton softwareButton;
    public ToggleButton gamingButton;
    public ToggleButton beeButton;
    public ToggleButton litButton;
    public static Client client;
    public static Socket socket;
    public List<String> extensionFilter;

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
        try {
            if ((message = sendMessageBox.getText()) != null) {
                client.setRequestPost(message);
                client.in.readLine();
                readChat();
                sendMessageBox.clear();
            }
        } catch (NoSuchElementException e) {
            System.out.println("Nothing to send in the text area.");
        }
    }

    @FXML
    protected void attachImage() throws IOException {
        extensionFilter = new ArrayList<>();
        extensionFilter.add("*.jpg");
        extensionFilter.add("*.png");
        extensionFilter.add("*.gif");
        FileChooser chooseFile = new FileChooser();
        chooseFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", extensionFilter));
        File file = chooseFile.showOpenDialog(null);

        if (file != null) {
            byte[] rawData = readBytes(file);
            String encodedImage = Base64.getEncoder().encodeToString(rawData);

            FileWriter out = new FileWriter(file.getAbsolutePath() + ".base64");
            out.write(encodedImage);
            out.close();

            client.setRequestPostImage(encodedImage);
        }
    }

    private byte[] readBytes(File file) throws IOException {
        byte[] content = new byte[(int) file.length()];
        InputStream in = new FileInputStream(file);
        int n = in.read(content);
        in.close();
        if (n != (int) file.length()) {
            throw new IOException(n + " (bytes read) != (file size) " + (int) file.length());
        }
        return content;
    }


    @FXML
    protected void saveImage(byte[] decodedImage, String fileName) throws IOException {
        OutputStream out = new FileOutputStream(fileName);
        out.write(decodedImage);
        out.close();
    }

    @FXML
    protected void readChat() throws IOException {
        String messageStr;
        client.setRequestRead();
        if ((messageStr = client.in.readLine()) != null) {
            Object json = JSONValue.parse(messageStr);
            MessageListResponse response;
            if ((response = MessageListResponse.fromJSON(json)) != null) {
                for (Message message : response.getMessages()) {
                    display.appendText(message + "\n");
                }
            }
        } else {
            System.out.println("All messages have been read.");
        }
    }

    @FXML
    protected void readImages() throws IOException {
        String imagesToDecode;
        client.setRequestReadImage();
        if ((imagesToDecode = client.in.readLine()) != null) {
            Object json = JSONValue.parse(imagesToDecode);
            ImageListResponse response;
            if ((response = ImageListResponse.fromJSON(json)) != null) {
                for (Message image : response.getImages()) {
                    byte[] decodedImage = Base64.getDecoder().decode(image.getBody());
                    saveImage(decodedImage, image.getAuthor() + ".jpg");

                    ByteArrayInputStream in = new ByteArrayInputStream(decodedImage);
                    Image displayImage = new Image(in);

                    imageView = new ImageView(displayImage);
                    imageView.setFitHeight(311);
                    imageView.setFitWidth(267);
                    imageView.setPreserveRatio(true);
                    imageSection.setGraphic(imageView);
                    in.close();
                }
            }
        } else {
            System.out.println("All images read.");
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