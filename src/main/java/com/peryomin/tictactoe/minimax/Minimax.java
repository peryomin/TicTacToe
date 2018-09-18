package main.java.com.peryomin.tictactoe.minimax;

import main.java.com.peryomin.tictactoe.*;

import java.util.ArrayList;

public class Minimax {
    static final int MAX_SCORE = Integer.MAX_VALUE / 3;
    static final int MIN_SCORE = Integer.MIN_VALUE / 3;
    public static final int MAX_DEPTH = 20;

    private EvaluationFunction eval;

    private long timeToMove;
    private long startTime;

    public Minimax(EvaluationFunction eval) {
        this.eval = eval;
    }

    private boolean isEnoughTime() {
        long now = System.currentTimeMillis();
        return (now - startTime < timeToMove);
    }

    /**
     * Returns the best solution for current player among the legal moves
     * @param state state for which need to choose the best move
     * @param depth depth of recursion
     * @return      evaluation of best move for current player among the legal moves
     */
    private Move rootMinimax(State state, int depth) {
        ArrayList<Move> legalMoves = new ArrayList<>(state.getLegalMoves());
        int curPlayer = state.getPlayerToMove();
        int bestScore = curPlayer == 0 ? MIN_SCORE : MAX_SCORE;
        int bestMoveIndex = 0;

        for (int i = 0; i < legalMoves.size(); i++) {
            Move curMove = legalMoves.get(i);
            state.makeMove(curMove);
            int curMoveScore = minimax(state, depth, 0, MIN_SCORE, MAX_SCORE);
            state.takeMove(curMove);
            if (!isEnoughTime()) {
                return null;
            }
            if (curPlayer == 0) {
                if (curMoveScore > bestScore) {
                    bestScore = curMoveScore;
                    bestMoveIndex = i;
                }
            } else {
                if (curMoveScore < bestScore) {
                    bestScore = curMoveScore;
                    bestMoveIndex = i;
                }
            }
        }
        return legalMoves.get(bestMoveIndex);
    }

    /**
     * Returns the best solution for current player among the legal moves
     * @param state    state for which need to choose the best move
     * @param maxDepth max depth of recursion
     * @param curDepth current depth of recursion
     * @param alpha    alpha score
     * @param beta     beta score
     * @return         evaluation of best move for current player among the legal moves
     */
    private int minimax(State state, int maxDepth, int curDepth, int alpha, int beta) {
        if (curDepth >= maxDepth || state.isTerminal() != 0) {
            return eval.evalState(state, curDepth);
        }

        if (!isEnoughTime()) {
            return 0;
        }

        ArrayList<Move> legalMoves = new ArrayList<>(state.getLegalMoves());
        int curPlayer = state.getPlayerToMove();

        for (int i = 0; i < legalMoves.size(); i++) {
            Move curMove = legalMoves.get(i);
            state.makeMove(curMove);
            int curMoveScore = minimax(state, maxDepth, curDepth + 1, alpha, beta);
            state.takeMove(curMove);
            if (curPlayer == 0) {
                alpha = Math.max(alpha, curMoveScore);
            } else {
                beta = Math.min(beta, curMoveScore);
            }
            if (beta < alpha) {
                break;
            }
        }
        return curPlayer == 0 ? alpha : beta;
    }

    /**
     * Controls the duration of evaluation
     * @param state            state for which need to choose the best move
     * @param timeMilliseconds time for move
     * @return                 the best move for current player among the legal moves in due time
     */
    public Move iterativeDeepening(State state, long timeMilliseconds) {
        startTime = System.currentTimeMillis();
        timeToMove = timeMilliseconds;
        Move bestMove = null;
        for (int depth = 1; depth < MAX_DEPTH; depth++) {
            Move currentMove = rootMinimax(state, depth);
            System.out.println("depth: " + depth);
            if (!isEnoughTime()) {
                return bestMove;
            }

            bestMove = currentMove;
        }

        return bestMove;
    }
}