package test.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.*;
import main.java.com.peryomin.tictactoe.players.ai.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinimaxTest {
    @Test
    public void test() {
        Player crossPlayer = new TestAI();
        Player zeroPlayer = new TestAI();
        Player[] players = new Player[]{crossPlayer, zeroPlayer};
        State currentState = new State();

        int expectedCallCounter;

        for (int i = 0; i < 100; i++) {
            expectedCallCounter = 1;

            for (int k = 0; k < Minimax.MAX_DEPTH; k++) {
                expectedCallCounter *= currentState.getLegalMoves().size() - k;
            }

            int curPlayer = currentState.getPlayerToMove();
            Move move = players[curPlayer].getMove(currentState);
            currentState = currentState.applyMove(move);

            currentState.printState();
            System.out.println("-----------------");

            assertEquals(curPlayer + 1, currentState.getField()[0][i % State.N]);

            System.out.println(expectedCallCounter);
            System.out.println(TestEvaluationState.callCounter);

            assertEquals(expectedCallCounter, TestEvaluationState.callCounter);
            TestEvaluationState.callCounter = 0;
        }
    }

    public class TestAI implements Player {
        @Override
        public Move getMove(State state) {
            Minimax minimax = new Minimax(new TestEvaluationState());
            return minimax.rootMinimax(state);
        }
    }

    public static class TestEvaluationState implements EvaluationFunction {
        static int callCounter = 0;

        @Override
        public int evalState(State state) {
            callCounter++;
            return 0;
        }
    }
}