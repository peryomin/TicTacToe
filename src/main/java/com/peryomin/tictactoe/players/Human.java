package main.java.com.peryomin.tictactoe.players;

import main.java.com.peryomin.tictactoe.*;
import java.util.Scanner;

public class Human implements Player {
    @Override
    public Move getMove(State state) {
        Scanner in = new Scanner(System.in);
        System.out.println("Your move. X coord:");
        int x = in.nextInt();
        System.out.println("Y coord:");
        int y = in.nextInt();

        return new Move(--x, --y);
    }
}