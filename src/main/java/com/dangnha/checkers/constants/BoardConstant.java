package com.checkers.constants;

public class BoardConstant {
    public static final int N = 6;
    public static final int CELL_WIDTH = 50;
    public static final int CELL_HEIGHT = 50;
    public static final int BOARD_WIDTH = CELL_WIDTH * N;
    public static final int BOARD_HEIGHT = CELL_HEIGHT * N;
    public static final int CHESS_NUMBERS_PER_SIDE = (N/2 - 1) * (N/2);
    public static final String BLACK_CELL_COLOR = "#1a1b1c";
    public static final String WHITE_CELL_COLOR = "#f2f5f7";
}
