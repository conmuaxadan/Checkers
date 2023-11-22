package com.dangnha.checkers.eventsHandler;

import com.dangnha.checkers.controller.CheckerBoardController;
import com.dangnha.checkers.model.Position;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class CheckerButtonEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
        int currentX = Integer.MIN_VALUE, currentY = Integer.MIN_VALUE;

        CheckerBoardController checkerBoardController = CheckerBoardController.getInstance();
        if (mouseEvent.getTarget() instanceof Button) {
            System.out.println("test event handler button");

            currentX = GridPane.getColumnIndex(((Button) mouseEvent.getTarget()).getParent());
            currentY = GridPane.getRowIndex(((Button) mouseEvent.getTarget()).getParent());
        }

        checkerBoardController.displayAvailableMoves(new Position(currentX, currentY));
        checkerBoardController.setCurrentCheckerPos(new Position(currentX, currentY));

    }
}
