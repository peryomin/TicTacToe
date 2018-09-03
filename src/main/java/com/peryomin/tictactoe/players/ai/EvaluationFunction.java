package main.java.com.peryomin.tictactoe.players.ai;

import main.java.com.peryomin.tictactoe.State;

public interface EvaluationFunction {
    public int evalState(State state);
}
