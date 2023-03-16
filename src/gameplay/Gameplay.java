package gameplay;

import board.Board;
import cities.City;
import players.Players;
import players.humanPlayer;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Gameplay {

    /*
    Spawn board
    Spawn card decks
    Keep count of: infection rate, epidemics (what else?)
    At the beginning cards are dealt to players - 3 cards per player (? check rules)
    Also cards from epidemic deck are drawn (2 at the beginning, check infection rate)
    Some cities are infected (infection cubes added)
    Spawn players in Atlanta
    Every player has 4 moves in 1 turn
    Player can move around, cure diseases, use special actions (some actions cost cards)

    I need something that would always check state of the game and feed it into agent, so agent could
    do good decisions for their next play

    Chatbot - how to do this I have no idea

    If epidemic card - check rules
    */

    public Gameplay() throws FileNotFoundException {
        Board playground = new Board();
        for (Players player : playground.getPLAYERS()) {
            turn(player);
        }
    }

    // Maybe add all players to an array and perform turn for each element ?
    private void turn(Players player) {
        System.out.println("Player color of " + player.getPLAYER_ID() + " is playing.");
        int actionCounter = 0;
        Scanner scanner = new Scanner(System.in);
        while (actionCounter < 4) {
            System.out.println("What do you want to do next?");
            System.out.println("1. move to the adjacent city\n");
            String userInput = scanner.next();
            if (userInput.equals("1")) {
                if(player.moveToAdjacentCity()) {
                    actionCounter += 1;
                }
            }
        }
    }
}
