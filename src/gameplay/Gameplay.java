package gameplay;

import carddecks.Deck;
import carddecks.DiscardPile;
import carddecks.DiseasesDeck;
import carddecks.PlayersDeck;
import map.Map;
import players.Player;
import players.HumanPlayer;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Gameplay {
    // Map
    private final Map MAP = new Map();

    private final Player[] PLAYERS = initialisePlayers(MAP);

    // Card decks
    private final PlayersDeck PLAYERS_DECK = new PlayersDeck(4);
    private final DiseasesDeck DISEASES_DECK = new DiseasesDeck();
    private final DiscardPile PLAYERS_DISCARD_PILE = new DiscardPile();

    // Things we need to keep track of
    private int infectionRate = 2;
    private int numberOfOutbreaks = 0;
    private boolean blueCured = false;
    private boolean blackCured = false;
    private boolean redCured = false;
    private boolean yellowCured = false;

    /*
    Spawn board
    Spawn card decks
    Keep count of: infection rate till 4, epidemics till 8 (what else?)
    At the beginning cards are dealt to players - 4 cards per player in 2 player game
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

        // JUST SOME DEBUGGING AND TESTING
        System.out.println(PLAYERS[0].getCards());
        PLAYERS[0].startingCards(PLAYERS_DECK);
        System.out.println("BEFORE");
        for (int i = 0; i < PLAYERS[0].getCards().size(); i++) {
            System.out.println(PLAYERS[0].getCards().get(i).getNAME());
        }
        PLAYERS[0].drawCard(PLAYERS_DECK, PLAYERS_DISCARD_PILE);
        System.out.println("AFTER");
        for (int i = 0; i < PLAYERS[0].getCards().size(); i++) {
            System.out.println(PLAYERS[0].getCards().get(i).getNAME());
        }
        System.out.println("DISCARD PILE");
        for (int i = 0; i < PLAYERS_DISCARD_PILE.getDeck().size(); i++) {
            System.out.println(PLAYERS_DISCARD_PILE.getDeck().get(i).getNAME());
        }

        // Loop for turns
        for (Player player : this.PLAYERS) {
            turn(player);
        }
    }

    // Public methods
    public Player[] getPLAYERS() {
        return PLAYERS;
    }

    public int getInfectionRate() {
        return infectionRate;
    }

    public void increaseInfectionRate() {
        this.infectionRate += 1;
    }

    public int getNumberOfOutbreaks() {
        return numberOfOutbreaks;
    }

    public void setNumberOfOutbreaks() {
        this.numberOfOutbreaks += 1;
    }

    // Private methods

    /*
    Initialise players on the board. All players are initially in Atlanta.
    */
    private Player[] initialisePlayers(Map map) {
        String INITIAL_CITY = "Atlanta";
        HumanPlayer human = new HumanPlayer("Red", "Dispatcher");
        HumanPlayer anotherHuman = new HumanPlayer("Blue", "Medic");
        Player[] players = {human, anotherHuman};
        for (Player player : players) {
            player.setCurrentCity(map.getCITIES().get(INITIAL_CITY));
        }
        return players;
    }

    // Maybe add all players to an array and perform turn for each element ?
    private void turn(Player player) {
        System.out.println("\n" + player.getPLAYER_ID() + " player is playing.");
        int actionCounter = 0;
        Scanner scanner = new Scanner(System.in);
        while (actionCounter < 4) {
            System.out.println("What do you want to do next?");
            System.out.println("1. move to the adjacent city");
            String userInput = scanner.next();
            if (userInput.equals("1")) {
                if (player.moveToAdjacentCity()) {
                    actionCounter += 1;
                }
            } else if (userInput.equals("2")) {

            }
        }
    }
}
