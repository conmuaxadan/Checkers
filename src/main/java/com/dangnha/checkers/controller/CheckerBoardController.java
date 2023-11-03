package com.checkers.controller;

import com.checkers.model.CheckerBoard;
import com.checkers.view.CheckBoardView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CheckerBoardController {
    private CheckerBoard checkerBoard;
    private final CheckBoardView checkBoardView;
    private static CheckerBoardController instance;

    private CheckerBoardController() {
        checkBoardView = CheckBoardView.getInstance();
    }

    public static CheckerBoardController getInstance() {
        if (instance == null)
            instance = new CheckerBoardController();
        return instance;
    }

    public void setCheckerBoard(CheckerBoard checkerBoard) {
        this.checkerBoard = checkerBoard;
        checkBoardView.refreshBoardView(checkerBoard);
    }
}