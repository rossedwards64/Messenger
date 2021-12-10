package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageBoardController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}