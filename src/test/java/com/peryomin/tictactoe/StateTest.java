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
        0 [x][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        1 [ ][x][ ][ ][ ][ ][o][ ][ ][ ]
        2 [ ][ ][x][ ][ ][ ][ ][o][ ][ ]
        3 [ ][ ][ ][x][ ][x][x][x][o][ ]
        4 [ ][o][ ][ ][ ][ ][ ][ ][ ][o]
        5 [ ][o][ ][ ][o][ ][ ][ ][ ][x]
        6 [ ][o][o][o][x][ ][ ][ ][ ][x]
        7 [ ][ ][o][x][ ][ ][ ][ ][ ][x]
        8 [ ][o][x][ ][ ][ ][ ][ ][ ][x]
        9 [ ][x][ ][ ][ ][o][o][o][o][ ]
        */
        State state = new State();
        int player = state.getPlayerToMove();
        state = state.applyMove(new Move(8, 9)).applyMove(new Move(9, 8))
                .applyMove(new Move(7, 9)).applyMove(new Move(9, 7))
                .applyMove(new Move(6, 9)).applyMove(new Move(9, 6))
                .applyMove(new Move(5, 9)).applyMove(new Move(9, 5))
                .applyMove(new Move(1, 8)).applyMove(new Move(0, 0))
                .applyMove(new Move(2, 7)).applyMove(new Move(1, 1))
                .applyMove(new Move(3, 6)).applyMove(new Move(2, 2))
                .applyMove(new Move(4, 5)).applyMove(new Move(3, 3))
                .applyMove(new Move(9, 4)).applyMove(new Move(1, 9))
                .applyMove(new Move(8, 3)).applyMove(new Move(2, 8))
                .applyMove(new Move(7, 2)).applyMove(new Move(3, 7))
                .applyMove(new Move(6, 1)).applyMove(new Move(4, 6))
                .applyMove(new Move(1, 6)).applyMove(new Move(5, 3))
                .applyMove(new Move(1, 5)).applyMove(new Move(6, 3))
                .applyMove(new Move(1, 4)).applyMove(new Move(8, 4))
                .applyMove(new Move(2, 6)).applyMove(new Move(7, 3));

        assertEquals(player + 1, state.applyMove(new Move(0, 9)).isTerminal());
        assertEquals(player + 1, state.applyMove(new Move(1, 7)).isTerminal());
        assertEquals(player + 1, state.applyMove(new Move(9, 9)).isTerminal());
        assertEquals(player + 1, state.applyMove(new Move(5, 0)).isTerminal());
        assertEquals(0, state.applyMove(new Move(0, 6)).isTerminal());

        state = state.applyMove(new Move(9, 0));
        assertEquals(0, state.applyMove(new Move(0, 6)).isTerminal());
        player ^= 1;
        assertEquals(player + 1, state.applyMove(new Move(5, 5)).isTerminal());
        assertEquals(player + 1, state.applyMove(new Move(4, 4)).isTerminal());
        assertEquals(player + 1, state.applyMove(new Move(9, 9)).isTerminal());
        assertEquals(player + 1, state.applyMove(new Move(4, 3)).isTerminal());
    }
}