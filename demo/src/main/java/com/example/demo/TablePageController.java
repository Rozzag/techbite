package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TablePageController implements Initializable {
    @FXML
    private ComboBox<Integer> tabDrop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> tables = FXCollections.observableArrayList();
        for (int i = 1; i < 16; i++){
            tables.add(i);
        }
        tabDrop.setItems(tables);
    }
}
