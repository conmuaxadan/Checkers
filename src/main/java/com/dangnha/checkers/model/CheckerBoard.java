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
    private boolean isBlackTurn;

    public CheckerBoard() {
        this.boardStates = new String[BoardConstant.N][BoardConstant.N];
        checkerList = new ArrayList<>();
        initBoard();
        attackState = false;
        isBlackTurn = true;

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

            Checker currentChessman = findCheckerByPosition(currentX, currentY);

            if (isBlackTurn && (currentChessman.getCheckerType().equals(CheckerConstant.CHESS_TYPE_BLACK) || currentChessman.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_BLACK))) {
                setCheckerPosition(currentX, currentY, newX, newY, currentChessman);
                isBlackTurn = false;
            }

            if (!isBlackTurn && (currentChessman.getCheckerType().equals(CheckerConstant.CHESS_TYPE_WHITE) || currentChessman.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_WHITE))) {
                setCheckerPosition(currentX, currentY, newX, newY, currentChessman);
                System.out.println("White heuristic: " + this.heuristic());
                isBlackTurn = true;
            }
        }
    }

    private void setCheckerPosition(int currentX, int currentY, int newX, int newY, Checker currentChessman) {
        Position newPos = new Position(newX, newY);
        List<Position> attackPosList = new ArrayList<>();

        if (currentChessman.isValid(newPos, this)) {

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

    public List<CheckerBoard> generateNeighbours() {
        List<CheckerBoard> result = new ArrayList<>();

        CheckerBoard checkerBoardClone = new CheckerBoard(this.boardStates, this.checkerList);
        CheckerBoard neighbour = null;
        for (Checker checker : checkerBoardClone.checkerList) {
            for (Position pos : checker.getValidPositions(checkerBoardClone)) {
                checkerBoardClone = new CheckerBoard(this.boardStates, this.checkerList);
                neighbour = new CheckerBoard(checkerBoardClone.boardStates, checkerBoardClone.checkerList);
                neighbour.placeChecker(checker.getPosition().getX(), checker.getPosition().getY(), pos.getX(), pos.getY());
                result.add(neighbour);
            }
        }

        return result;

    }

    public int heuristic() {
        int blackPieces = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_BLACK)).count();
        int blackKings = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_BLACK)).count();

        int whitePieces = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_WHITE)).count();
        int whiteKings = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_WHITE)).count();

        int countAttackPosWhite = 0;
        for (Checker checker : checkerList) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_WHITE)) {
                for (Position validPos : checker.getValidPositions(this)
                ) {
                    if (validPos.isAttackPos())
                        countAttackPosWhite++;
                }
            }
        }

        int countAttackPosBlack = 0;
        for (Checker checker : checkerList) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_BLACK)) {
                for (Position validPos : checker.getValidPositions(this))
                    if (validPos.isAttackPos())
                        countAttackPosBlack++;
            }
        }

        return (-blackPieces + whitePieces) + (-blackKings * 5 + whiteKings * 5) + (-countAttackPosBlack * 2 + countAttackPosWhite * 2);
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

    public boolean isAttackState() {
        return attackState;
    }

    public void setAttackState(boolean attackState) {
        this.attackState = attackState;
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        isBlackTurn = blackTurn;
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
//        board.placeChecker(2, 3, 3, 2);
//        board.placeChecker(3, 2, 1, 2);
//        board.placeChecker(1, 0, 0, 1);
//        board.placeChecker(3, 2, 2, 1);
//        board.placeChecker(2, 1, 1, 0);

        System.out.println(board.heuristic());

//        System.out.println(board);
    }
}
