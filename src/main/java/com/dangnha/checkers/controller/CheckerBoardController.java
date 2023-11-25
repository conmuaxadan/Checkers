package com.dangnha.checkers.controller;

import com.dangnha.checkers.constants.GameState;
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


    /**
     * Place checker to new position.
     *
     * @param currentCheckerPos the position of current checker
     * @param newCheckerPos     the position that checker want to move
     */
    public boolean placeChecker(Position currentCheckerPos, Position newCheckerPos) {
        boolean result = checkerBoard.setCheckerPosition(currentCheckerPos, newCheckerPos);

        System.out.println(checkerBoard);
        checkerBoardView.refreshBoardView(checkerBoard);
        return result;
    }

    /**
     * Display available move for current checker
     *
     * @param currentCheckerPos is the current checker position
     */
    public void displayAvailableMoves(Position currentCheckerPos) {
        Checker currentChecker = checkerBoard.findCheckerByPosition(currentCheckerPos.getX(),
                currentCheckerPos.getY());
        List<Position> availableMoves = currentChecker.getValidPositions(checkerBoard);

        checkerBoardView.refreshBoardView(checkerBoard);
        for (Position availableMove : availableMoves) {
            checkerBoardView.highlightCell(availableMove.getX(), availableMove.getY());
        }

    }

    /**
     * This method grant for each team add event handler for its checker to move the checker
     * <br> (using for human player only)
     */
    public void grantTeamEventHandler(boolean isBlackTeam) {
        checkerBoardView.grantTeamEventHandler(isBlackTeam);
    }

    /**
     * Remove event handler for each element (checker buttons)
     *
     * @param isBlackTeam determines if black checker buttons or white checker buttons
     */
    public void removeTeamEventHandler(boolean isBlackTeam) {
        checkerBoardView.removeTeamEventHandler(isBlackTeam);
    }

    public GameState gameOver() {
        return this.checkerBoard.gameOver();
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

    public CheckerBoard getCheckerBoard() {
        return checkerBoard;
    }
}