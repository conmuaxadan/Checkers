package com.dangnha.checkers.controller;

import com.dangnha.checkers.constants.GameState;
import com.dangnha.checkers.model.CheckerBoard;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class GameController {
    private CheckerBoardController checkerBoardController;

    private static GameController instance;
    private boolean isBlackTurn;

    private GameController() {
        isBlackTurn = true;
        this.checkerBoardController = CheckerBoardController.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void initGame() {
        CheckerBoard newGameBoard = new CheckerBoard();
        this.checkerBoardController.setCheckerBoard(newGameBoard);
        startGame(false);
    }

    public void startGame(boolean isAI) {
//        checkerBoardController.grantTeamEventHandler(true);
//        checkerBoardController.grantTeamEventHandler(false);

//        while (checkGameOver()) {
//            if (isBlackTurn) {
//                checkerBoardController.grantTeamEventHandler(true);
//                isBlackTurn = false;
//            } else {
//                checkerBoardController.grantTeamEventHandler(false);
//                isBlackTurn = true;
//            }
//
//        }

        AnimationTimer gameLoop = new AnimationTimer() {
//            boolean isBlackTurn = true;

            @Override
            public void handle(long now) {
                if (!checkGameOver())
                    if (isBlackTurn) {
                        checkerBoardController.grantTeamEventHandler(true);
                        // remove event handler after clicked 1s
                        PauseTransition pause = new PauseTransition(Duration.seconds(30));
                        pause.setOnFinished(event -> {
                            checkerBoardController.removeTeamEventHandler(true);
                        });
                        pause.play();
//                        isBlackTurn = false;

                    } else {
                        checkerBoardController.grantTeamEventHandler(false);
                        // remove event handler after clicked 1s
                        PauseTransition pause = new PauseTransition(Duration.seconds(30));
                        pause.setOnFinished(event -> {
                            checkerBoardController.removeTeamEventHandler(false);
                        });
                        pause.play();
//                        isBlackTurn = true;
                    }
            }
        };

        gameLoop.start();

    }

    private boolean checkGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (checkerBoardController.gameOver().equals(GameState.BLACK_LOSE)) {
            alert.setHeaderText("Game over");
            alert.setContentText("Black was lose!!");
            alert.show();
            return true;
        }
        if (checkerBoardController.gameOver().equals(GameState.WHITE_LOSE)) {
            alert.setHeaderText("Game over");
            alert.setContentText("White was lose!!");
            alert.show();
            return true;
        }
        return false;
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        isBlackTurn = blackTurn;
    }
}
