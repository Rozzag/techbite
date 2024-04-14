package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminViewController {
    @FXML
    private Button dashboard;

    @FXML
    private Button menu;

    @FXML
    private Button table;

    @FXML
    private Button payment;

    @FXML
    private Button admin;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button logoutButton;


    private Button previousSelectedButton = null;

    // normal button
    public void highlightedButton(Button button) {
        button.setStyle("-fx-background-color: #D9D9D9; -fx-text-fill: #2B3336;");
    }

    // highlighted button
    public void normalButton(Button button) {
        button.setStyle("-fx-background-color: #2B3336; -fx-text-fill: #D9D9D9;");
    }

    // initialize the main page for the administrator to be the dashboard
    public void initialize() throws IOException {
        // the previous button will revert to its normal colour
        if (this.previousSelectedButton != null) {
            normalButton(this.previousSelectedButton);
        }
        highlightedButton(dashboard);
        this.previousSelectedButton = dashboard;

        // add the tables page to the centre of the border pane
        Node dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));

        borderPane.setCenter(dashboard);
    }



    public void dashboardPage(MouseEvent mouseEvent) throws IOException {
        // the previous button will revert to its normal colour
        if (this.previousSelectedButton != null) {
            normalButton(this.previousSelectedButton);
        }
        highlightedButton(dashboard);
        this.previousSelectedButton = dashboard;

        // add the tables page to the centre of the border pane
        Node dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));

        borderPane.setCenter(dashboard);
        if(borderPane.getRight() != null){
            borderPane.setRight(null);
        }
    }

    public void tablesPage(MouseEvent mouseEvent) throws IOException {
        if (this.previousSelectedButton != null) {
            normalButton(this.previousSelectedButton);

        }
        highlightedButton(table);
        this.previousSelectedButton = table;

        // add the tables page to the centre of the border pane
        Node tablesPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("table-page.fxml")));

        borderPane.setCenter(tablesPage);


        Node tables = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("table.fxml")));

        borderPane.setRight(tables);
    }

    public void menusPage(MouseEvent mouseEvent) throws IOException {
        if (this.previousSelectedButton != null) {
            normalButton(this.previousSelectedButton);
        }
        highlightedButton(menu);
        this.previousSelectedButton = menu;

        Node menuPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        borderPane.setCenter(menuPage);

        if(borderPane.getRight() != null){
            borderPane.setRight(null);
        }
    }

    public void adminPage() throws IOException {
        if (this.previousSelectedButton != null) {
            normalButton(this.previousSelectedButton);
        }
        highlightedButton(admin);
        this.previousSelectedButton = admin;

        Node menuPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-page.fxml")));
        borderPane.setCenter(menuPage);

        if(borderPane.getRight() != null){
            borderPane.setRight(null);
        }
    }


    public void logout(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
        Stage stage = LancasterPage.stage;
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Lancaster Restaurant");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


}
