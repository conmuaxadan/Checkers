package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.CheckerConstant;

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
    public List<Position> getValidPositions(CheckerBoard board) {
        return null;
    }
}
