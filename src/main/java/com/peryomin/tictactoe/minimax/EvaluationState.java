package main.java.com.peryomin.tictactoe.minimax;

import main.java.com.peryomin.tictactoe.State;

import static main.java.com.peryomin.tictactoe.State.*;

public class EvaluationState implements EvaluationFunction {
    int[] coeffs;

    public void setCoeffs(int[] coeffs) {
        this.coeffs = coeffs;
    }

    @Override
    public int evalState(State state, int depth) {
        int stateCode = state.isTerminal();
        if (stateCode == 3) {
            return 0;
        } else if (stateCode == 1) {
            return Minimax.MAX_SCORE - state.getPly();
        } else if (stateCode == 2) {
            return Minimax.MIN_SCORE + state.getPly();
        }
        int[][] field = state.getField();
        return evalFieldForPlayer(field, CROSS) - evalFieldForPlayer(field, ZERO);
    }

    /**
     * Returns evaluation of field
     * @param field  field which should be evaluated
     * @param player for which need to evaluate field
     * @return       evaluated score
     */
    private int evalFieldForPlayer(int[][] field, int player) {
        int evalScore = 0;
        int cellsInCenter;
        int almostThreeInARow;
        int almostFourInARow;
        int twoInARow;
        int threeInARow;
        int emptyCellsNearPlayerCells;
        for (int i = 0; i < N; i++) {
            cellsInCenter = 0;
            almostThreeInARow = 0;
            almostFourInARow = 0;
            twoInARow = 0;
            threeInARow = 0;
            emptyCellsNearPlayerCells = 0;
            for (int j = 0; j < N; j++) {
                if (i > 3 && i < N - 4 && j > 3 && j < N - 4 && field[i][j] == player) {
                    cellsInCenter++;
                }
                // Evaluate horizontal lines
                if (j <= N - 4 && (field[i][j] == EMPTY_CELL && field[i][j + 1] == player && field[i][j + 2] == player && field[i][j + 3] == player ||
                        field[i][j] == player && field[i][j + 1] == EMPTY_CELL && field[i][j + 2] == player && field[i][j + 3] == player ||
                        field[i][j] == player && field[i][j + 1] == player && field[i][j + 2] == EMPTY_CELL && field[i][j + 3] == player ||
                        field[i][j] == player && field[i][j + 1] == player && field[i][j + 2] == player && field[i][j + 3] == EMPTY_CELL)){
                    almostFourInARow++;
                } else if (j <= N - 3 && (field[i][j] == EMPTY_CELL && field[i][j + 1] == player && field[i][j + 2] == player ||
                        field[i][j] == player && field[i][j + 1] == EMPTY_CELL && field[i][j + 2] == player ||
                        field[i][j] == player && field[i][j + 1] == player && field[i][j + 2] == EMPTY_CELL)){
                    almostThreeInARow++;
                }
                if (j < N - 2 && field[i][j] == player && field[i][j + 1] == player) {
                    twoInARow++;
                }
                if (j < N - 3 && field[i][j] == player && field[i][j + 1] == player &&
                        field[i][j + 2] == player) {
                    threeInARow++;
                }
                if (j > 0 && field[i][j] == player && field[i][j - 1] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                if (j < N - 1 && field[i][j] == player && field[i][j + 1] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                // Evaluate vertical lines
                if (j <= N - 4 && (field[j][i] == EMPTY_CELL && field[j + 1][i] == player && field[j + 2][i] == player && field[j + 3][i] == player ||
                        field[j][i] == player && field[j + 1][i] == EMPTY_CELL && field[j + 2][i] == player && field[j + 3][i]  == player ||
                        field[j][i] == player && field[j + 1][i] == player && field[j + 2][i] == EMPTY_CELL && field[j + 3][i]  == player ||
                        field[j][i] == player && field[j + 1][i] == player && field[j + 2][i] == player && field[j + 3][i]  == EMPTY_CELL)){
                    almostFourInARow++;
                } else if (j <= N - 3 && (field[j][i] == EMPTY_CELL && field[j + 1][i] == player && field[j + 2][i] == player ||
                        field[j][i] == player && field[j + 1][i] == EMPTY_CELL && field[j + 2][i] == player ||
                        field[j][i] == player && field[j + 1][i] == player && field[j + 2][i] == EMPTY_CELL)){
                    almostThreeInARow++;
                }
                if (j < N - 2 && field[j][i] == player && field[j + 1][i] == player) {
                    twoInARow++;
                }
                if (j < N - 3 && field[j][i] == player && field[j + 1][i] == player &&
                        field[j + 2][i] == player) {
                    threeInARow++;
                }
                if (j > 0 && field[j][i] == player && field[j - 1][i] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                if (j < N - 1 && field[j][i] == player && field[j + 1][i] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                // Evaluate left-right diagonal
                if (i < N - 4 && j < N - 4 && (field[i][j] == EMPTY_CELL && field[i + 1][j + 1] == player && field[i + 2][j + 2] == player && field[i + 3][j + 3] == player ||
                        field[i][j] == player && field[i + 1][j + 1] == EMPTY_CELL && field[i + 2][j + 2] == player && field[i + 3][j + 3]  == player ||
                        field[i][j] == player && field[i + 1][j + 1] == player && field[i + 2][j + 2] == EMPTY_CELL && field[i + 3][j + 3]  == player ||
                        field[i][j] == player && field[i + 1][j + 1] == player && field[i + 2][j + 2] == player && field[i + 3][j + 3]  == EMPTY_CELL)){
                    almostFourInARow++;
                } else if (i < N - 4 && j < N - 4 && (field[i][j]  == EMPTY_CELL && field[i + 1][j + 1] == player && field[i + 2][j + 2] == player ||
                        field[i][j] == player && field[i + 1][j + 1] == EMPTY_CELL && field[i + 2][j + 2] == player ||
                        field[i][j] == player && field[i + 1][j + 1] == player && field[i + 2][j + 2] == EMPTY_CELL)) {
                    almostThreeInARow++;
                }
                if (j < N - 2 && i < N - 2 && field[i][j] == player && field[i + 1][j + 1] == player) {
                    twoInARow++;
                }
                if (j < N - 3 && i < N - 3 && field[i][j] == player && field[i + 1][j + 1] == player &&
                        field[i + 2][j + 2] == player) {
                    threeInARow++;
                }
                if (i > 0 && j > 0 && i < N - 1 && j < N - 1 && field[i][j] == player && field[i - 1][j - 1] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                if (i > 0 && j > 0 && i < N - 1 && j < N - 1 && field[i][j] == player && field[i + 1][j + 1] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                // Evaluate right-left diagonal
                if (i + 4 < N && j - 4 >= 0 && (field[i][j] == EMPTY_CELL && field[i + 1][j - 1] == player && field[i + 2][j - 2] == player && field[i + 3][j - 3] == player ||
                        field[i][j] == player && field[i + 1][j - 1] == EMPTY_CELL && field[i + 2][j - 2] == player && field[i + 3][j - 3]  == player ||
                        field[i][j] == player && field[i + 1][j - 1] == player && field[i + 2][j - 2] == EMPTY_CELL && field[i + 3][j - 3]  == player ||
                        field[i][j] == player && field[i + 1][j - 1] == player && field[i + 2][j - 2] == player && field[i + 3][j - 3]  == EMPTY_CELL)){
                    almostFourInARow++;
                } else if (i + 4 < N && j - 4 >= 0 && (field[i][j]  == EMPTY_CELL && field[i + 1][j - 1] == player && field[i + 2][j - 2] == player ||
                        field[i][j] == player && field[i + 1][j - 1] == EMPTY_CELL && field[i + 2][j - 2] == player ||
                        field[i][j] == player && field[i + 1][j - 1] == player && field[i + 2][j - 2] == EMPTY_CELL)) {
                    almostThreeInARow++;
                }
                if (j >= 1 && i < N - 2 && field[i][j] == player && field[i + 1][j - 1] == player) {
                    twoInARow++;
                }
                if (j >= 2 && i < N - 3 && field[i][j] == player && field[i + 1][j - 1] == player &&
                        field[i + 2][j - 2] == player) {
                    threeInARow++;
                }
                if (i > 0 && j > 0 && i < N - 1 && j < N - 1 && field[i][j] == player && field[i - 1][j + 1] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
                if (i > 0 && j > 0 && i < N - 1 && j < N - 1 && field[i][j] == player && field[i + 1][j - 1] == EMPTY_CELL) {
                    emptyCellsNearPlayerCells++;
                }
            }
            evalScore += coeffs[0] * cellsInCenter + coeffs[1] * almostFourInARow + coeffs[2] * almostThreeInARow +
                    coeffs[3] * twoInARow + coeffs[4] * threeInARow + coeffs[5] * emptyCellsNearPlayerCells;
        }
        return evalScore;
    }
}