package main.java.com.peryomin.tictactoe.players;

import main.java.com.peryomin.tictactoe.Move;
import main.java.com.peryomin.tictactoe.State;

public interface Player {
    /**
     * Returns the current player's move
     * @param currentState     current state of the game
     * @param timeMilliseconds time for move
     * @return                 current player's move
     */
    Move getMove(State currentState, long timeMilliseconds);
}