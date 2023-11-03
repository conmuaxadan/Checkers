package com.dangnha.checkers.eventsHandler;

import com.dangnha.checkers.controller.CheckerBoardController;
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

        System.out.println(checkerBoardController.getCurrentCheckerPos() + "\t" + checkerBoardController.getNewCheckerPos());
        // place checker
        if (checkerBoardController.getCurrentCheckerPos() != null) {
            checkerBoardController.placeChecker(checkerBoardController.getCurrentCheckerPos(), checkerBoardController.getNewCheckerPos());

            checkerBoardController.setCurrentCheckerPos(null);
            checkerBoardController.setNewCheckerPos(null);

        }

    }

}