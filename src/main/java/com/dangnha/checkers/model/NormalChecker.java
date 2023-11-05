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

        addValidPosToResult(result, leftPos, rightPos, aheadPos, aheadPosIfCan, board);

        return result;
    }


}
