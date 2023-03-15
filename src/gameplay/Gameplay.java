package gameplay;

import board.Board;

import java.io.FileNotFoundException;

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

    If epidemic card - check rules
    */

    public Gameplay() throws FileNotFoundException {
        new Board();
    }
}
