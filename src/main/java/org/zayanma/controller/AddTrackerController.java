package org.zayanma.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.zayanma.model.APIManager;
import org.zayanma.model.DataManager;

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
    @FXML
    Text errorText;
    DataManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = MainController.manager;
        button_add.setDisable(true);
        comboBox_region.getItems().addAll("AMERICAS", "EUROPE", "ASIA", "ESPORTS");
    }

    public void onGet() throws IOException {
        if(comboBox_region.getValue().equals("") || textfield_tagline.getText().equals("") || textfield_username.getText().equals("")){
            errorText.setText("Please input all values");
        } else{
            errorText.setText("Account successfully received");
            button_add.setDisable(false);
        }
    }

    public void onAdd(){
        if(comboBox_region.getValue().equals("") || textfield_tagline.getText().equals("") || textfield_username.getText().equals("")){
            errorText.setText("Please input all values");
        } else{
            manager.addNewAccount(comboBox_region.getValue(), textfield_username.getText(), textfield_tagline.getText());
            Stage currentStage = (Stage) textfield_tagline.getScene().getWindow();
            MainController.accountsList.add(textfield_username.getText() + "#" + textfield_tagline.getText());
            currentStage.close();
        }

    }
}
