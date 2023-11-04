package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.constants.CheckerConstant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckerBoard {
    private String[][] boardStates;
    private List<Checker> checkerList;
    private boolean attackState;

    public CheckerBoard() {
        this.boardStates = new String[BoardConstant.N][BoardConstant.N];
        checkerList = new ArrayList<>();
        initBoard();
        attackState = false;

    }

    public CheckerBoard(String[][] boardStates, List<Checker> checkerList) {
        this.boardStates = new String[BoardConstant.N][BoardConstant.N];
        this.checkerList = new ArrayList<>(checkerList);
        for (int i = 0; i < boardStates.length; i++) {
            System.arraycopy(boardStates[i], 0, this.boardStates[i], 0, this.boardStates.length);
        }
        refreshBoard();
    }

    public void generateBoard() {
        int cellState = 0;

        for (int i = 0; i < BoardConstant.N; i++) {
            for (int j = 0; j < BoardConstant.N; j++) {
                // even rows
                if (i % 2 == 0) {
                    if (j % 2 == 0)
                        cellState = 0;
                    else cellState = 1;
                }

                // odd rows
                if (i % 2 != 0) {
                    if (j % 2 == 0)
                        cellState = 1;
                    else cellState = 0;
                }

                this.boardStates[i][j] = String.valueOf(cellState);
            }
        }

    }

    private void initBoard() {
        generateBoard();

        // generate white
        int countWhite = 0;
        for (int y = 0; y < BoardConstant.N / 2; y++) {
            for (int x = 0; x < BoardConstant.N; x++) {

                if (countWhite < BoardConstant.CHESS_NUMBERS_PER_SIDE && boardStates[y][x].equals("1")) {
                    Checker white = new NormalChecker(CheckerConstant.CHESS_TYPE_WHITE, new Position(x, y));
                    this.checkerList.add(white);
                    refreshBoard();
                    countWhite++;
                }
            }
        }


        int countBlack = 0;
        for (int y = BoardConstant.N - 1; y >= BoardConstant.N / 2; y--) {
            for (int x = 0; x < BoardConstant.N; x++) {

                if (countBlack < BoardConstant.CHESS_NUMBERS_PER_SIDE && boardStates[y][x].equals("1")) {
                    Checker black = new NormalChecker(CheckerConstant.CHESS_TYPE_BLACK, new Position(x, y));
                    this.checkerList.add(black);
                    refreshBoard();
                    countBlack++;
                }
            }
        }
    }

    public void refreshBoard() {
        generateBoard();
        for (Checker checker : checkerList
        ) {
            this.boardStates[checker.getPosition().getY()][checker.getPosition().getX()] = checker.checkerType;
        }
    }

    public Checker findCheckerByPosition(int x, int y) {
        for (Checker checker : checkerList
        ) {
            if (checker.position.getX() == x && checker.position.getY() == y)
                return checker;
        }
        return null;
    }

    /////////
    // GETTERS AND SETTERS
    ////////
    public String[][] getBoardStates() {
        return boardStates;
    }

    public void placeChecker(int currentX, int currentY, int newX, int newY) {
        if (currentX != Integer.MIN_VALUE && currentY != Integer.MIN_VALUE && newX != Integer.MIN_VALUE && newY != Integer.MIN_VALUE) {

            Position newPos = new Position(newX, newY);
            Checker currentChessman = findCheckerByPosition(currentX, currentY);
            System.out.println("Get valid pos: " + currentChessman.getValidPositions(this));
            if (currentChessman != null && !currentChessman.isAttacken() && currentChessman.isValid(newPos, this)) {

                // check if new pos is an attack pos
                if (currentChessman.isAttackPos(newPos, this)) {
                    int opponentX = (currentX + newX) / 2;
                    int opponentY = (currentY + newY) / 2;

                    Checker opponentChecker = findCheckerByPosition(opponentX, opponentY);

                    removeAttackedChecker(opponentChecker);


                }

                currentChessman.position.setX(newX);
                currentChessman.position.setY(newY);
                System.out.println("checker placed!");
                makeKingChecker(newX, newY);
            }
        }
    }

    public void removeAttackedChecker(Checker attackedChecker) {
        this.boardStates[attackedChecker.getPosition().getY()][attackedChecker.getPosition().getX()] = "1";
        this.checkerList.remove(attackedChecker);
    }

    public void makeKingChecker(int x, int y) {
        Checker checker = findCheckerByPosition(x, y);
        if (checker == null)
            return;
        else {
            Position currentPos = checker.getPosition();
            Checker newChecker = null;
            if (checker.checkerType.equals(CheckerConstant.CHESS_TYPE_WHITE) && y == BoardConstant.N - 1) {
                newChecker = new KingChecker(CheckerConstant.CHESS_TYPE_KING_WHITE, currentPos);
                this.checkerList.remove(checker);
                this.checkerList.add(newChecker);

            }

            if (checker.checkerType.equals(CheckerConstant.CHESS_TYPE_BLACK) && y == 0) {
                newChecker = new KingChecker(CheckerConstant.CHESS_TYPE_KING_BLACK, currentPos);
                this.checkerList.remove(checker);
                this.checkerList.add(newChecker);
            }

            refreshBoard();
        }
    }

    public void setBoardStates(String[][] boardStates) {
        this.boardStates = boardStates;
    }

    public List<Checker> getCheckerList() {
        return checkerList;
    }

    public void setCheckerList(List<Checker> checkerList) {
        this.checkerList = checkerList;
    }

    @Override
    public String toString() {
        String result = "";

        for (String[] row : this.boardStates
        ) {
            result += Arrays.toString(row) + "\n";
        }
        return result;
    }


    public static void main(String[] args) {
        CheckerBoard board = new CheckerBoard();

//        board.placeChessman(1, 0, 0, 1);
//        board.placeChessman(3,0,2,1);
//        board.placeChessman(0, 1, 1, 2);
//        board.placeChessman(2, 1, 3, 2);
//        board.placeChessman(1, 4, 2, 3);
//        board.placeChessman(3, 4, 4, 3);
//        board.placeChessman(1, 2, 3, 4);
//        board.placeChessman(4, 5, 2, 3);
        board.placeChecker(2, 3, 3, 2);
        board.placeChecker(3, 2, 1, 2);
        board.placeChecker(1, 0, 0, 1);
        board.placeChecker(3, 2, 2, 1);
        board.placeChecker(2, 1, 1, 0);


        System.out.println(board);
    }
}
