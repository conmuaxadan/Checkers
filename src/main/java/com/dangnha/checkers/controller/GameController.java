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
    private GameDifficult gameDifficult = GameDifficult.EASY;
    private final CheckerBoardController checkerBoardController;
    private boolean isPlayWithAI = true;
    private boolean isPlaying;

    private static GameController instance;
    private boolean isBlackTurn;
    private final AI ai;

    private GameController() {
        this.checkerBoardController = CheckerBoardController.getInstance();
        ai = AI.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

    public void initGame() {
        CheckerBoard newGameBoard = new CheckerBoard();
        this.checkerBoardController.setCheckerBoard(newGameBoard);
    }

    public void startGame() {
        isBlackTurn = true;
        isPlaying = true;

        PauseTransition pause = new PauseTransition();
        AnimationTimer gameLoop = new AnimationTimer() {
//            boolean isBlackTurn = true;

            @Override
            public void handle(long now) {
                if (!checkGameOver())
                    if (isBlackTurn) {
                        if (isPlayWithAI) {
                            // delay AI 1s to make view display when user (WHITE team) places checker
                            pause.setDuration(Duration.seconds(1));
                            pause.setOnFinished(event -> {
                                CheckerBoard bestMove = ai.getBestMove(checkerBoardController.getCheckerBoard(), gameDifficult);
                                checkerBoardController.setCheckerBoard(bestMove);
                                isBlackTurn = false;
                                checkerBoardController.getCheckerBoard().setBlackTurnModel(isBlackTurn);
                            });
                            pause.play();
                        } else {
                            checkerBoardController.grantTeamEventHandler(true);
                            // remove event handler after clicked 1s
                            PauseTransition pause = new PauseTransition(Duration.seconds(30));
                            pause.setOnFinished(event -> {
                                checkerBoardController.removeTeamEventHandler(true);
                            });
                            pause.play();
                        }

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
                    // stop game if game over!
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

        if (!checkerBoardController.gameOver().equals(GameState.CONTINUE))
            isPlaying = false;
        return false;
    }

    /**
     * Stop game if game over for each team
     * @param gameLoop is the loop util the game finished (black or white lose)
     */
    public void stopGame(AnimationTimer gameLoop) {
        gameLoop.stop();
        this.resetGame();
    }

    /**
     * Set value default of the game when user want to reset
     */
    public void resetGame() {
        initGame();
        isPlayWithAI = true;
        isPlaying = false;
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        isBlackTurn = blackTurn;
    }

    public boolean isPlayWithAI() {
        return isPlayWithAI;
    }

    public void setPlayWithAI(boolean playWithAI) {
        isPlayWithAI = playWithAI;
    }

    public GameDifficult getGameDifficult() {
        return gameDifficult;
    }

    public void setGameDifficult(GameDifficult gameDifficult) {
        this.gameDifficult = gameDifficult;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
