package main.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.players.Player;

public class Game {
    private State currentState;
    private Player[] players;

    public Game(Player crossPlayer, Player zeroPlayer) {
        currentState = new State();
        players = new Player[]{crossPlayer, zeroPlayer};
    }

    /**
     * Game logic goes here. Ask players for their moves, check if game is over,
     * print intermediate states and so on.
     * @param timeForMove time for one move in milliseconds
     * @return            code of game state
     */
    public int playGame(long timeForMove) {
        // bla bla
        while (currentState.isTerminal() == 0) {
            // ask current player for a move, update current state
            int curPlayer = currentState.getPlayerToMove();
            System.out.printf("Turn: %s\n", curPlayer == 0 ? "cross" : "zero");
            Move move = players[curPlayer].getMove(currentState, timeForMove);
            currentState = currentState.applyMove(move);
            currentState.printState();
        }
        int result = currentState.isTerminal();
        if (result == 3) {
            System.out.println("Draw.");
        } else {
            System.out.printf("Winner: %s\n", result == 1 ? "cross" : "zero");
            currentState.printWinnerCombo(result);
        }
        return result;
    }

}