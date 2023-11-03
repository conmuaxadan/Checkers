package com.checkers.view;

import com.checkers.model.Checker;
import com.checkers.model.CheckerBoard;
import com.checkers.model.NormalChecker;
import com.checkers.model.Position;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import com.checkers.constants.*;
import javafx.scene.layout.Pane;

public class CheckBoardView extends GridPane {
    private static CheckBoardView instance;

    private CheckBoardView() {
    }

    public static CheckBoardView getInstance() {
        if (instance == null) instance = new CheckBoardView();
        return instance;
    }

    /**
     * Create checker from State of the {@link com.checkers.model.CheckerBoard}
     *
     * @param checkerType is the type in {@link com.checkers.model.CheckerBoard} state. Ex: KB, KW, B, W
     * @param x           is horizontal cooperate of this (Descartes Cooperate)
     * @param y           is vertical cooperate of this (Descartes Cooperate)
     * @return {@link Checker} of the factory
     */
    private Checker checkersFactory(String checkerType, int x, int y) {
        Position pos = new Position(x, y);
        switch (checkerType) {
            case CheckerConstant.CHESS_TYPE_BLACK -> {
                return new NormalChecker(CheckerConstant.CHESS_TYPE_BLACK, pos);
            }
            case CheckerConstant.CHESS_TYPE_WHITE -> {
                return new NormalChecker(CheckerConstant.CHESS_TYPE_WHITE, pos);
            }
            case CheckerConstant.CHESS_TYPE_KING_BLACK -> {
                return new NormalChecker(CheckerConstant.CHESS_TYPE_KING_BLACK, pos);
            }
            case CheckerConstant.CHESS_TYPE_KING_WHITE -> {
                return new NormalChecker(CheckerConstant.CHESS_TYPE_KING_WHITE, pos);
            }
        }
        return null;
    }

    /**
     * Refresh {@link CheckBoardView} after {@link CheckerBoard} changes.
     * <br>
     * Because each {@link CheckerBoard} has only one states.
     * @param boardModel is the current {@link CheckerBoard}
     */
    public void refreshBoardView(CheckerBoard boardModel) {
        String[][] boardModelStates = boardModel.getBoardStates();
        for (int y = 0; y < boardModelStates.length; y++) {
            for (int x = 0; x < boardModelStates.length; x++) {
                String cell = boardModelStates[x][y];
                if (cell.equalsIgnoreCase("0"))
                    continue;
                if (cell.equalsIgnoreCase("1")) {
                    Pane darkCell = new Pane();
                    darkCell.getStyleClass().add("dark-cell");
                    this.add(darkCell, x, y);
                } else {
                    Checker checker = checkersFactory(cell, x, y);
                    Button checkerButton = new Button(checker.getCheckerType());
                    this.add(checkerButton, x, y);
                }
            }
        }
    }
}




