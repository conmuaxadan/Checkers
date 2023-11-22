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
        int countAvailableMoveBlack = Integer.MIN_VALUE;
        for (Checker checker : checkerBoard.getCheckerList()
        ) {
            if (checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_BLACK) && checker.getValidPositions(checkerBoard) != null) {
                countAvailableMoveBlack += checker.getValidPositions(checkerBoard).size();
            }
        }

        int countAvailableMoveWhite = Integer.MIN_VALUE;
        for (Checker checker : checkerBoard.getCheckerList()
        ) {
            if (checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_WHITE) && checker.getValidPositions(checkerBoard) != null) {
                countAvailableMoveWhite += checker.getValidPositions(checkerBoard).size();
            }
        }

        int countBlack = (int) checkerBoard.getCheckerList().stream().filter(checker -> checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_BLACK)).count();
        int countWhite = (int) checkerBoard.getCheckerList().stream().filter(checker -> checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_WHITE)).count();

        if (countWhite == 0)
            return GameState.WHITE_LOSE;

        if (countBlack == 0)
            return GameState.BLACK_LOSE;

        if (countAvailableMoveBlack == 0 && countAvailableMoveWhite > 0)
            return GameState.BLACK_LOSE;

        if (countAvailableMoveWhite == 0 && countAvailableMoveBlack > 0)
            return GameState.WHITE_LOSE;

        if (countAvailableMoveWhite == 0 && countAvailableMoveBlack == 0)
            return GameState.DRAW;

        return GameState.CONTINUE;
    }

    public void setCheckBoardModelTurn(boolean isBlackTurn) {
        checkerBoard.setBlackTurnModel(isBlackTurn);
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