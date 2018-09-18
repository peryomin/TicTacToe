package main.java.com.peryomin.tictactoe.minimax;

import main.java.com.peryomin.tictactoe.State;

public interface EvaluationFunction {
    /**
     * Return evaluation if state
     * @param state state that need to evaluate
     * @param depth need to improve evaluation (winning combinations have greater priority)
     * @return      evaluation
     */
    int evalState(State state, int depth);
}
