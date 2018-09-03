package main.java.com.peryomin.tictactoe.players.ai;

import main.java.com.peryomin.tictactoe.*;

import java.util.ArrayList;

public class Minimax {
    private static final int MIN_SCORE = -10;
    private static final int MAX_SCORE = 10;
    public static final int MAX_DEPTH = 3;

    private EvaluationFunction eval;

    public Minimax(EvaluationFunction eval) {
        this.eval = eval;
    }

    public Move rootMinimax(State state) {
        ArrayList<Move> legalMoves = new ArrayList<>(state.getLegalMoves());
        int curPlayer = state.getPlayerToMove();
        int bestScore = curPlayer == 0 ? MIN_SCORE : MAX_SCORE;
        int bestMoveIndex = 0;

        for (int i = 0; i < legalMoves.size(); i++) {
            int curMoveScore = minimax(state.applyMove(legalMoves.get(i)), 1);
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

    private int minimax(State state, int depth) {
        if (depth == MAX_DEPTH) {
            return eval.evalState(state);
        }

        ArrayList<Move> legalMoves = new ArrayList<>(state.getLegalMoves());
        int curPlayer = state.getPlayerToMove();
        int bestScore = curPlayer == 0 ? MIN_SCORE : MAX_SCORE;

        for (int i = 0; i < legalMoves.size(); i++) {
            int curMoveScore = minimax(state.applyMove(legalMoves.get(i)), depth + 1);
            if (curPlayer == 0) {
                if (curMoveScore > bestScore) {
                    bestScore = curMoveScore;
                }
            } else {
                if (curMoveScore < bestScore) {
                    bestScore = curMoveScore;
                }
            }
        }
        return bestScore;
    }
}