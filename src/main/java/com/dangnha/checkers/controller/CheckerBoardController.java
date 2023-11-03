package com.dangnha.checkers.controller;

import com.dangnha.checkers.model.Checker;
import com.dangnha.checkers.model.CheckerBoard;
import com.dangnha.checkers.model.Position;
import com.dangnha.checkers.view.CheckBoardView;

import java.util.EventListener;

public class CheckerBoardController {
    private CheckerBoard checkerBoard;
    private Position currentCheckerPos;
    private Position newCheckerPos;
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

    public void placeChecker(Position currentCheckerPos, Position newCheckerPos) {
        checkerBoard.placeChessman(currentCheckerPos.getX(), currentCheckerPos.getY(), newCheckerPos.getX(), newCheckerPos.getY());

        checkBoardView.refreshBoardView(checkerBoard);
    }

    public Position getCurrentCheckerPos() {
        return currentCheckerPos;
    }

    public void setCurrentCheckerPos(Position currentCheckerPos) {
        this.currentCheckerPos = currentCheckerPos;
    }

    public Position getNewCheckerPos() {
        return newCheckerPos;
    }

    public void setNewCheckerPos(Position newCheckerPos) {
        this.newCheckerPos = newCheckerPos;
    }
}