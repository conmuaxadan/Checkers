package com.dangnha.checkers.controller;

import com.dangnha.checkers.constants.GameDifficult;
import com.dangnha.checkers.view.CheckerBoardView;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class CheckerGameController implements Initializable {
    @FXML
    private Label turnLabel;
    @FXML
    private RadioButton playWithAIRadio;
    @FXML
    private RadioButton playWithHumanRadio;
    @FXML
    private VBox boardContainer;
    @FXML
    private MenuButton menuButtonDifficult;


    private final GameController gameController = GameController.getInstance();

    @FXML
    protected void setPlayWithAI() {
        gameController.setPlayWithAI(true);
        menuButtonDifficult.setDisable(false);
    }

    @FXML
    protected void setPlayWithHuman() {
        gameController.setPlayWithAI(false);
        menuButtonDifficult.setDisable(true);
    }

    @FXML
    protected void resetGame() {
        gameController.resetGame();
    }

    @FXML
    protected void playGame() {
        gameController.startGame();
    }

    @FXML
    protected void handleDifficultClick(ActionEvent event) {
        MenuItem selectedItem = (MenuItem) event.getTarget();
        String itemLabel = selectedItem.getText();

        if (!gameController.isPlaying()) {
            if (itemLabel.equals("EASY"))
                gameController.setGameDifficult(GameDifficult.EASY);

            if (itemLabel.equals("MEDIUM"))
                gameController.setGameDifficult(GameDifficult.MEDIUM);

            if (itemLabel.equals("HARD"))
                gameController.setGameDifficult(GameDifficult.HARD);

            menuButtonDifficult.setText(itemLabel);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckerBoardView view = CheckerBoardView.getInstance();

        gameController.initGame();
        boardContainer.getChildren().add(view);

        ToggleGroup toggleGroup = new ToggleGroup();

        playWithAIRadio.setToggleGroup(toggleGroup);
        playWithAIRadio.setSelected(true);

        playWithHumanRadio.setToggleGroup(toggleGroup);

        updateValueViaLoop();
    }

    public void updateValueViaLoop() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                // update turn label
                if (gameController.isPlaying()) {
                    boolean isBlackTurn = gameController.isBlackTurn();
                    if (isBlackTurn)
                        turnLabel.setText("Black turn");
                    else
                        turnLabel.setText("White turn");
                }

                // update MenuButton choose difficult
                if (gameController.isPlaying() || !gameController.isPlayWithAI())
                    menuButtonDifficult.setDisable(true);
                else
                    menuButtonDifficult.setDisable(false);

                // update play with AI or Human radio
                if (gameController.isPlayWithAI())
                    playWithAIRadio.setSelected(true);
                else
                    playWithHumanRadio.setSelected(true);

            }
        };
        animationTimer.start();

    }
}
