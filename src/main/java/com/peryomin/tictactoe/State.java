package main.java.com.peryomin.tictactoe;

import java.util.ArrayList;
import java.util.List;

public final class State {
    public static final int EMPTY_CELL = 0;
    public static final int CROSS = 1;
    public static final int ZERO = 2;
    public static final int N = 10;
    private int[][] field;
    private int playerToMove = (int) Math.round(Math.random());

    public State() {
        field = new int[N][N];
    }

    public State(State state) {
        this.field = state.field;
        this.playerToMove = state.playerToMove;
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

    public void makeMove(Move move) {
        field[move.y][move.x] = playerToMove == 0 ? CROSS : ZERO;
    }

    /**
     * Cancel move on the game field
     * @param move that need to be cancelled
     */
    public void takeMove(Move move) {
        field[move.y][move.x] = EMPTY_CELL;
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

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int player = CROSS; player <= ZERO; player++) {
                    if (j + 4 < N && field[i][j] == player && field[i][j + 1] == player && field[i][j + 2] == player
                            && field[i][j + 3] == player && field[i][j + 4] == player) {
                        return player;
                    }
                    if (j + 4 < N && field[j][i] == player && field[j + 1][i] == player && field[j + 2][i] == player
                            && field[j + 3][i] == player && field[j + 4][i] == player) {
                        return player;
                    }
                    if (i + 4 < N && j + 4 < N && field[i][j] == player && field[i + 1][j + 1] == player
                            && field[i + 2][j + 2] == player && field[i + 3][j + 3] == player && field[i + 4][j + 4] == player) {
                        return player;
                    }
                    if (j - 4 >= 0 && i + 4 < N && field[i][j] == player  && field[i + 1][j - 1] == player &&
                             field[i + 2][j - 2] == player && field[i + 3][j - 3] == player && field[i + 4][j - 4] == player) {
                        return player;
                    }
                }
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

    /**
     * Prints current state with the allotted winner to console
     */
    public void printWinnerCombo() {
        int[][] curField = getField();
        int upperSymbol = 2;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int player = CROSS; player <= ZERO; player++) {
                    if (j + 4 < N && curField[i][j] == player && curField[i][j + 1] == player && curField[i][j + 2] == player
                            && curField[i][j + 3] == player && curField[i][j + 4] == player) {
                        curField[i][j]     = upperSymbol + player;
                        curField[i][j + 1] = upperSymbol + player;
                        curField[i][j + 2] = upperSymbol + player;
                        curField[i][j + 3] = upperSymbol + player;
                        curField[i][j + 4] = upperSymbol + player;
                    }
                    if (j + 4 < N && curField[j][i] == player && curField[j + 1][i] == player && curField[j + 2][i] == player
                            && curField[j + 3][i] == player && curField[j + 4][i] == player) {
                        curField[j][i]     = upperSymbol + player;
                        curField[j + 1][i] = upperSymbol + player;
                        curField[j + 2][i] = upperSymbol + player;
                        curField[j + 3][i] = upperSymbol + player;
                        curField[j + 4][i] = upperSymbol + player;
                    }
                    if (i + 4 < N && j + 4 < N && field[i][j] == player && curField[i + 1][j + 1] == player
                            && curField[i + 2][j + 2] == player && curField[i + 3][j + 3] == player && curField[i + 4][j + 4] == player) {
                        curField[i][j]         = upperSymbol + player;
                        curField[i + 1][j + 1] = upperSymbol + player;
                        curField[i + 2][j + 2] = upperSymbol + player;
                        curField[i + 3][j + 3] = upperSymbol + player;
                        curField[i + 4][j + 4] = upperSymbol + player;
                    }
                    if (j - 4 >= 0 && i + 4 < N && curField[i][j] == player  && curField[i + 1][j - 1] == player &&
                            curField[i + 2][j - 2] == player && curField[i + 3][j - 3] == player && curField[i + 4][j - 4] == player) {
                        curField[i][j]         = upperSymbol + player;
                        curField[i + 1][j - 1] = upperSymbol + player;
                        curField[i + 2][j - 2] = upperSymbol + player;
                        curField[i + 3][j - 3] = upperSymbol + player;
                        curField[i + 4][j - 4] = upperSymbol + player;
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (curField[i][j] == EMPTY_CELL) {
                    System.out.print("[ ]");
                } else if (curField[i][j] == CROSS) {
                    System.out.print("[x]");
                } else if (curField[i][j] == ZERO) {
                    System.out.print("[o]");
                } else {
                    System.out.print(curField[i][j] == upperSymbol + CROSS ? " X " : " O ");
                }
            }
            System.out.print("\n");
        }
    }
}