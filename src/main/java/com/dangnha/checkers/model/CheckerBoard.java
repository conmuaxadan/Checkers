package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.constants.CheckerConstant;
import com.dangnha.checkers.constants.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        try {
            this.boardStates = new String[BoardConstant.N][BoardConstant.N];
            this.checkerList = new ArrayList<>();

            for (Checker checker : checkerList) {
                this.checkerList.add((Checker) checker.clone());
            }

            for (int i = 0; i < boardStates.length; i++) {
                System.arraycopy(boardStates[i], 0, this.boardStates[i], 0, this.boardStates.length);
            }
            refreshBoard();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate board states with 0 and 1
     */
    public void generateBoard() {
        int cellState = 0;

        for (int i = 0; i < BoardConstant.N; i++) {
            for (int j = 0; j < BoardConstant.N; j++) {
                // even rows
                if (i % 2 == 0) {
                    if (j % 2 == 0) cellState = 0;
                    else cellState = 1;
                }

                // odd rows
                if (i % 2 != 0) {
                    if (j % 2 == 0) cellState = 1;
                    else cellState = 0;
                }

                this.boardStates[i][j] = String.valueOf(cellState);
            }
        }

    }

    /**
     * Generate board states with 0 and 1 and place checkers to on the board <br>
     * 0: empty cell <br>
     * 1: cell with no checker <br>
     * W: cell with white checker <br>
     * B: cell with black checker <br>
     * KB: cell with king black checker <br>
     * KW: cell with king white checker
     */
    private void initBoard() {
        generateBoard();

        // generate white
        int countWhite = 0;
        for (int y = 0; y < BoardConstant.N / 2; y++) {
            for (int x = 0; x < BoardConstant.N; x++) {

                if (countWhite < BoardConstant.CHESS_NUMBERS_PER_SIDE && boardStates[y][x].equals("1")) {
                    Checker white = new NormalChecker(CheckerConstant.CHESS_TYPE_BLACK, new Position(x, y));
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
                    Checker black = new NormalChecker(CheckerConstant.CHESS_TYPE_WHITE, new Position(x, y));
                    this.checkerList.add(black);
                    refreshBoard();
                    countBlack++;
                }
            }
        }
    }

    /**
     * Refresh board states with checkers. <br>
     * Place checker to the board by checker position in checker list
     */
    public void refreshBoard() {
        generateBoard();
        for (Checker checker : checkerList) {
            this.boardStates[checker.getPosition().getY()][checker.getPosition().getX()] = checker.checkerType;
        }
    }

    /**
     * Find checker by position
     *
     * @param x x position
     * @param y y position
     * @return Checker
     */
    public Checker findCheckerByPosition(int x, int y) {
        for (Checker checker : checkerList) {
            if (checker.position.getX() == x && checker.position.getY() == y) return checker;
        }
        return null;
    }

    /////////
    // GETTERS AND SETTERS
    ////////
    public String[][] getBoardStates() {
        return boardStates;
    }

    /**
     * Place checker to the board
     */
    public boolean setCheckerPosition(Position currentPos, Position newPos) {
        int currentX = currentPos.getX();
        int currentY = currentPos.getY();
        int newX = newPos.getX();
        int newY = newPos.getY();

        Checker currentChecker = findCheckerByPosition(currentX, currentY);

        if (currentChecker.isValid(newPos, this)) {
            // check if new pos is an attack pos
            if (currentChecker.isAttackPos(newPos, this)) {
                int opponentX = (currentX + newX) / 2;
                int opponentY = (currentY + newY) / 2;

                Checker opponentChecker = findCheckerByPosition(opponentX, opponentY);

                removeAttackedChecker(opponentChecker);
            }

            currentChecker.position.setX(newX);
            currentChecker.position.setY(newY);

            // make king checker if possible
            makeKingChecker(newX, newY);

            return true;
        }
        return false;
    }

    /**
     * Remove attacked checker from the board
     *
     * @param attackedChecker attacked checker
     */
    public void removeAttackedChecker(Checker attackedChecker) {
        this.boardStates[attackedChecker.getPosition().getY()][attackedChecker.getPosition().getX()] = "1";
        this.checkerList.remove(attackedChecker);
    }

    /**
     * Make king checker when the checker arrived the end of the board (y = 0 or y = 7)
     */
    public void makeKingChecker(int x, int y) {
        Checker checker = findCheckerByPosition(x, y);
        if (checker == null) return;
        else {
            int makeKingY = -1;
            if (checker.checkerType.equals(CheckerConstant.CHESS_TYPE_BLACK)) makeKingY = BoardConstant.N - 1;
            if (checker.checkerType.equals(CheckerConstant.CHESS_TYPE_WHITE)) makeKingY = 0;

            Position currentPos = checker.getPosition();
            Checker newChecker = null;
            if (checker.checkerType.equals(CheckerConstant.CHESS_TYPE_WHITE) && y == makeKingY) {
                newChecker = new KingChecker(CheckerConstant.CHESS_TYPE_KING_WHITE, currentPos);
                this.checkerList.remove(checker);
                this.checkerList.add(newChecker);
            }

            if (checker.checkerType.equals(CheckerConstant.CHESS_TYPE_BLACK) && y == makeKingY) {
                newChecker = new KingChecker(CheckerConstant.CHESS_TYPE_KING_BLACK, currentPos);
                this.checkerList.remove(checker);
                this.checkerList.add(newChecker);
            }
        }
        refreshBoard();
    }


    public List<CheckerBoard> generateNeighbours(boolean isBlackTurn) {
        List<CheckerBoard> result = new ArrayList<>();

        CheckerBoard neighbour = null;
        CheckerBoard cloneBoard = new CheckerBoard(this.boardStates, this.checkerList);
        for (Checker checker : cloneBoard.checkerList) {
            if (checker.isValidTurn(isBlackTurn)) {
                for (Position pos : checker.getValidPositions(this)) {
                    neighbour = new CheckerBoard(this.boardStates, this.checkerList);
                    neighbour.setCheckerPosition(checker.position, pos);
                    result.add(neighbour);
                }
            }
        }

        return result;
    }

    public int heuristic() {
        int result = Integer.MIN_VALUE;

        // count normal pieces and king pieces (checkers) for each team
        // if you want to increase priority attack of checkers, you should increase point of it
        int blackPieces = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_BLACK)).count();
        int blackKings = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_BLACK)).count();

        int whitePieces = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_WHITE)).count();
        int whiteKings = (int) checkerList.stream().filter(checker -> checker.getCheckerType().equals(CheckerConstant.CHESS_TYPE_KING_WHITE)).count();

        int countAttackPosWhite = 0;
        for (Checker checker : checkerList) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_WHITE)) {
                for (Position validPos : checker.getValidPositions(this)) {
                    if (validPos.isAttackPos()) countAttackPosWhite++;
                }
            }
        }

        int countAttackPosBlack = 0;
        for (Checker checker : checkerList) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_BLACK)) {
                for (Position validPos : checker.getValidPositions(this))
                    if (validPos.isAttackPos()) countAttackPosBlack++;
            }
        }

        int countWhiteCheckersNearKingY = 0;
        int countBlackCheckersNearKingY = 0;

        for (Checker checker : checkerList) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_WHITE)) {
                // checker nears position to make king (WHITE -> makeKingY = 0; BLACK -> makeKingY = n - 1)
                if (checker.getPosition().getY() == 1 || checker.getPosition().getY() == 2)
                    countWhiteCheckersNearKingY++;
            }
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_BLACK)) {
                // checker nears position to make king (WHITE -> makeKingY = 0; BLACK -> makeKingY = n - 1)
                if (checker.getPosition().getY() == BoardConstant.N - 1 || checker.getPosition().getY() == BoardConstant.N - 2)
                    countBlackCheckersNearKingY++;
            }
        }

        // if black have at least one king and white has smaller kings than black then priority attack
        if (blackKings >= 1 && whiteKings <= blackKings) {
            result = 10 * (blackPieces - whitePieces) + 3 * (blackKings - whiteKings) + 4 * (countAttackPosBlack - countAttackPosWhite)
                    + 2 * (countBlackCheckersNearKingY - countWhiteCheckersNearKingY);
        } else {
            result = 8 * (blackPieces - whitePieces) + 5 * (blackKings - whiteKings) + 4 * (countAttackPosBlack - countAttackPosWhite)
                    + 3 * (countBlackCheckersNearKingY - countWhiteCheckersNearKingY);
        }

        return result;
    }

    /**
     * Check states of the game. Ex: GAME_OVER, DRAW,...
     */
    public GameState gameOver() {
        int countAvailableMovesBlack = 0;
        for (Checker checker : this.getCheckerList()
        ) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_BLACK) && checker.getValidPositions(this) != null) {
                countAvailableMovesBlack += checker.getValidPositions(this).size();
            }
        }

        int countAvailableMovesWhite = 0;
        for (Checker checker : this.getCheckerList()
        ) {
            if (checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_WHITE) && checker.getValidPositions(this) != null) {
                countAvailableMovesWhite += checker.getValidPositions(this).size();
            }
        }

        int countBlack = (int) this.getCheckerList().stream().filter(checker -> checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_BLACK)).count();
        int countWhite = (int) this.getCheckerList().stream().filter(checker -> checker.getCheckerType().endsWith(CheckerConstant.CHESS_TYPE_WHITE)).count();

        if (countAvailableMovesBlack == 0 && countAvailableMovesWhite > 0)
            return GameState.BLACK_LOSE;

        if (countAvailableMovesWhite == 0 && countAvailableMovesBlack > 0)
            return GameState.WHITE_LOSE;

        if (countAvailableMovesWhite == 0 && countAvailableMovesBlack == 0)
            return GameState.DRAW;

        if (countWhite == 0)
            return GameState.WHITE_LOSE;

        if (countBlack == 0)
            return GameState.BLACK_LOSE;

        return GameState.CONTINUE;
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

        for (String[] row : this.boardStates) {
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

    public static void main(String[] args) {
        CheckerBoard board = new CheckerBoard();

//        Checker checker = board.findCheckerByPosition(1, 2);
//        System.out.println(checker.getValidPositions(board));

        CheckerBoard n1 = board.generateNeighbours(true).get(0);

        System.out.println(n1.generateNeighbours(false));


        AI ai = AI.getInstance();
//        System.out.println(ai.minimax(14, true, board));
    }

}
