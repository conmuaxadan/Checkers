package com.dangnha.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CheckerGameApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String cssFile = Objects.requireNonNull(this.getClass().getResource("/css/style.css")).toExternalForm();
        FXMLLoader fxmlLoader = new FXMLLoader(CheckerGameApplication.class.getResource("checker-game-application.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(cssFile);
        primaryStage.setTitle("Checker!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
