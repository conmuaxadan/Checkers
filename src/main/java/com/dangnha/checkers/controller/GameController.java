package com.checkers.controller;

import com.checkers.constants.BoardConstant;
import com.checkers.model.CheckerBoard;

public class GameController {
    private CheckerBoardController checkerBoardController;

    private static GameController instance;

    private GameController() {
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
    }
}
