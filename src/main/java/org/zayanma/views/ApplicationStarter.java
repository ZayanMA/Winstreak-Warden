package org.zayanma.views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class ApplicationStarter extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        Parent root = loader.load();
        stage.setTitle("Winstreak-Warden");
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setTitle("Profile Selector");
        Scene dialogBox = new Scene (FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/fxml/profileDialog.fxml"))));
        dialogStage.setScene(dialogBox);
        dialogStage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        dialogStage.show();
    }

}
