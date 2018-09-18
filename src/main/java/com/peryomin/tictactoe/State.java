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
     * @param move which need to apply
     * @return     new state with this move
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
                    if (isCloseEnough(i, j) || (i == N / 2 && j == N / 2)){
                        result.add(new Move(j, i));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Check if there are non-empty cells nearby
     * @param x coordinate of cell
     * @param y coordinate of cell
     * @return  true if there are non-empty cells nearby
     */
    private boolean isCloseEnough(int x, int y) {
        int margin = 1;
        for (int i = Math.max(0, x - margin); i < Math.min(N, y + margin); i++) {
            for (int j = Math.max(0, x - margin); j < Math.min(N, y + margin); j++) {
                if (field[i][j] != EMPTY_CELL) {
                    return true;
                }
            }
        }
        return false;
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
        // Checking horizontal lines
        int prevCell = EMPTY_CELL;
        int curCell = EMPTY_CELL;
        int counter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                curCell = field[i][j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    return field[i][j];
                }
                prevCell = curCell;
            }
            counter = 0;
        }
        // Checking vertical lines
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                curCell = field[j][i];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    return field[j][i];
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        // Checking diagonal lines
        /* 0  1  2  3  4  5  6  7  8  9
        0 [#][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        1 [#][#][ ][ ][ ][ ][ ][ ][ ][ ]
        2 [#][#][#][ ][ ][ ][ ][ ][ ][ ]
        3 [#][#][#][#][ ][ ][ ][ ][ ][ ]
        4 [#][#][#][#][#][ ][ ][ ][ ][ ]
        5 [#][#][#][#][#][#][ ][ ][ ][ ]
        6 [ ][#][#][#][#][#][#][ ][ ][ ]
        7 [ ][ ][#][#][#][#][#][#][ ][ ]
        8 [ ][ ][ ][#][#][#][#][#][#][ ]
        9 [ ][ ][ ][ ][#][#][#][#][#][#]
        */
        for (int i = 0; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j++) {
                curCell = field[i + j][j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    return field[i + j][j];
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        /* 0  1  2  3  4  5  6  7  8  9
        0 [ ][#][#][#][#][#][ ][ ][ ][ ]
        1 [ ][ ][#][#][#][#][#][ ][ ][ ]
        2 [ ][ ][ ][#][#][#][#][#][ ][ ]
        3 [ ][ ][ ][ ][#][#][#][#][#][ ]
        4 [ ][ ][ ][ ][ ][#][#][#][#][#]
        5 [ ][ ][ ][ ][ ][ ][#][#][#][#]
        6 [ ][ ][ ][ ][ ][ ][ ][#][#][#]
        7 [ ][ ][ ][ ][ ][ ][ ][ ][#][#]
        8 [ ][ ][ ][ ][ ][ ][ ][ ][ ][#]
        9 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        */
        for (int i = 1; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j ++) {
                curCell = field[j][i + j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    return field[j][i + j];
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        /* 0  1  2  3  4  5  6  7  8  9
        0 [ ][ ][ ][ ][#][#][#][#][#][#]
        1 [ ][ ][ ][#][#][#][#][#][#][ ]
        2 [ ][ ][#][#][#][#][#][#][ ][ ]
        3 [ ][#][#][#][#][#][#][ ][ ][ ]
        4 [#][#][#][#][#][#][ ][ ][ ][ ]
        5 [#][#][#][#][#][ ][ ][ ][ ][ ]
        6 [#][#][#][#][ ][ ][ ][ ][ ][ ]
        7 [#][#][#][ ][ ][ ][ ][ ][ ][ ]
        8 [#][#][ ][ ][ ][ ][ ][ ][ ][ ]
        9 [#][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        */
        for (int i = 0; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j++) {
                curCell = field[N - 1 - j - i][j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    return field[N - 1 - j - i][j];
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        /* 0  1  2  3  4  5  6  7  8  9
        0 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        1 [ ][ ][ ][ ][ ][ ][ ][ ][ ][#]
        2 [ ][ ][ ][ ][ ][ ][ ][ ][#][#]
        3 [ ][ ][ ][ ][ ][ ][ ][#][#][#]
        4 [ ][ ][ ][ ][ ][ ][#][#][#][#]
        5 [ ][ ][ ][ ][ ][#][#][#][#][#]
        6 [ ][ ][ ][ ][#][#][#][#][#][ ]
        7 [ ][ ][ ][#][#][#][#][#][ ][ ]
        8 [ ][ ][#][#][#][#][#][ ][ ][ ]
        9 [ ][#][#][#][#][#][ ][ ][ ][ ]
        */
        for (int i = 1; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j ++) {
                curCell = field[N - 1 - j][j + i];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    return field[N - 1 - j][j + i];
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
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

    /**
     * Prints current state with the allotted winner to console
     * @param winner player
     */
    public void printWinnerCombo(int winner) {
        int[][] curField = getField();
        int upperSymbol = 3;
        // Checking horizontal lines
        int prevCell = EMPTY_CELL;
        int curCell = EMPTY_CELL;
        int counter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                curCell = field[i][j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[i][k] = upperSymbol;
                    }
                }
                prevCell = curCell;
            }
            counter = 0;
        }
        // Checking vertical lines
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                curCell = field[j][i];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[k][i] = upperSymbol;
                    }
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        // Checking diagonal lines
        /* 0  1  2  3  4  5  6  7  8  9
        0 [#][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        1 [#][#][ ][ ][ ][ ][ ][ ][ ][ ]
        2 [#][#][#][ ][ ][ ][ ][ ][ ][ ]
        3 [#][#][#][#][ ][ ][ ][ ][ ][ ]
        4 [#][#][#][#][#][ ][ ][ ][ ][ ]
        5 [#][#][#][#][#][#][ ][ ][ ][ ]
        6 [ ][#][#][#][#][#][#][ ][ ][ ]
        7 [ ][ ][#][#][#][#][#][#][ ][ ]
        8 [ ][ ][ ][#][#][#][#][#][#][ ]
        9 [ ][ ][ ][ ][#][#][#][#][#][#]
        */
        for (int i = 0; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j++) {
                curCell = field[i + j][j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[i + k][k] = upperSymbol;
                    }
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        /* 0  1  2  3  4  5  6  7  8  9
        0 [ ][#][#][#][#][#][ ][ ][ ][ ]
        1 [ ][ ][#][#][#][#][#][ ][ ][ ]
        2 [ ][ ][ ][#][#][#][#][#][ ][ ]
        3 [ ][ ][ ][ ][#][#][#][#][#][ ]
        4 [ ][ ][ ][ ][ ][#][#][#][#][#]
        5 [ ][ ][ ][ ][ ][ ][#][#][#][#]
        6 [ ][ ][ ][ ][ ][ ][ ][#][#][#]
        7 [ ][ ][ ][ ][ ][ ][ ][ ][#][#]
        8 [ ][ ][ ][ ][ ][ ][ ][ ][ ][#]
        9 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        */
        for (int i = 1; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j ++) {
                curCell = field[j][i + j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[k][i + k] = upperSymbol;
                    }
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        /* 0  1  2  3  4  5  6  7  8  9
        0 [ ][ ][ ][ ][#][#][#][#][#][#]
        1 [ ][ ][ ][#][#][#][#][#][#][ ]
        2 [ ][ ][#][#][#][#][#][#][ ][ ]
        3 [ ][#][#][#][#][#][#][ ][ ][ ]
        4 [#][#][#][#][#][#][ ][ ][ ][ ]
        5 [#][#][#][#][#][ ][ ][ ][ ][ ]
        6 [#][#][#][#][ ][ ][ ][ ][ ][ ]
        7 [#][#][#][ ][ ][ ][ ][ ][ ][ ]
        8 [#][#][ ][ ][ ][ ][ ][ ][ ][ ]
        9 [#][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        */
        for (int i = 0; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j++) {
                curCell = field[N - 1 - j - i][j];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[N - 1 - k - i][k] = upperSymbol;
                    }
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
        }
        /* 0  1  2  3  4  5  6  7  8  9
        0 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        1 [ ][ ][ ][ ][ ][ ][ ][ ][ ][#]
        2 [ ][ ][ ][ ][ ][ ][ ][ ][#][#]
        3 [ ][ ][ ][ ][ ][ ][ ][#][#][#]
        4 [ ][ ][ ][ ][ ][ ][#][#][#][#]
        5 [ ][ ][ ][ ][ ][#][#][#][#][#]
        6 [ ][ ][ ][ ][#][#][#][#][#][ ]
        7 [ ][ ][ ][#][#][#][#][#][ ][ ]
        8 [ ][ ][#][#][#][#][#][ ][ ][ ]
        9 [ ][#][#][#][#][#][ ][ ][ ][ ]
        */
        for (int i = 1; i <= N - N_IN_A_ROW; i++) {
            for (int j = 0; j < N - i; j ++) {
                curCell = field[N - 1 - j][j + i];
                if (j == 0) {
                    prevCell = curCell;
                }
                if (prevCell != curCell) {
                    counter = 0;
                }
                if (curCell != EMPTY_CELL) {
                    counter++;
                }
                if (counter == N_IN_A_ROW) {
                    for (int k = j; k > j - N_IN_A_ROW; k--) {
                        curField[N - 1 - k][k + i] = upperSymbol;
                    }
                }
                prevCell = curCell;
            }
            prevCell = EMPTY_CELL;
            counter = 0;
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