package main.java.com.peryomin.tictactoe;

import main.java.com.peryomin.tictactoe.minimax.EvaluationState;
import main.java.com.peryomin.tictactoe.minimax.Evolution;
import main.java.com.peryomin.tictactoe.minimax.VladsEval;
import main.java.com.peryomin.tictactoe.players.*;
import main.java.com.peryomin.tictactoe.players.AI;

import java.util.Random;

public class TicTacToe {
    public static void main(String[] args) {
        Player human = new Human();
        //Player ai = new AI(new EvaluationState());
        Player ai = new AI(new VladsEval());

        Game game = new Game(ai, human);
        game.playGame(15000, true);
        /*Evolution evolution = new Evolution(6, 10, 1);
        evolution.start();*/
    }
}