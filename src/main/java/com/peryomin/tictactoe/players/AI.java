package main.java.com.peryomin.tictactoe.players;

import main.java.com.peryomin.tictactoe.*;
import java.util.List;
import java.util.Random;

public class AI implements Player {
    @Override
    public Move getMove(State state) {
        List<Move> moves = state.getLegalMoves();
        Random r = new Random(42);
        return moves.get(r.nextInt(moves.size()));
    }
}