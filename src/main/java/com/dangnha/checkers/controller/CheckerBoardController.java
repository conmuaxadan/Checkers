package com.dangnha.checkers.controller;

import com.dangnha.checkers.constants.CheckerConstant;
import com.dangnha.checkers.model.Checker;
import com.dangnha.checkers.model.CheckerBoard;
import com.dangnha.checkers.model.Position;
import com.dangnha.checkers.view.CheckerBoardView;

import java.util.List;

public class CheckerBoardController {
    private CheckerBoard checkerBoard;
    private Position currentCheckerPos;
    private Position newCheckerPos;
    private final CheckerBoardView checkerBoardView;
    private static CheckerBoardController instance;

    private CheckerBoardController() {
        checkerBoardView = CheckerBoardView.getInstance();
    }

    public static CheckerBoardController getInstance() {
        if (instance == null)
            instance = new CheckerBoardController();
        return instance;
    }

    public void setCheckerBoard(CheckerBoard checkerBoard) {
        this.checkerBoard = checkerBoard;
        checkerBoardView.refreshBoardView(checkerBoard);
    }

    public void placeChecker(Position currentCheckerPos, Position newCheckerPos) {
        checkerBoard.placeChecker(currentCheckerPos.getX(), currentCheckerPos.getY(), newCheckerPos.getX(), newCheckerPos.getY());

        checkerBoardView.refreshBoardView(checkerBoard);
    }

    public void displayAvailableMoves(Position currentCheckerPos) {
        Checker currentChecker = checkerBoard.findCheckerByPosition(currentCheckerPos.getX(),
                currentCheckerPos.getY());
        List<Position> availableMoves = currentChecker.getValidPositions(checkerBoard);

        boolean isBlackTurn = checkerBoard.isBlackTurn();

        if ((isBlackTurn && (currentChecker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_BLACK) || currentChecker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_BLACK)))
                || (!isBlackTurn && (currentChecker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_WHITE) || currentChecker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_WHITE))))
        {
            checkerBoardView.refreshBoardView(checkerBoard);
            for (Position availableMove : availableMoves) {
                checkerBoardView.highlightCell(availableMove.getX(), availableMove.getY());
            }
        }
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