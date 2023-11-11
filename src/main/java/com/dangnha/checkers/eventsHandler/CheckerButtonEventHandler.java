package com.dangnha.checkers.eventsHandler;

import com.dangnha.checkers.controller.CheckerBoardController;
import com.dangnha.checkers.model.Position;
import com.dangnha.checkers.view.CheckerBoardView;
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
        System.out.println(mouseEvent.getTarget());
        if (mouseEvent.getTarget() instanceof Button) {

            currentX = GridPane.getColumnIndex(((Button) mouseEvent.getTarget()).getParent());
            currentY = GridPane.getRowIndex(((Button) mouseEvent.getTarget()).getParent());
        }

        checkerBoardController.displayAvailableMoves(new Position(currentX, currentY));

        checkerBoardController.setCurrentCheckerPos(new Position(currentX, currentY));

    }
}
