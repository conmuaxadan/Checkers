package com.dangnha.checkers.controller;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.model.CheckerBoard;

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
