package org.zayanma.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.zayanma.model.DataManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    Text errorText;
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

    DataManager dataManager;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataManager = MainController.manager;
        ArrayList<String> profiles = dataManager.getExistingProfiles();
        profilesChoiceBox.getItems().addAll(profiles);
    }

    public void onLoad(){
        if(!Objects.equals(profilesChoiceBox.getValue(), "")){
            MainController.currentProfile.set(profilesChoiceBox.getValue());
            Stage currentStage = (Stage) profileNameField.getScene().getWindow();
            currentStage.close();
        }
    }

    public void onCreate(){
        if(dataManager.getExistingProfiles().contains(profileNameField.getText())){
            errorText.setText("Sorry that profile name already exists.");
        } else if(profileNameField.getText().equals("")){
            errorText.setText("Please input a valid name.");
        } else{
            dataManager.addNewProfile(profileNameField.getText());
            profilesChoiceBox.getItems().clear();
            ArrayList<String> profiles = dataManager.getExistingProfiles();
            profilesChoiceBox.getItems().addAll(profiles);
        }
    }

    public void onDelete(){
        if(!profilesChoiceBox.getValue().equals("")){
            profilesChoiceBox.getItems().remove(profilesChoiceBox.getValue());
            dataManager.deleteProfile(profilesChoiceBox.getValue());
        }
    }
}
