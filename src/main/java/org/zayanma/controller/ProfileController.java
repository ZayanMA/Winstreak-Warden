package org.zayanma.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    Button loadButton;
    @FXML
    Button createButton;
    @FXML
    ChoiceBox<String> profilesChoiceBox;
    @FXML
    TextField profileNameField;
    @FXML
    Pane root;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
