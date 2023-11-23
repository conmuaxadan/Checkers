package com.dangnha.checkers.controller;

import com.dangnha.checkers.constants.GameDifficult;
import com.dangnha.checkers.constants.GameState;
import com.dangnha.checkers.model.AI;
import com.dangnha.checkers.model.CheckerBoard;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class GameController {
    private GameDifficult gameDifficult;
    private CheckerBoardController checkerBoardController;

    private static GameController instance;
    private boolean isBlackTurn;
    private AI ai;

    private GameController() {
        this.checkerBoardController = CheckerBoardController.getInstance();
        ai = AI.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void initGame(GameDifficult gameDifficult) {
        this.gameDifficult = gameDifficult;
        CheckerBoard newGameBoard = new CheckerBoard();
        this.checkerBoardController.setCheckerBoard(newGameBoard);
        startGame(true, gameDifficult);
    }

    public void startGame(boolean isAI, GameDifficult gameDifficult) {
        isBlackTurn = true;
        AnimationTimer gameLoop = new AnimationTimer() {
//            boolean isBlackTurn = true;

            @Override
            public void handle(long now) {
                if (!checkGameOver())
                    if (isAI) {
                        if (isBlackTurn) {
                            CheckerBoard bestMove = ai.getBestMove(checkerBoardController.getCheckerBoard(), gameDifficult);
                            checkerBoardController.setCheckerBoard(bestMove);
                            isBlackTurn = false;
                            checkerBoardController.getCheckerBoard().setBlackTurnModel(isBlackTurn);
                        } else {
                            checkerBoardController.grantTeamEventHandler(false);
                            // remove event handler after clicked 1s
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event -> {
                                checkerBoardController.removeTeamEventHandler(false);
                            });
                            pause.play();
                        }

                    } else if (isBlackTurn) {
                        checkerBoardController.grantTeamEventHandler(true);
                        // remove event handler after clicked 1s
                        PauseTransition pause = new PauseTransition(Duration.seconds(30));
                        pause.setOnFinished(event -> {
                            checkerBoardController.removeTeamEventHandler(true);
                        });
                        pause.play();


                    } else {
                        checkerBoardController.grantTeamEventHandler(false);
                        // remove event handler after clicked 1s
                        PauseTransition pause = new PauseTransition(Duration.seconds(30));
                        pause.setOnFinished(event -> {
                            checkerBoardController.removeTeamEventHandler(false);
                        });
                        pause.play();
                    }
                else {
                    stopGame(this);
                }
            }
        };

        gameLoop.start();

    }

    private boolean checkGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (checkerBoardController.gameOver().equals(GameState.BLACK_LOSE)) {
            alert.setTitle("Checkers!");
            alert.setHeaderText("Game Over");
            alert.setContentText("Black was lose!! Try again?");
            alert.show();
            return true;
        }
        if (checkerBoardController.gameOver().equals(GameState.WHITE_LOSE)) {
            alert.setTitle("Checkers!");
            alert.setHeaderText("Game Over");
            alert.setContentText("White was lose!! Try again?");
            alert.show();
            return true;
        }
        if (checkerBoardController.gameOver().equals(GameState.DRAW)) {
            alert.setTitle("Checkers!");
            alert.setHeaderText("Game Over");
            alert.setContentText("You was draw with opponent!! Try again?");
            alert.show();
            return true;
        }
        return false;
    }

    public void stopGame(AnimationTimer gameLoop) {
        gameLoop.stop();
        this.initGame(this.gameDifficult);
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        isBlackTurn = blackTurn;
    }
}
