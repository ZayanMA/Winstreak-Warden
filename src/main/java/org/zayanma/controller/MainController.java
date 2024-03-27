package org.zayanma.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.zayanma.model.APIManager;
import org.zayanma.model.DataManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static DataManager manager = new DataManager();

    public static StringProperty currentProfile = new SimpleStringProperty();

    public static APIManager apiManager = new APIManager();

    public static ObservableList<String> accountsList = FXCollections.observableArrayList();
    @FXML
    Button addTrackedAccount;

    @FXML
    ListView<String> accountsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountsList.addAll(manager.getTrackedPlayers(currentProfile.getValue()));
        currentProfile.addListener((observable, oldValue, newValue) -> {
            updateInterface();
        });
        accountsList.addListener((ListChangeListener<String>) change -> {
            accountsListView.getItems().clear();
            accountsListView.getItems().addAll(accountsList);
        });
        ContextMenu trackedaccountsMenu = new ContextMenu();
        MenuItem deleteAccountItem = new MenuItem();
        deleteAccountItem.setText("Delete");
        trackedaccountsMenu.getItems().add(deleteAccountItem);
        accountsListView.setContextMenu(trackedaccountsMenu);
        deleteAccountItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                manager.deleteAccount(accountsListView.getSelectionModel().getSelectedItem());
                accountsList.remove(accountsListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    public void updateInterface(){
        Stage currentStage = (Stage) addTrackedAccount.getScene().getWindow();
        currentStage.setTitle("Winstreak Warden - " + currentProfile.getValue());
        accountsList.clear();
        accountsList.addAll(manager.getTrackedPlayers(currentProfile.getValue()));
    }

    public void onAddTracked() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) addTrackedAccount.getScene().getWindow();
        stage.initOwner(parentStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addTrackerDialog.fxml"));
        Parent root = loader.load();
        stage.setTitle("Add a new account");
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void onChangeProfile() throws IOException {
        Stage dialogStage = new Stage();
        Stage stage = (Stage) addTrackedAccount.getScene().getWindow();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setTitle("Profile Selector");
        Scene dialogBox = new Scene (FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/fxml/profileDialog.fxml"))));
        dialogStage.setScene(dialogBox);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}