package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LancasterPage extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {

        // allows for easy switching of pages on the same stage
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(LancasterPage.class.getResource("login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Lancaster Restaurant");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}