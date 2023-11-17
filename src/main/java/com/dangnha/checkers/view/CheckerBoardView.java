package com.dangnha.checkers.view;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.constants.CheckerConstant;
import com.dangnha.checkers.eventsHandler.CheckerButtonEventHandler;
import com.dangnha.checkers.eventsHandler.CheckerPaneCellEventHandler;
import com.dangnha.checkers.model.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;

public class CheckerBoardView extends GridPane {
    private static CheckerBoardView instance;

    private CheckerBoardView() {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new CheckerPaneCellEventHandler());
    }

    public static CheckerBoardView getInstance() {
        if (instance == null) instance = new CheckerBoardView();
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
                return new KingChecker(CheckerConstant.CHESS_TYPE_KING_BLACK, pos);
            }
            case CheckerConstant.CHESS_TYPE_KING_WHITE -> {
                return new KingChecker(CheckerConstant.CHESS_TYPE_KING_WHITE, pos);
            }
        }
        return null;
    }

    /**
     * Refresh {@link CheckerBoardView} after {@link CheckerBoard} changes.
     * <br>
     * Because each {@link CheckerBoard} has only one states.
     *
     * @param boardModel is the current {@link CheckerBoard}
     */
    public void refreshBoardView(CheckerBoard boardModel) {

        String[][] boardModelStates = boardModel.getBoardStates();
//        System.out.println(boardModel);
        for (int y = 0; y < boardModelStates.length; y++) {
            for (int x = 0; x < boardModelStates.length; x++) {
                String cell = boardModelStates[y][x];
                if (cell.equalsIgnoreCase("0"))
                    continue;
                if (cell.equalsIgnoreCase("1")) {
                    Pane darkCell = createCheckerBox();

                    this.add(darkCell, x, y);
                } else {
                    HBox checkerButtonBox = new HBox();
                    checkerButtonBox.getStyleClass().add("dark-cell");

                    Checker checker = checkersFactory(cell, x, y);
                    Button checkerButton = createCheckerButton(checker);
                    checkerButtonBox.getChildren().add(checkerButton);
                    checkerButtonBox.setAlignment(Pos.CENTER);

                    this.add(checkerButtonBox, x, y);
                }
            }
        }
    }

    private Pane createCheckerBox() {
        RowConstraints rowConstraints = new RowConstraints(BoardConstant.CELL_WIDTH);
        this.getRowConstraints().add(rowConstraints);
        ColumnConstraints columnConstraints = new ColumnConstraints(BoardConstant.CELL_HEIGHT);
        this.getColumnConstraints().add(columnConstraints);

        Pane darkCell = new Pane();
        darkCell.getStyleClass().add("dark-cell");

        return darkCell;
    }

    private Button createCheckerButton(Checker checker) {
        try {
            String iconLink = new File(checker.getIconLink()).toURI().toString();
            Image img = new Image(iconLink);
            ImageView imgView = new ImageView(img);
            imgView.getStyleClass().add("checker-icon");
            imgView.setFitWidth(40);
            imgView.setFitHeight(40);

            Button checkerButton = new Button();
            checkerButton.setGraphic(imgView);

            checkerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new CheckerButtonEventHandler());
            checkerButton.getStyleClass().add("checker-button");

            return checkerButton;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void highlightCell(int x, int y) {
        for (Node node : this.getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                node.getStyleClass().add("highlight-cell");
            }

        }
    }

}




