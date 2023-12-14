package com.dangnha.checkers.constants;

public enum GameDifficult {
    HARD(7),
    MEDIUM(5),
    EASY(7);

    private int depth;
    private GameDifficult(int depth){
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }
}
