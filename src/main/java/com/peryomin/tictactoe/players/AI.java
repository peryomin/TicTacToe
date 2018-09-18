package main.java.com.peryomin.tictactoe.players;

import main.java.com.peryomin.tictactoe.*;
import main.java.com.peryomin.tictactoe.minimax.*;

public class AI implements Player {
    private Minimax minimax;

    public AI(EvaluationFunction eval) {
        minimax = new Minimax(eval);
    }

    @Override
    public Move getMove(State state, long timeMilliseconds) {
        return minimax.iterativeDeeping(state, timeMilliseconds);
    }
}