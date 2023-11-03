package com.dangnha.checkers.controller;

import com.dangnha.checkers.model.Position;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CheckerHBoxCellEventHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
        int newX = Integer.MIN_VALUE, newY = Integer.MIN_VALUE;

        CheckerBoardController checkerBoardController = CheckerBoardController.getInstance();

        if (mouseEvent.getTarget() instanceof HBox) {
            newX = GridPane.getColumnIndex((Node) mouseEvent.getTarget());
            newY = GridPane.getRowIndex((Node) mouseEvent.getTarget());
        }

        checkerBoardController.setNewCheckerPos(new Position(newX, newY));


        System.out.println("haha");
    }

}