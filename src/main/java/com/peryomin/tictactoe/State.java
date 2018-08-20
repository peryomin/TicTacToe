package main.java.com.peryomin.tictactoe;

import java.util.ArrayList;
import java.util.List;

public final class State {
    public static final int EMPTY_CELL = 0;
    public static final int CROSS = 1;
    public static final int ZERO = 2;
    public static final int N = 10;
    private final int[][] field;
    private static final int N_IN_A_ROW = 5;

    private int playerToMove = (int) Math.round(Math.random());

    public State() {
        field = new int[N][N];
    }

    private State(int[][] field, int playerToMove) {
        this.field = field;
        this.playerToMove = playerToMove;
    }

    /**
     * Makes a move and returns new state after that move
     */
    public State applyMove(Move move) {
        int[][] resultField = getField();
        resultField[move.y][move.x] = playerToMove == 0 ? CROSS : ZERO;
        int player = playerToMove == 0 ? 1 : 0;

        return new State(resultField, player);
    }

    /**
     * Returns all the legal moves in this state
     */
    public List<Move> getLegalMoves() {
        List<Move> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (field[i][j] == EMPTY_CELL) {
                    result.add(new Move(i, j));
                }
            }
        }
        return result;
    }

    /**
     * Returns the copy of a game field
     */
    public int[][] getField() {
        int[][] copy = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(this.field[i], 0, copy[i], 0, this.field[i].length);
        }
        return copy;
    }

    /**
     * Returns 0 - game is on, 1 - cross won, 2 - zero won, 3 - draw
     */
    public int isTerminal() {
        if (getLegalMoves().isEmpty()) {
            return 3;
        }
        int counter = 1;
        // Checking horizontal lines
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (field[i][j] == field[i][j - 1] && counter < N_IN_A_ROW
                        && field[i][j] != EMPTY_CELL) {
                    counter++;
                } else {
                    counter = 1;
                }
                if (counter == N_IN_A_ROW) {
                    return field[i][j];
                }
            }
            counter = 1;
        }
        // Checking vertical lines
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (field[j][i] == field[j - 1][i] && counter < N_IN_A_ROW
                        && field[j][i] != EMPTY_CELL) {
                    counter++;
                } else {
                    counter = 1;
                }
                if (counter == N_IN_A_ROW) {
                    return field[j][i];
                }
            }
            counter = 1;
        }
        // Checking diagonal lines
        for (int i = 1; i < N; i++) {
            if (field[i][i] == field[i - 1][i - 1] && counter < N_IN_A_ROW
                    && field[i][i] != EMPTY_CELL) {
                counter++;
            } else {
                counter = 1;
            }
            if (counter == N_IN_A_ROW) {
                return field[i][i];
            }
        }
        for (int i = 1; i < N; i++) {
            if (field[N - 1 - i][i] == field[N - i][i - 1] && counter < N_IN_A_ROW
                    && field[N - 1 - i][i] != EMPTY_CELL) {
                counter++;
            } else {
                counter = 1;
            }
            if (counter == N_IN_A_ROW) {
                return field[N - 1 - i][i];
            }
        }
        return 0;
    }


    /**
     * Returns either 0 or 1, 0 - cross to move, 1 - zero to move
     */
    public int getPlayerToMove() {
        return playerToMove;
    }

    /**
     * Prints current state to console
     */
    public void printState() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (field[i][j] == EMPTY_CELL) {
                    System.out.print("[ ]");
                } else if (field[i][j] == CROSS) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[O]");
                }
            }
            System.out.print("\n");
        }
    }

    public void printWinnerCombo(int winner) {
        int[][] curField = getField();
        int upperSymbol = 3;
        int counter = 1;
        // Checking horizontal lines
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (curField[i][j] == curField[i][j - 1] && counter < N_IN_A_ROW
                        && curField[i][j] != EMPTY_CELL) {
                    counter++;
                } else {
                    counter = 1;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[i][k] = upperSymbol;
                    }
                }
            }
            counter = 1;
        }
        // Checking vertical lines
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (curField[j][i] == curField[j - 1][i] && counter < N_IN_A_ROW
                        && curField[j][i] != EMPTY_CELL) {
                    counter++;
                } else {
                    counter = 1;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[k][i] = upperSymbol;
                    }
                }
            }
            counter = 1;
        }
        // Checking diagonal lines
        for (int i = 1; i < N; i++) {
            if (curField[i][i] == curField[i - 1][i - 1] && counter < N_IN_A_ROW
                    && curField[i][i] != EMPTY_CELL) {
                counter++;
            } else {
                counter = 1;
            }
            if (counter == N_IN_A_ROW) {
                for (int k = i; k > i - N_IN_A_ROW; k--) {
                    curField[k][k] = upperSymbol;
                }
            }
        }
        for (int i = 1; i < N; i++) {
            if (curField[N - 1 - i][i] == curField[N - i][i - 1] && counter < N_IN_A_ROW
                    && curField[N - 1 - i][i] != EMPTY_CELL) {
                counter++;
            } else {
                counter = 1;
            }
            if (counter == N_IN_A_ROW) {
                for (int k = i; k > i - N_IN_A_ROW; k--) {
                    curField[N - 1 - k][i] = upperSymbol;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (curField[i][j] == EMPTY_CELL) {
                    System.out.print("[ ]");
                } else if (curField[i][j] == upperSymbol) {
                    System.out.print(winner == 1 ? " X " : " O ");
                } else if (curField[i][j] == CROSS) {
                    System.out.print("[x]");
                } else {
                    System.out.print("[o]");
                }
            }
            System.out.print("\n");
        }
    }

}