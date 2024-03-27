package org.zayanma.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.zayanma.model.APIManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddTrackerController implements Initializable {

    @FXML
    TextField textfield_tagline;
    @FXML
    TextField textfield_username;
    @FXML
    Button button_get;
    @FXML
    Button button_add;
    @FXML
    ComboBox<String> comboBox_region;

    APIManager apiManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apiManager = new APIManager();
        button_add.setDisable(true);
        comboBox_region.getItems().addAll("AMERICAS", "EUROPE", "ASIA", "ESPORTS");
    }

    public void onGet() throws IOException {
        System.out.println(apiManager.puuidFromName(comboBox_region.getSelectionModel().getSelectedItem(), textfield_username.getText(), textfield_tagline.getText()));
    }
}
