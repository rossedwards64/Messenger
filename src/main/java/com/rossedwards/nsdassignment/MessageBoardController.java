package com.rossedwards.nsdassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.PrintWriter;
import java.util.Scanner;

public class MessageBoardController {

    public Font x1;
    public Color x2;
    public Font x11;
    public Color x21;
    public Font x3;
    public Color x4;
    Client client;
    PrintWriter out;
    Scanner reader;

    @FXML
    private Label username;

    @FXML
    private TextField sendMessageBox;

    @FXML
    protected void startClient() {

    }

    @FXML
    protected void sendMessage() {

    }

    @FXML
    protected void setUsernameLabelText() {

    }
}