package com.dangnha.checkers.model;

import com.dangnha.checkers.constants.GameDifficult;
import com.dangnha.checkers.constants.GameState;

public class AI {
    private static AI instance;

    private AI() {
    }

    public static AI getInstance() {
        if (instance == null)
            instance = new AI();
        return instance;
    }

    /**
     * Get value for min node
     * @param board is the current Checker board
     * @param alpha is the alpha value
     * @param beta is the beta value
     * @param depth is the expectation depth
     * @return min value
     */
    public int minValue(CheckerBoard board, int alpha, int beta, int depth) {
        if (depth == 0) {
            return board.heuristic();
        }

        int value = Integer.MAX_VALUE;
        for (CheckerBoard neighbour : board.generateNeighbours()) {
            value = Math.min(value, maxValue(neighbour, alpha, beta, depth - 1));
            if (alpha >= value) return value;
            beta = Math.min(beta, value);
        }
        return value;
    }


    /**
     * Get value for max node
     * @param board is the current Checker board
     * @param alpha is the alpha value
     * @param beta is the beta value
     * @param depth is the expectation depth
     * @return max value
     */
    public int maxValue(CheckerBoard board, int alpha, int beta, int depth) {
        if (depth == 0) {
            return board.heuristic();
        }

        int value = Integer.MIN_VALUE;
        for (CheckerBoard neighbour : board.generateNeighbours()) {
            value = Math.max(value, minValue(neighbour, alpha, beta, depth - 1));
            if (value >= beta) return value;
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    /**
     * Alpha Beta Pruning to get the best {@link CheckerBoard} to place for each move of AI
     * @param board this is a current board
     * @param depth is the expectation depth
     * @return a {@link CheckerBoard} that have best max value (because AI play first and play as Maximize player)
     */
    public CheckerBoard alphaBetaSearch(CheckerBoard board, int depth) {
        CheckerBoard result = null;
        int bestValue = Integer.MIN_VALUE;

        for (CheckerBoard neighbour : board.generateNeighbours()) {
            int value = maxValue(neighbour, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

            if (value > bestValue) {
                bestValue = value;
                result = neighbour;
            }
        }
        return result;
    }


    /**
     * Get the best move for each turn of AI by using {@link #alphaBetaSearch(CheckerBoard, int)}
     * @param currentBoard this is a current board
     * @param gameDifficult this enum include depth
     * @return {@link CheckerBoard} that most suitable
     */
    public CheckerBoard getBestMove(CheckerBoard currentBoard, GameDifficult gameDifficult) {
        return alphaBetaSearch(currentBoard, gameDifficult.getDepth());
    }


}
