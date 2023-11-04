package com.dangnha.checkers;

import com.dangnha.checkers.controller.GameController;
import com.dangnha.checkers.view.CheckerBoardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String cssFile = this.getClass().getResource("/css/style.css").toExternalForm();

        GameController gameController = GameController.getInstance();
        gameController.initGame();
        Scene scene = new Scene(CheckerBoardView.getInstance(), 475, 475);
        stage.setResizable(false);
        scene.getStylesheets().add(cssFile);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}