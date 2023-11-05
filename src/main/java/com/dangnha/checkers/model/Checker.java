package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.utils.CheckerUtils;

import java.util.List;

public abstract class Checker {
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
                            result.add(new Position(leftPos - 1, aheadPosIfCan, true));
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
                System.out.println(this.getCheckerType() + " " + board.getBoardStates()[aheadPos][rightPos]);
                System.out.println(CheckerUtils.isOpponent(this.getCheckerType(), board.getBoardStates()[aheadPos][rightPos]));
                if (CheckerUtils.isOpponent(this.getCheckerType(), board.getBoardStates()[aheadPos][rightPos])) {
                    if (rightPos + 1 < BoardConstant.N && (aheadPosIfCan >= 0 && aheadPosIfCan < BoardConstant.N))
                        if (board.getBoardStates()[aheadPosIfCan][rightPos + 1].equals("1")) {
                            result.add(new Position(rightPos + 1, aheadPosIfCan, true));
                        }

                }
            }
        }
    }
}
