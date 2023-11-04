package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.constants.CheckerConstant;

import java.util.ArrayList;
import java.util.List;

public class NormalChecker extends Checker {
    public NormalChecker(String checkerType, Position position) {
        super(checkerType, position);
        switch (checkerType) {
            case CheckerConstant.CHESS_TYPE_BLACK -> iconLink = CheckerConstant.CHESS_BLACK_IMG_PATH;
            case CheckerConstant.CHESS_TYPE_WHITE -> iconLink = CheckerConstant.CHESS_WHITE_IMG_PATH;
        }
    }

    @Override
    public List<Position> getValidPositions(CheckerBoard board) {
        List<Position> result = new ArrayList<>();
        Checker attackenChecker = null;

        int aheadPos = 0, aheadPosIfCan = 0, leftPos, rightPos;
        if (this.checkerType.equals(CheckerConstant.CHESS_TYPE_WHITE)) {
            aheadPos = this.position.getY() + 1;
            if (aheadPos == BoardConstant.N)
                return null;
            aheadPosIfCan = aheadPos + 1;
        }
        if (this.checkerType.equals(CheckerConstant.CHESS_TYPE_BLACK)) {
            aheadPos = this.position.getY() - 1;
            if (aheadPos < 0)
                return null;
            aheadPosIfCan = aheadPos - 1;
        }
        leftPos = this.position.getX() - 1;
        rightPos = this.position.getX() + 1;

        // check left
        if (leftPos >= 0) {
            if (board.getBoardStates()[aheadPos][leftPos].equals("1"))
                result.add(new Position(leftPos, aheadPos));
            else {
                // if this is an opponent chessman
                if (!board.getBoardStates()[aheadPos][leftPos].equals(this.getCheckerType())) {
                    if (leftPos - 1 >= 0 && aheadPosIfCan < BoardConstant.N)
                        if (board.getBoardStates()[aheadPosIfCan][leftPos - 1].equals("1")) {
                            result.add(new Position(leftPos - 1, aheadPosIfCan, true));
                        }
                }
            }
        }

        // check right
        if (rightPos < BoardConstant.N) {
            if (board.getBoardStates()[aheadPos][rightPos].equals("1"))
                result.add(new Position(rightPos, aheadPos));
            else {
                // if this is an opponent chessman
                if (!board.getBoardStates()[aheadPos][rightPos].equals(this.getCheckerType())) {
                    if (rightPos + 1 < BoardConstant.N) {
                        if (board.getBoardStates()[aheadPosIfCan][rightPos + 1].equals("1")) {
                            result.add(new Position(rightPos + 1, aheadPosIfCan, true));
                        }
                    }
                }
            }
        }
        return result;
    }


}
