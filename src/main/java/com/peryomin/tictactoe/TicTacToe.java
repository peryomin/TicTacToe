package main.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.minimax.EvaluationState;
import main.java.com.peryomin.tictactoe.minimax.VladsEval;
import main.java.com.peryomin.tictactoe.players.*;
import main.java.com.peryomin.tictactoe.players.AI;

import java.util.Random;

public class TicTacToe {
    public static void main(String[] args) {
        Player human = new Human();
        Player ai = new AI(new EvaluationState());
        Player ai1 = new AI(new VladsEval());

        Game game = new Game(ai1, human);
        game.playGame(15000);
    }
}