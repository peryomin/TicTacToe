package main.java.com.peryomin.tictactoe;

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
     */
    public int playGame() {
        // bla bla
        while (currentState.isTerminal() == 0) {
            // ask current player for a move, update current state
            int curPlayer = currentState.getPlayerToMove();
            System.out.printf("Turn: %s\n", curPlayer == 0 ? "cross" : "zero");
            Move move = players[curPlayer].getMove(currentState);
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