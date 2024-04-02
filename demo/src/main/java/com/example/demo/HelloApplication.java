package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        ImageView imageView = (ImageView) fxmlLoader.getNamespace().get("imageView");
        Image image = new Image("demo/src/main/java/com/example/demo/pictures/Lancasters-logos.jpg");
        imageView.setImage(image);


        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        stage.setTitle("Lancaster Restaurant");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}