package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class MainPageController {
    @FXML
    private Button dashboard;

    @FXML
    private Button menu;

    @FXML
    private Button table;

    @FXML
    private Button payment;

    @FXML
    private Pane pane;

    public void dashboardPage(MouseEvent mouseEvent) {
    }

    public void tablesPage(MouseEvent mouseEvent) throws IOException {
        // clear all other pages and add the tables page to the stack
        pane.getChildren().clear();
        Node tablesPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("table-page.fxml")));
        pane.getChildren().add(tablesPage);
    }

    public void menusPage(MouseEvent mouseEvent) {
    }

    public void paymentPage(MouseEvent mouseEvent) {
    }
}
