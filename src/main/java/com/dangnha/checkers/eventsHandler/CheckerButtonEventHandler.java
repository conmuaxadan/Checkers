package com.dangnha.checkers.eventsHandler;

import com.dangnha.checkers.controller.CheckerBoardController;
import com.dangnha.checkers.model.Position;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CheckerButtonEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
        int currentX = Integer.MIN_VALUE, currentY = Integer.MIN_VALUE;

        CheckerBoardController checkerBoardController = CheckerBoardController.getInstance();

        if (mouseEvent.getTarget() instanceof Button) {
            currentX = GridPane.getColumnIndex((Node) mouseEvent.getTarget());
            currentY = GridPane.getRowIndex((Node) mouseEvent.getTarget());
        }

        checkerBoardController.setCurrentCheckerPos(new Position(currentX, currentY));

    }
}
