package main.java.com.peryomin.tictactoe.minimax;

import main.java.com.peryomin.tictactoe.State;

public class VladsEval implements EvaluationFunction {
    @Override
    public int evalState(State state, int depth) {
        int terminal = state.isTerminal();
        if (terminal == 3) {
            return 0;
        }

        if (terminal == State.CROSS) {
            return Integer.MAX_VALUE / 3 - state.getPly();
        }

        if (terminal == State.ZERO) {
            return Integer.MIN_VALUE / 3 + state.getPly();
        }

        return evalPlayer(state, State.CROSS) - evalPlayer(state, State.ZERO);
    }

    public int evalPlayer(State state, int player) {
        int twoInARow = 0;
        int threeInARow = 0;
        int center = 0;
        int fourInARow1Opened = 0;
        int fourInARow2Opened = 0;
        int canWin = 0;
        int[][] map = state.getField();
        int n = map.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == player && i > n / 3 && i < 2 * n / 3 && j > n / 3 && j < 2 * n / 3) {
                    center++;
                }

                //two in a row
                if (j+1 < n) {
                    if (map[i][j] == player && map[i][j + 1] == player)
                        twoInARow++;
                }
                if(i+1 < n){
                    if (map[i][j] == player && map[i+1][j] == player)
                        twoInARow++;
                }
                if(j-1 >= 0 && i+1 < n){
                    if (map[i][j] == player && map[i+1][j-1] == player)
                        twoInARow++;
                }
                if(j+1 < 0 && i+1 < n){
                    if (map[i][j] == player && map[i+1][j+1] == player)
                        twoInARow++;
                }

                //three in a row
                if (j+2 < n) {
                    if (map[i][j] == player && map[i][j + 1] == player &&  map[i][j + 2] == player)
                        threeInARow++;
                }
                if(i+2 < n){
                    if (map[i][j] == player && map[i+1][j] == player && map[i+2][j] == player)
                        threeInARow++;
                }
                if(j-2 >= 0 && i+2 < n){
                    if (map[i][j] == player && map[i+1][j-1] == player && map[i+2][j-2] == player)
                        threeInARow++;
                }
                if(j+2 < n && i+2 < n){
                    if (map[i][j] == player && map[i+1][j+1] == player && map[i+2][j+2] == player)
                        threeInARow++;
                }

                //four in a row with 1 or 2 opened sides
                if (i + 3 < n && j + 3 < n && j-1 >= 0 && i-1 >= 0){
                    if (map[i][j] == player && map[i + 1][j + 1] == player && map[i + 2][j + 2] == player && map[i + 3][j + 3] == player && map[i -1 ][j -1] == 0)
                        fourInARow1Opened++;
                }
                if (j + 3 < n && j - 1 >= 0){
                    if (map[i][j] == player && map[i][j + 1] == player && map[i][j + 2] == player && map[i][j + 3] == player &&  map[i][j -1] == 0)
                        fourInARow1Opened++;
                }
                if (i + 3 < n && i - 1 >= 0){
                    if (map[i][j] == player && map[i + 1][j] == player && map[i + 2][j] == player && map[i + 3][j] == player && map[i - 1][j] == 0)
                        fourInARow1Opened++;
                }
                if (j - 3 >= 0 && i + 3 < 10 && j + 1 < n && i - 1 >=0){
                    if (map[i][j] == player && map[i + 1][j - 1] == player && map[i + 2][j - 2] == player && map[i + 3][j - 3] == player &&  map[i - 1 ][j + 1] == 0)
                        fourInARow1Opened++;
                }
                //******************************************
                if (i + 4 < n && j + 4 < n){
                    if (map[i][j] == player && map[i + 1][j + 1] == player && map[i + 2][j + 2] == player && map[i + 3][j + 3] == player && map[i + 4][j + 4] == 0)
                        fourInARow1Opened++;
                }
                if (j + 4 < n){
                    if (map[i][j] == player && map[i][j + 1] == player && map[i][j + 2] == player && map[i][j + 3] == player && map[i][j + 4] == 0)
                        fourInARow1Opened++;
                }
                if (i + 4 < n){
                    if (map[i][j] == player && map[i + 1][j] == player && map[i + 2][j] == player && map[i + 3][j] == player && map[i + 4][j] == 0)
                        fourInARow1Opened++;
                }
                if (j - 4 >= 0 && i + 4 < n){
                    if (map[i][j] == player && map[i + 1][j - 1] == player && map[i + 2][j - 2] == player && map[i + 3][j - 3] == player && map[i + 4][j - 4] == 0)
                        fourInARow1Opened++;
                }

                //four in a row with 2 opened sides
                if (i + 4 < n && j + 4 < n && j-1 >= 0 && i-1 >= 0){
                    if (map[i][j] == player && map[i + 1][j + 1] == player && map[i + 2][j + 2] == player && map[i + 3][j + 3] == player && map[i + 4][j + 4] == 0 &&  map[i -1 ][j -1] == 0)
                        fourInARow2Opened++;
                }
                if (j + 4 < 10 && j - 1 >= 0){
                    if (map[i][j] == player && map[i][j + 1] == player && map[i][j + 2] == player && map[i][j + 3] == player && map[i][j + 4] == 0 &&  map[i][j -1] == 0)
                        fourInARow2Opened++;
                }
                if (i + 4 < 10 && i - 1 >= 0){
                    if (map[i][j] == player && map[i + 1][j] == player && map[i + 2][j] == player && map[i + 3][j] == player && map[i + 4][j] == 0 &&  map[i - 1][j] == 0)
                        fourInARow2Opened++;
                }
                if (j - 4 >= 0 && i + 4 < 10 && j + 1 < n && i - 1 >=0){
                    if (map[i][j] == player && map[i + 1][j - 1] == player && map[i + 2][j - 2] == player && map[i + 3][j - 3] == player && map[i + 4][j - 4] == 0 &&  map[i - 1 ][j + 1] == 0)
                        fourInARow2Opened++;
                }


                // Check if we can win right away.
                if (i + 4 < 10 && j + 4 < 10){
                    if (map[i][j] == player && map[i + 1][j + 1] == player && map[i + 2][j + 2] == player && map[i + 3][j + 3] == player && map[i + 4][j + 4] == player) {
                        canWin = 1;
                    }
                }

                if (j + 4 < 10){
                    if (map[i][j] == player && map[i][j + 1] == player && map[i][j + 2] == player && map[i][j + 3] == player && map[i][j + 4] == player) {
                        canWin = 1;
                    }
                }

                if (i + 4 < 10){
                    if (map[i][j] == player && map[i + 1][j] == player && map[i + 2][j] == player && map[i + 3][j] == player && map[i + 4][j] == player) {
                        canWin = 1;
                    }
                }

                if (j - 4 >= 0 && i + 4 < 10){
                    if (map[i][j] == player && map[i + 1][j - 1] == player && map[i + 2][j - 2] == player && map[i + 3][j - 3] == player && map[i + 4][j - 4] == player) {
                        canWin = 1;
                    }
                }

            }
        }

        return 1 * twoInARow + 5 * threeInARow + 30 * fourInARow1Opened + 100 * fourInARow2Opened + center + canWin * 1000;
    }
}