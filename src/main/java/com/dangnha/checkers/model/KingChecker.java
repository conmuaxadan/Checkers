package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.BoardConstant;
import com.dangnha.checkers.constants.CheckerConstant;

import java.util.ArrayList;
import java.util.List;

public class KingChecker extends Checker {
    public KingChecker(String checkerType, Position position) {
        super(checkerType, position);
        switch (checkerType) {
            case CheckerConstant.CHESS_TYPE_KING_BLACK -> iconLink = CheckerConstant.CHESS_KING_BLACK_IMG_PATH;
            case CheckerConstant.CHESS_TYPE_KING_WHITE -> iconLink = CheckerConstant.CHESS_KING_WHITE_IMG_PATH;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        KingChecker newInstance = new KingChecker(this.checkerType, new Position(position.getX(), position.getY()));
        return newInstance;
    }

    @Override
    public List<Position> getValidPositions(CheckerBoard board) {
        List<Position> result = new ArrayList<>();

        result.addAll(getValidPosInTheTop(board));
        result.addAll(getValidPosInTheBottom(board));

//        System.out.println(result);
        return result;
    }

    /**
     * Check valid positions in the bottom for the king
     *
     * @param board is the current checkerboard
     * @return a list of positions that valid for king in the top
     */
    private List<Position> getValidPosInTheTop(CheckerBoard board) {
        List<Position> result = new ArrayList<>();

        int leftPos = this.position.getX() - 1;
        int rightPos = this.position.getX() + 1;
        int aheadPos = Integer.MIN_VALUE;
        int aheadPosIfCan = Integer.MIN_VALUE;

        // check up
        if (this.position.getY() - 1 >= 0) {
            aheadPos = this.position.getY() - 1;
            if (aheadPos > 0)
                aheadPosIfCan = aheadPos - 1;
            else
                aheadPosIfCan = aheadPos;
        } else
            aheadPos = this.getPosition().getY();

        addValidPosToResult(result, leftPos, rightPos, aheadPos, aheadPosIfCan, board);

        return result;
    }

    /**
     * Check valid positions in the bottom for the king
     *
     * @param board is the current checker board
     * @return a list of positions that valid for king in the bottom
     */
    private List<Position> getValidPosInTheBottom(CheckerBoard board) {
        List<Position> result = new ArrayList<>();

        int leftPos = this.position.getX() - 1;
        int rightPos = this.position.getX() + 1;
        int aheadPos = Integer.MAX_VALUE;
        int aheadPosIfCan = Integer.MAX_VALUE;

        // check bottom
        if (this.position.getY() + 1 < BoardConstant.N) {
            aheadPos = this.position.getY() + 1;
            if (aheadPos < BoardConstant.N - 1)
                aheadPosIfCan = aheadPos + 1;
            else
                aheadPosIfCan = aheadPos;
        } else
            aheadPos = this.position.getY();

        addValidPosToResult(result, leftPos, rightPos, aheadPos, aheadPosIfCan, board);
        return result;
    }


}
