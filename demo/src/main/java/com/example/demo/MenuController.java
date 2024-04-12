package com.example.demo;

import com.example.demo.supportclasses.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private ListView<String> menuItems;

    private String currItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Menu menu = new Menu();
            String[] items = menu.getNames().toArray(new String[0]);
            menuItems.getItems().addAll(items);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
