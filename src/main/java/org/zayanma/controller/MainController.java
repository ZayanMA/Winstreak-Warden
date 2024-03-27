package org.zayanma.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.zayanma.model.DataManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    DataManager manager;
    @FXML
    Button addTrackedAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DataManager();
    }

    public void onAddTracked() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) addTrackedAccount.getScene().getWindow();
        stage.initOwner(parentStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addTrackerDialog.fxml"));
        Parent root = loader.load();
        stage.setTitle("Winstreak-Warden");
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}