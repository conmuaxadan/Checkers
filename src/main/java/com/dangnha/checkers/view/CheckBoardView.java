package com.dangnha.checkers.view;

import com.dangnha.checkers.eventsHandler.CheckerButtonEventHandler;
import com.dangnha.checkers.eventsHandler.CheckerHBoxCellEventHandler;
import com.dangnha.checkers.model.Checker;
import com.dangnha.checkers.model.CheckerBoard;
import com.dangnha.checkers.model.NormalChecker;
import com.dangnha.checkers.model.Position;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import com.dangnha.checkers.constants.*;
import javafx.scene.layout.HBox;

public class CheckBoardView extends GridPane {
    private static CheckBoardView instance;

    private CheckBoardView() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new CheckerHBoxCellEventHandler());
    }

    public static CheckBoardView getInstance() {
        if (instance == null) instance = new CheckBoardView();
        return instance;
    }

    /**
     * Create checker from State of the {@link com.dangnha.checkers.model.CheckerBoard}
     *
     * @param checkerType is the type in {@link com.dangnha.checkers.model.CheckerBoard} state. Ex: KB, KW, B, W
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
     *
     * @param boardModel is the current {@link CheckerBoard}
     */
    public void refreshBoardView(CheckerBoard boardModel) {
        String[][] boardModelStates = boardModel.getBoardStates();
        for (int y = 0; y < boardModelStates.length; y++) {
            for (int x = 0; x < boardModelStates.length; x++) {
                String cell = boardModelStates[y][x];
                if (cell.equalsIgnoreCase("0"))
                    continue;
                if (cell.equalsIgnoreCase("1")) {
                    HBox darkCell = createCheckerBox();

                    this.add(darkCell, x, y);
                } else {

                    Checker checker = checkersFactory(cell, x, y);
                    Button checkerButton = createCheckerButton(checker);


                    this.add(checkerButton, x, y);
                }
            }
        }
    }

    private HBox createCheckerBox() {
        HBox darkCell = new HBox();
        darkCell.getStyleClass().add("dark-cell");
        darkCell.setPrefWidth(BoardConstant.CELL_WIDTH);
        darkCell.setPrefHeight(BoardConstant.CELL_HEIGHT);
        darkCell.setAlignment(Pos.CENTER);
        return darkCell;
    }

    private Button createCheckerButton(Checker checker) {
        String iconLink = this.getClass().getResource(checker.getIconLink()).toString();
        Image img = new Image(iconLink);
        ImageView imgView = new ImageView(img);
        imgView.getStyleClass().add("checker-icon");
        imgView.setFitWidth(50);
        imgView.setFitHeight(50);

        Button checkerButton = new Button();

        checkerButton.getStyleClass().add("checker-button");
        checkerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new CheckerButtonEventHandler());

        checkerButton.setPrefWidth(CheckerConstant.CHESS_WIDTH);
        checkerButton.setPrefHeight(CheckerConstant.CHESS_HEIGHT);
        checkerButton.setGraphic(imgView);

        return checkerButton;
    }


}




