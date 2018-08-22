package test.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.Move;
import main.java.com.peryomin.tictactoe.State;
import org.junit.Test;

import static org.junit.Assert.*;

public class StateTest {

    @Test
    public void isTerminal() {
        /*
           0  1  2  3  4  5  6  7  8  9
        0 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        1 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        2 [ ][o][ ][ ][ ][ ][ ][ ][ ][ ]
        3 [ ][o][ ][ ][ ][ ][ ][ ][ ][ ]
        4 [ ][o][ ][ ][ ][ ][ ][ ][x][ ]
        5 [ ][o][ ][ ][ ][ ][ ][x][ ][ ]
        6 [ ][ ][ ][ ][ ][ ][x][ ][ ][ ]
        7 [ ][ ][ ][ ][ ][x][ ][ ][ ][ ]
        8 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        9 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        */
        State state = new State();
        int player = state.getPlayerToMove();
        state = state.applyMove(new Move(2, 1))
                .applyMove(new Move(7, 5))
                .applyMove(new Move(3, 1))
                .applyMove(new Move(6, 6))
                .applyMove(new Move(4, 1))
                .applyMove(new Move(5, 7))
                .applyMove(new Move(5, 1))
                .applyMove(new Move(4, 8));

        assertEquals(state.applyMove(new Move(6, 1)).isTerminal(), player + 1);
        assertEquals(state.applyMove(new Move(1, 1)).isTerminal(), player + 1);

        state = state.applyMove(new Move(0, 0));
        assertEquals(state.applyMove(new Move(1, 1)).isTerminal(), 0);
        player ^= 1;

        assertEquals(state.applyMove(new Move(8, 4)).isTerminal(), player + 1);
        assertEquals(state.applyMove(new Move(3, 9)).isTerminal(), player + 1);
    }
}