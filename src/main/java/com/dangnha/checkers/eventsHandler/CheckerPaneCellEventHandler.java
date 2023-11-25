package com.dangnha.checkers.eventsHandler;

import com.dangnha.checkers.controller.CheckerBoardController;
import com.dangnha.checkers.controller.GameController;
import com.dangnha.checkers.model.Position;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CheckerPaneCellEventHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
        int newX = Integer.MIN_VALUE, newY = Integer.MIN_VALUE;

        CheckerBoardController checkerBoardController = CheckerBoardController.getInstance();

        if (mouseEvent.getTarget() instanceof Pane) {
            newX = GridPane.getColumnIndex((Node) mouseEvent.getTarget());
            newY = GridPane.getRowIndex((Node) mouseEvent.getTarget());
        }

        checkerBoardController.setNewCheckerPos(new Position(newX, newY));

        // place checker
        if (checkerBoardController.getCurrentCheckerPos() != null) {
            boolean placeCheckerSuccess = checkerBoardController.placeChecker(checkerBoardController.getCurrentCheckerPos(), checkerBoardController.getNewCheckerPos());

            // reset position after move
            checkerBoardController.setCurrentCheckerPos(null);
            checkerBoardController.setNewCheckerPos(null);

            if (placeCheckerSuccess) {
                GameController instance = GameController.getInstance();
                instance.setBlackTurn(!instance.isBlackTurn());
            }
        }
    }
}