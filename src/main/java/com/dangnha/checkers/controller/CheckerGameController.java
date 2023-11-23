package com.dangnha.checkers.controller;

import com.dangnha.checkers.model.CheckerBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;


public class CheckerGameController {
   @FXML
    private Label turnLabel;
   @FXML
    private RadioButton playWithAIRadio;
   @FXML
    private RadioButton playWithHumanRadio;
   @FXML
    private Button resetGame;
   @FXML
    private Button playGame;

   private GameController gameController = GameController.getInstance();

   @FXML
    protected void setPlayWithAI() {
       gameController.setPlayWithAI(true);
    }

    @FXML
    protected void setPlayWithHuman() {
       gameController.setPlayWithAI(false);
    }


}
