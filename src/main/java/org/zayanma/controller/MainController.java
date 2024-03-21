package org.zayanma.controller;

import javafx.fxml.Initializable;
import org.zayanma.model.DataManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    DataManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DataManager();
    }
}