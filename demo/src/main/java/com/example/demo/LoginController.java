package com.example.demo;


import com.example.demo.connectivity.Database;
import com.example.demo.supportclasses.Staff;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    private Button logInButton;

    @FXML
    private Label errorLabel;

    private Staff staff;


    // initialise the button to be deactivated
    public void initialize() {
        logInButton.setDisable(true);
    }

    @FXML
    protected void onButtonClick() throws IOException, SQLException {

        // connect to database to authenticate user
        Database dataBase = new Database();
        String query = "SELECT * FROM Credentials WHERE username='%s';".formatted(userName.getText());
        ArrayList<ArrayList<String>> credentials = dataBase.selectValues(query);

        if (credentials.isEmpty()) {
            errorLabel.setText("Username or password is incorrect");
            PauseTransition disappearingMessage = new PauseTransition(Duration.seconds(1));
            disappearingMessage.setOnFinished(event -> errorLabel.setText(""));
            disappearingMessage.play();
            return;
        }

        ArrayList<String> credential = credentials.get(0);

        if (credential.get(3).equals(passWord.getText())) {
            // if the password is correct, then we move to the main menu, else show error
            userName.setText("");
            passWord.setText("");

            staff = new Staff(Integer.parseInt(credential.get(1)));

            String role = staff.getRole();

            if (role.equals("Maitre d")) {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("admin-menu.fxml"));
                Stage stage = LancasterPage.stage;
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(Objects.requireNonNull(LoginController.class.getResource("stackbutton.css")).toExternalForm());

                stage.setTitle("Lancaster Restaurant");
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.show();
            } else if (role.equals("Waiter") || role.equals("Sommelier")) {
                FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("waiter-page.fxml"));
                Stage stage = LancasterPage.stage;
                Scene scene = new Scene(fxmlLoader.load());

                stage.setTitle("Lancaster Restaurant");
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.show();

        }






        } else {
            errorLabel.setText("Incorrect username or password");
        }

        dataBase.close();

    }

    public void handleKeyReleased() {
        // set the button as disabled until the username and password fields are filled in
        String userNameField = userName.getText();
        String passWordField = passWord.getText();
        boolean disable = (userNameField.isEmpty() || userNameField.trim().isEmpty()) ||
                (passWordField.isEmpty() || passWordField.trim().isEmpty());
        logInButton.setDisable(disable);
    }

}