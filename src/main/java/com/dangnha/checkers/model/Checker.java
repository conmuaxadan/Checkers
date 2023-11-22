package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.constants.CheckerConstant;
import com.dangnha.checkers.utils.CheckerUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Checker implements Cloneable {
    protected String checkerType;
    protected Position position;
    protected String iconLink;


    public Checker(String checkerType, Position position) {
        this.checkerType = checkerType;
        this.position = position;

    }

    public boolean isValid(Position newPos, CheckerBoard board) {
        for (Position pos : this.getValidPositions(board)) {
            if (pos.getX() == newPos.getX() && pos.getY() == newPos.getY()) return true;
        }
        return false;
    }

    public boolean isAttackPos(Position newPos, CheckerBoard board) {
        for (Position pos : this.getValidPositions(board)) {
            if (pos.getX() == newPos.getX() && pos.getY() == newPos.getY() && pos.isAttackPos()) return true;
        }
        return false;
    }

    @Override
    protected abstract Object clone() throws CloneNotSupportedException;

    public abstract List<Position> getValidPositions(CheckerBoard board);

    public String getCheckerType() {
        return checkerType;
    }

    public void setCheckerType(String checkerType) {
        this.checkerType = checkerType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }


    @Override
    public String toString() {
        return "CHECKER: " + this.position + ", " + this.checkerType;
    }

    /**
     * Encapsulates algorithm check valid pos in left and right to prevent duplicate codes
     */
    protected void addValidPosToResult(List<Position> result, int leftPos, int rightPos, int aheadPos, int aheadPosIfCan, CheckerBoard board) {
        // check left
        if (leftPos >= 0) {
            if (board.getBoardStates()[aheadPos][leftPos].equals("1")) result.add(new Position(leftPos, aheadPos));
            else {
                // if this is an opponent chessman
                // check if (B or W not in KB or KW) => opponent
                if (CheckerUtils.isOpponent(this.getCheckerType(), board.getBoardStates()[aheadPos][leftPos])) {
                    if (leftPos - 1 >= 0 && (aheadPosIfCan >= 0 && aheadPosIfCan < BoardConstant.N))
                        if (board.getBoardStates()[aheadPosIfCan][leftPos - 1].equals("1")) {

                            if (hasMultipleMoves(leftPos - 1, aheadPosIfCan, board).isEmpty())
                                result.add(new Position(leftPos - 1, aheadPosIfCan, true));
                            else
                                result.addAll(hasMultipleMoves(leftPos - 1, aheadPosIfCan, board));
                        }
                }
            }
        }

        // check right
        if (rightPos < BoardConstant.N) {
            if (board.getBoardStates()[aheadPos][rightPos].equals("1")) result.add(new Position(rightPos, aheadPos));
            else {
                // if this is an opponent chessman
                // check if (B or W not in KB or KW) => opponent
//                System.out.println(this.getCheckerType() + " " + board.getBoardStates()[aheadPos][rightPos]);
//                System.out.println(CheckerUtils.isOpponent(this.getCheckerType(), board.getBoardStates()[aheadPos][rightPos]));
                if (CheckerUtils.isOpponent(this.getCheckerType(), board.getBoardStates()[aheadPos][rightPos])) {
                    if (rightPos + 1 < BoardConstant.N && (aheadPosIfCan >= 0 && aheadPosIfCan < BoardConstant.N))
                        if (board.getBoardStates()[aheadPosIfCan][rightPos + 1].equals("1")) {

                            if (hasMultipleMoves(rightPos + 1, aheadPosIfCan, board).isEmpty())
                                result.add(new Position(rightPos + 1, aheadPosIfCan, true));
                            else
                                result.addAll(hasMultipleMoves(rightPos + 1, aheadPosIfCan, board));
                        }
                }
            }
        }
    }

    private List<Position> hasMultipleMoves(int x, int y, CheckerBoard board) {
        List<Position> result = new ArrayList<>();
//        result.add(new Position(x, y, true));
//        if (x > 0 && x < BoardConstant.N - 1 && y > 0 && y < BoardConstant.N - 1) {
//            //check white
//            if (this.getCheckerType().equals(CheckerConstant.CHESS_TYPE_WHITE) || this.getCheckerType().startsWith("K")) {
//
//                // check right
//                if (CheckerUtils.isOpponent(this.checkerType, board.getBoardStates()[y + 1][x + 1])) {
//                    if (x + 2 < BoardConstant.N && (y + 2 >= 0 && y + 2 < BoardConstant.N)) {
//                        if (board.getBoardStates()[y + 2][x + 2].equals("1"))
//                            result.add(new Position(x + 2, y + 2, true));
//                    }
//                }
//
//                // check left
//                if (CheckerUtils.isOpponent(this.checkerType, board.getBoardStates()[y + 1][x - 1])) {
//                    if (x - 2 >= 0 && (y + 2 >= 0 && y + 2 < BoardConstant.N)) {
//                        if (board.getBoardStates()[y + 2][x - 2].equals("1"))
//                            result.add(new Position(x - 2, y + 2, true));
//                    }
//                }
//            }
//            //check black
//            if (this.getCheckerType().equals(CheckerConstant.CHESS_TYPE_BLACK) || this.getCheckerType().startsWith("K")) {
//
//                // check right
//                if (CheckerUtils.isOpponent(this.checkerType, board.getBoardStates()[y - 1][x + 1])) {
//                    if (x + 2 < BoardConstant.N && (y - 2 >= 0 && y - 2 < BoardConstant.N)) {
//                        if (board.getBoardStates()[y - 2][x + 2].equals("1"))
//                            result.add(new Position(x + 2, y - 2, true));
//                    }
//                }
//
//                // check left
//                if (CheckerUtils.isOpponent(this.checkerType, board.getBoardStates()[y - 1][x - 1])) {
//                    if (x - 2 >= 0 && (y - 2 >= 0 && y - 2 < BoardConstant.N)) {
//                        if (board.getBoardStates()[y - 2][x - 2].equals("1"))
//                            result.add(new Position(x - 2, y - 2, true));
//                    }
//                }
//            }
//        }
        return result;
    }

    public boolean isValidTurn(boolean blackTurnModel) {
        if(checkerType.endsWith(CheckerConstant.CHESS_TYPE_WHITE) && !blackTurnModel)
            return true;
        if(checkerType.endsWith(CheckerConstant.CHESS_TYPE_BLACK) && blackTurnModel)
            return true;
        else return false;
    }
}
