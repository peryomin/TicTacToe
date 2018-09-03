package main.java.com.peryomin.tictactoe.players.ai;

import main.java.com.peryomin.tictactoe.*;

public class AI implements Player {
    @Override
    public Move getMove(State state) {
        Minimax minimax = new Minimax(new EvaluationState());
        return minimax.rootMinimax(state);
    }
}