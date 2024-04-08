package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

public class LoginController {

    String CONN_STRING = "jdbc:mysql://smcse-stuproj00.city.ac.uk:3306/in2033t01";
    String userNameDatabase = "in2033t01_d";
    String passWordDatabase = "9XDKGxcQhhI";

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    private Button logInButton;

    @FXML
    private Label errorLabel;


    // initialise the button to be deactivated
    public void initialize() {
        logInButton.setDisable(true);
    }

    @FXML
    protected void onButtonClick() throws IOException {
        boolean isAdmin = false;
        // connect to database to authenticate user
        try {
            Connection conn = DriverManager.getConnection(CONN_STRING, userNameDatabase, passWordDatabase);

            // execute query to get all the credentials
            String selectCustomer = "SELECT password FROM Credentials WHERE username=?";
            PreparedStatement selectCustStm = conn.prepareStatement(selectCustomer);
            selectCustStm.setString(1, userName.getText());

            var result = selectCustStm.executeQuery();

            var meta = result.getMetaData();


            while (result.next()) {
                for (int i=1; i<=meta.getColumnCount(); i++) {
                    // now we will compare the texts from the database to the text from the user name and password
                    String password = result.getString(i);
                    System.out.println(password);
                    System.out.println(passWord.getText());
                    if (!password.isEmpty() || !password.trim().isEmpty()) {
                        if (password.equals(passWord.getText())) {
                            isAdmin = true;
                            break;
                        }
                    }

                }
            }
        } catch (SQLException e) {
            System.err.println("There was an error connecting to the database");
        }

        // if the password is correct, then we move to the main menu, else show error
        if (isAdmin) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("main-page.fxml"));
            Stage stage = LancasterPage.stage;
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Lancaster Restaurant");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } else {
            errorLabel.setText("Incorrect username or password");
        }

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