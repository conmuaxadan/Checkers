package com.dangnha.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckerGame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CheckerGame.class.getResource("checker-game-application.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Checker!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
