package gameplay;

// Cards packages
import carddecks.DiscardPile;
import carddecks.DiseaseDeck;
import carddecks.PlayersDeck;
// Map and players
import cards.Card;
import cities.City;
import map.Map;
import players.Player;
import players.HumanPlayer;
// Chatbot
import chatbot.Chatbot;
// Others
import java.io.FileNotFoundException;

public class Gameplay {
    // Map
    private final Map MAP = new Map();

    private final Player[] PLAYERS = initialisePlayers();

    // Card decks
    private final PlayersDeck PLAYERS_DECK = new PlayersDeck();
    private final DiseaseDeck DISEASES_DECK = new DiseaseDeck();
    private final DiscardPile PLAYERS_DISCARD_PILE = new DiscardPile();
    private final DiscardPile DISEASES_DISCARD_PILE = new DiscardPile();

    // Things we need to keep track of
    private int startingInfectionRate = 2;
    private int numberOfOutbreaks = 0;
    private final int MAX_NUMBER_OUTBREAKS = 8;

    /*
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
        Chatbot chat = new Chatbot();
        PLAYERS[0].startingCards(PLAYERS_DECK);
        PLAYERS[1].startingCards(PLAYERS_DECK);

        // Loop for turns
        infect();
        while (!losingCases() && !winningCase()) {
            for (Player player : this.PLAYERS) {
                turn((HumanPlayer) player, chat);
                infect();
            }
        }
    }

    // Public methods
    public Player[] getPLAYERS() {
        return PLAYERS;
    }

    public int getInfectionRate() {
        return startingInfectionRate;
    }

    public void increaseInfectionRate() {
        this.startingInfectionRate += 1;
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
    private Player[] initialisePlayers() {
        String INITIAL_CITY = "Atlanta";
        HumanPlayer human = new HumanPlayer("Red");
        HumanPlayer anotherHuman = new HumanPlayer("Blue");
        Player[] players = {human, anotherHuman};
        for (Player player : players) {
            player.setCurrentCity(this.MAP.getCITIES().get(INITIAL_CITY));
        }
        return players;
    }

    /*
    Turn for every human player. This method first announces basic information about the player,
    shows their cards and then invoke chatbot. Chatbot returns it evaluation about what the user
    wants, and other methods are invoked in accord with this evaluation. Every player has 4 actions
    per turn. Some actions (mainly the informative actions without effect on game) do not take away
    any action.
    */
    private void turn(HumanPlayer player, Chatbot chat) {
        final int ACTIONS_PER_TURN = 5;
        int actionCounter = 1;
        while (actionCounter < ACTIONS_PER_TURN) {
            showCards(player);
            System.out.println("What do you want to do next?");
            System.out.println("Action: " + actionCounter + " out of 4.");
            int verdict = chat.chatTurn();
            if (verdict == 2) {
                whatToDo();
            } else if (verdict == 3) {
                showInfectedCities();
            } else if (verdict == 4) {
                if (player.moveToAdjacentCity()) {
                    actionCounter += 1;
                }
            } else if (verdict == 5) {
                if (player.flyToCity(this.MAP, this.PLAYERS_DISCARD_PILE)) {
                    actionCounter += 1;
                }
            } else if (verdict == 6) {
                if (player.cureCity()) {
                    actionCounter += 1;
                }
            } else if (verdict == 7) {
                if (player.findCure(this.PLAYERS_DISCARD_PILE)) {
                    eradicateDisease(player);
                    actionCounter +=1;
                }
            }
        }
        player.drawCard(PLAYERS_DECK, PLAYERS_DISCARD_PILE);
    }

    /*
    !!!!!!!!!!!! Check edge cases
    */
    private void infect() {
        if (this.DISEASES_DECK.getDeck().size() > 0) {
            for (int diseaseCards = 0; diseaseCards < startingInfectionRate; diseaseCards++) {
                this.DISEASES_DECK.discard(this.DISEASES_DISCARD_PILE);
                City city = this.MAP.getCITIES().get(this.DISEASES_DISCARD_PILE.getDeck().get(0).getNAME());
                outbreakOrNot(city);
            }
        }
    }

    private void outbreakOrNot(City city) {
        final int MAX_INFECTION_CUBES = 3;
        this.numberOfOutbreaks += 1;
        if (city.getInfectionCubes() == MAX_INFECTION_CUBES) {
            for (City adjacentCity : city.getAdjacentCities()) {
                outbreakOrNot(adjacentCity);
            }
        } else {
            city.increaseInfectionCubes();
        }
    }

    /*
    Manual for player with description of possible actions.
    */
    private void whatToDo() {
        System.out.println("You can do these actions:");
        System.out.println("move to city adjacent to your current position");
    }

    /*
    Show infected cities and how many infection cubes do they currently have.
    */
    private void showInfectedCities() {
        int counterOfInfected = 0;
        for (String key : this.MAP.getCITIES().keySet()) {
            int infections = this.MAP.getCITIES().get(key).getInfectionCubes();
            if (infections > 0) {
                String color = this.MAP.getCITIES().get(key).getCOLOR();
                System.out.println(key + " (color: " + color + ") has " + infections + " infections.");
                counterOfInfected += 1;
            }
        }
        if (counterOfInfected == 0) {
            System.out.println("There are no infected cities currently.");
        }
    }

    /*
    Eradicate disease sets city parameter "curedDisease" to true.
    */
    private void eradicateDisease(Player player) {
        String color = player.getCurrentCity().getCOLOR();
        for (String key : this.MAP.getCITIES().keySet()) {
            if (this.MAP.getCITIES().get(key).getCOLOR().equals(color)) {
                this.MAP.getCITIES().get(key).setCuredDisease();
            }
        }
    }

    private void playerInformationBanner(Player player) {
        System.out.println("\n" + player.getPLAYER_ID() + " player is playing.");
        System.out.println("Position: " + player.getCurrentCity().getNAME() + " color: " + player.getCurrentCity().getCOLOR());
        showCards(player);
    }

    /*
    Method to show current player's cards.
    */
    private void showCards(Player player) {
        System.out.println("\n" + player.getPLAYER_ID() + " player's CARDS:");
        for (Card card : player.getCards()) {
            System.out.println(card.getNAME() + " color: " + card.getCOLOR());
        }
    }

    /*
    Check if all cures were discovered - if yes, players win! Every city has boolean property
    indicating if the city was cured. If cure is found, every city of the disease color has
    it cured property switched to true. If all cities (therefore diseases of all colors) are
    cured, players win.
    */
    private boolean winningCase() {
        int cured = 0;
        for (String key : this.MAP.getCITIES().keySet()) {
            if (this.MAP.getCITIES().get(key).getCuredDisease()) {
                cured +=1;
            }
        }
        if (cured == this.MAP.getNUMBER_OF_CITIES()) {
            System.out.println("You found cure for every disease! Congrats, you win!");
            return true;
        }
        return false;
    }

    /*
    Checking for cases in which players loose the game. These are if player's card deck
    is empty or the number of outbreaks reached 8 outbreaks. If any of these conditions
    are met, the game loop will be stopped.
    */
    private boolean losingCases() {
        if (this.PLAYERS_DECK.getDeck().isEmpty()) {
            System.out.println("No more cards left in the player's deck. You lost!");
            return true;
        } else if (this.numberOfOutbreaks == this.MAX_NUMBER_OUTBREAKS) {
            System.out.println("Number of outbreaks reached 8! You lost!");
            return true;
        }
        return false;
    }
}