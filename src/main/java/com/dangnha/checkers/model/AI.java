package com.dangnha.checkers.model;

public class AI {
    private static AI instance;

    private AI() {
    }

    public static AI getInstance() {
        if (instance == null)
            instance = new AI();
        return instance;
    }

    public int minimax(int depth, boolean isMaximizePlayer, CheckerBoard board) {
        if (depth == 0)
            return board.heuristic();

        if (isMaximizePlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (CheckerBoard neighbour : board.generateNeighbours()) {
                int value = minimax(depth - 1, false, neighbour);
                bestValue = Math.max(bestValue, value);
            }
            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (CheckerBoard neighbour : board.generateNeighbours()) {
                int value = minimax(depth - 1, true, neighbour);
                bestValue = Math.min(bestValue, value);
            }
            return bestValue;
        }
    }

    public int alphaBeta(int depth, CheckerBoard board, boolean isMaximizePlayer, int alpha, int beta) {
        if (depth == 0)
            return board.heuristic();

        // max -> update alpha
        if (isMaximizePlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (CheckerBoard neighbour : board.generateNeighbours()) {
                int value = alphaBeta(depth, neighbour, false, alpha, beta);
                bestValue = Math.max(bestValue, value);
                alpha = Math.max(alpha, bestValue);
                if (alpha >= beta)
                    break;
            }
            return bestValue;
        } else {
            // min -> update beta
            int bestValue = Integer.MAX_VALUE;
            for (CheckerBoard neighbour : board.generateNeighbours()) {
                int value = alphaBeta(depth, neighbour, false, alpha, beta);
                bestValue = Math.min(bestValue, value);
                alpha = Math.min(alpha, bestValue);
                if (alpha >= beta)
                    break;
            }
            return bestValue;
        }

    }

    public CheckerBoard findBestMove(int depth, CheckerBoard board) {
        int bestValue = Integer.MIN_VALUE;
        CheckerBoard bestMove = null;

        for (CheckerBoard neighbour : board.generateNeighbours()) {
            int value = minimax(depth - 1, false, neighbour);
//            int value = alphaBeta(5, neighbour, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (value > bestValue) {
                bestValue = value;
                bestMove = neighbour;
            }
        }
        return bestMove;
    }


}
