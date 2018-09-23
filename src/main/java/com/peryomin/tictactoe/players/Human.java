package main.java.com.peryomin.tictactoe.players;

import main.java.com.peryomin.tictactoe.*;
import java.util.Scanner;

public class Human implements Player {
    @Override
    public Move getMove(State state, long timeMilliseconds) {
        Scanner in = new Scanner(System.in);
        System.out.println("Human move: ");
        int x = in.nextInt();
        int y = in.nextInt();

        return new Move(x, y);
    }
}