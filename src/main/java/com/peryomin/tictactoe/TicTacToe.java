package main.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.minimax.EvaluationState;
import main.java.com.peryomin.tictactoe.minimax.VladsEval;
import main.java.com.peryomin.tictactoe.players.*;
import main.java.com.peryomin.tictactoe.players.AI;

public class TicTacToe {
    public static void main(String[] args) {
        Player human = new Human();
        Player ai = new AI(new EvaluationState());

        Game game = new Game(ai, human);
        game.playGame(5000);
    }
}