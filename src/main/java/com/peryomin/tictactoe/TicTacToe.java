package main.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.players.*;

public class TicTacToe {
    public static void main(String[] args) {
        Player human = new Human();
        Player ai = new AI();

        Game game = new Game(ai, human);
        game.playGame();
    }
}