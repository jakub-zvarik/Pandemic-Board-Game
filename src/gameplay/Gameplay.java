package gameplay;

// Card & Decks
import cards.Card;
import carddecks.DiscardPile;
import carddecks.DiseaseDeck;
import carddecks.PlayersDeck;
// City & Map
import cities.City;
import map.Map;
// Players
import players.Player;
import players.HumanPlayer;
// Chatbot
import chatbot.Chatbot;
// Others
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
Gameplay class contains all the game rules and gameplay agent. Gameplay agent makes sure that the gameplay is
progressing as it is supposed to, checking all the rules and edge cases. Inside the Gameplay class, all other
objects are initialised to put together the game. Gameplay takes care of player's turns, calls Chatbot to evaluate
player's intentions and takes care of checking for outbreaks, epidemic cards, infections, evaluating if the game
is lost or won.
*/
public class Gameplay {
    // Map
    private final Map MAP = new Map();

    // Players
    private final HumanPlayer[] PLAYERS = initialisePlayers();
    private final String PLAYERS_INITIAL_CITY = "Atlanta";
    private final int ACTIONS_PER_TURN = 4;

    // Chatbot
    private final Chatbot CHAT = new Chatbot();

    // Decks
    private final PlayersDeck PLAYERS_DECK = new PlayersDeck();
    private final DiseaseDeck DISEASES_DECK = new DiseaseDeck();
    private final DiscardPile PLAYERS_DISCARD_PILE = new DiscardPile();
    private final DiscardPile DISEASES_DISCARD_PILE = new DiscardPile();

    // Infection rates and outbreaks
    private final int[] INFECTION_RATES = {2, 2, 3, 4};
    private final int MAX_INFECTION_INDEX = 6;
    private final int MAX_NUMBER_OUTBREAKS = 8;
    // Infection index points to infection rate in the infection rates array
    private int infectionRateIndex = 0;
    // Counting number of outbreaks for losing cases
    private int numberOfOutbreaks = 0;

    // Constructor and also the main gameplay loop
    public Gameplay() throws FileNotFoundException {
        // Gameplay loop
        firstInfection();
        while (!losingCases() && !winningCase()) {
            for (HumanPlayer player : this.PLAYERS) {
                turn(player);
                infect();
            }
        }
    }

    // Private methods

    // Initialise players on the board in the initial city (Atlanta by default). All players get starting cards.
    private HumanPlayer[] initialisePlayers() {
        HumanPlayer human = new HumanPlayer("Red");
        HumanPlayer anotherHuman = new HumanPlayer("Blue");
        HumanPlayer[] players = {human, anotherHuman};
        for (Player player : players) {
            player.setCurrentCity(this.MAP.getCITIES().get(this.PLAYERS_INITIAL_CITY));
            player.startingCards(this.PLAYERS_DECK);
        }
        return players;
    }

    /*
    Turn for players. This method first announces basic information about the player,
    shows their cards and then invoke chatbot. Chatbot returns its evaluation about what the user
    wants, and other methods are invoked in accord to this evaluation. Every player has 4 actions
    per turn (by default). Some actions (the informative actions without effect on game) do not take away
    any player's action. At the end of every player's play (after all turns) the player will draw cards
    and gameplay agent checks if there are no epidemic cards drawn.
    */
    private void turn(HumanPlayer player) {
        int actionCounter = 1;
        while (actionCounter <= this.ACTIONS_PER_TURN) {
            playerInformationBanner(player);
            System.out.println("What do you want to do next?");
            System.out.println("Action: " + actionCounter + " out of " + this.ACTIONS_PER_TURN);
            // Chatbot, evaluation and action call
            int CHATBOT_VERDICT = CHAT.chatTurn();
            if (CHATBOT_VERDICT == 2) {
                whatToDo();
            } else if (CHATBOT_VERDICT == 3) {
                showInfectedCities();
            } else if (CHATBOT_VERDICT == 4) {
                if (player.moveToAdjacentCity()) {
                    actionCounter += 1;
                }
            } else if (CHATBOT_VERDICT == 5) {
                if (player.flyToCity(this.MAP, this.PLAYERS_DISCARD_PILE)) {
                    actionCounter += 1;
                }
            } else if (CHATBOT_VERDICT == 6) {
                if (player.cureCity()) {
                    actionCounter += 1;
                }
            } else if (CHATBOT_VERDICT == 7) {
                if (player.findCure(this.PLAYERS_DISCARD_PILE)) {
                    setCuredParameter(player);
                    actionCounter +=1;
                }
            }
        }
        player.drawCard(this.PLAYERS_DECK, this.PLAYERS_DISCARD_PILE);
        epidemicCheck();
    }

    // First infection in the game! In the first infection 2 cities are infected, first by 3 and second by 2 cubes.
    private void firstInfection() {
        int FIRST_CARD_INDEX = 0;
        for (int diseaseCards = 0; diseaseCards < this.INFECTION_RATES[this.infectionRateIndex]; diseaseCards++) {
            this.DISEASES_DECK.discard(FIRST_CARD_INDEX, this.DISEASES_DISCARD_PILE);
        }
        City FIRST_CITY = this.MAP.getCITIES().get(this.DISEASES_DISCARD_PILE.getDECK().get(1).getNAME());
        City SECOND_CITY = this.MAP.getCITIES().get(this.DISEASES_DISCARD_PILE.getDECK().get(0).getNAME());
        FIRST_CITY.setInfectionCubes(3);
        SECOND_CITY.setInfectionCubes(2);
    }


    /*
    Checks for epidemic card in the last drawn cards in the players discard pile, removes it and returns true,
    otherwise false.
    */
    private void epidemicCheck() {
        int CARDS_TO_CHECK = infectionRateIndex;
        if (this.PLAYERS_DISCARD_PILE.checkDeckSizeBeforeDraw() == 1) {
            CARDS_TO_CHECK = 1;
        } else if (this.PLAYERS_DISCARD_PILE.checkDeckSizeBeforeDraw() == 0) {
            CARDS_TO_CHECK = 0;
        }
        for (int cardIndex = 0; cardIndex < CARDS_TO_CHECK; cardIndex++) {
            Card card = this.PLAYERS_DISCARD_PILE.getDECK().get(cardIndex);
            if (card.getNAME().contains("EPIDEMIC")) {
                this.PLAYERS_DISCARD_PILE.removeCard(cardIndex);
                epidemic();
            }
        }
    }

    /*
    Epidemic! If epidemic card is drawn, this method performs three steps: increase the infection rate,
    performs epidemic infection, and intensify the game.
    */
    private void epidemic() {
        int FIRST_CARD_INDEX = 0;
        // Increase the infection rate
        if (this.infectionRateIndex < this.MAX_INFECTION_INDEX) {
            this.infectionRateIndex += 1;
        }
        System.out.println("EPIDEMIC! New infection rate: " + this.INFECTION_RATES[infectionRateIndex]);
        // Infect
        epidemicInfect();
        // (Intensify)
        // Shuffle disease discard pile
        this.DISEASES_DISCARD_PILE.shuffleDeck(this.DISEASES_DISCARD_PILE.getDECK());
        // Place the discard pile back on top of disease deck
        int CARDS_TO_DISCARD = this.DISEASES_DISCARD_PILE.getDECK().size();
        for (int cards = 0; cards < CARDS_TO_DISCARD; cards ++) {
            this.DISEASES_DISCARD_PILE.discard(FIRST_CARD_INDEX, this.DISEASES_DECK);
        }
    }

    /*
    Epidemic infect is special kind of infection that only occurs when EPIDEMIC card is drawn.
    If there is 0 infection cubes in the drawn city, it will be increase it to 3. If there is more than 0
    cubes in the city already, this method will proceed to increase it to 3 and initiate outbreak.
    */
    private void epidemicInfect() {
        int TOP_CARD_INDEX = 0;
        int LAST_CARD = this.DISEASES_DECK.getDECK().size() - 1;
        // Array to keep track of cities with outbreak - so it won't occur in the same city multiple times in one turn.
        ArrayList<City> citiesWithOutbreak = new ArrayList<>();
        this.DISEASES_DECK.discard(LAST_CARD, this.DISEASES_DISCARD_PILE);
        City city = this.MAP.getCITIES().get(this.DISEASES_DISCARD_PILE.getDECK().get(TOP_CARD_INDEX).getNAME());
        // Decide if outbreak occurs or not
        if (city.getInfectionCubes() > 0) {
            city.setInfectionCubes(3);
            outbreakOrInfection(city, citiesWithOutbreak);
        } else {
            city.setInfectionCubes(3);
        }
    }

    /*
    Infection of cities when disease card with the city name is drawn from the disease deck.
    OutbreakOrInfection method is called to decide if there is outbreak in the city or the
    city will get classic infection. Creates ArrayList which is used to keep track of cities
    where outbreak already occurred.
    */
    private void infect() {
        int TOP_CARD_INDEX = 0;
        ArrayList<City> citiesWithOutbreak = new ArrayList<>();
        if (this.DISEASES_DECK.getDECK().size() > 0) {
            for (int diseaseCards = 0; diseaseCards < this.INFECTION_RATES[this.infectionRateIndex]; diseaseCards++) {
                this.DISEASES_DECK.discard(TOP_CARD_INDEX,this.DISEASES_DISCARD_PILE);
                City city = this.MAP.getCITIES().get(this.DISEASES_DISCARD_PILE.getDECK().get(TOP_CARD_INDEX).getNAME());
                outbreakOrInfection(city, citiesWithOutbreak);
            }
        }
    }

    /*
    OutbreakOrInfection checks if city already has 3 infection cubes, if yes, it starts infecting adjacent cities
    (outbreak). If city has not 3 infection cubes, the number of infection cubes is increased by 1. ArrayList taken
    into this method as parameter keeps up which cities had already got outbreak. This way city can have only one
    outbreak during one round.
    */
    private void outbreakOrInfection(City city, ArrayList<City> citiesWithOutbreak) {
        final int MAX_INFECTION_CUBES = 3;
        if (city.getInfectionCubes() == MAX_INFECTION_CUBES && !citiesWithOutbreak.contains(city)) {
            System.out.println("Outbreak in " + city.getNAME() + "!");
            citiesWithOutbreak.add(city);
            this.numberOfOutbreaks += 1;
            for (City adjacentCity : city.getAdjacentCities()) {
                outbreakOrInfection(adjacentCity, citiesWithOutbreak);
            }
        } else if (city.getInfectionCubes() < 3 && !city.getCureFound()) {
            city.increaseInfectionCubes();
        }
    }


    // Show all infected cities and how many infection cubes do they currently have.
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

    // Eradicate disease sets city parameter "curedDisease" to true.
    private void setCuredParameter(Player player) {
        String color = player.getCurrentCity().getCOLOR();
        for (String key : this.MAP.getCITIES().keySet()) {
            if (this.MAP.getCITIES().get(key).getCOLOR().equals(color)) {
                this.MAP.getCITIES().get(key).setCuredDisease();
            }
        }
    }


    // Display player's color, position and information about cure for current city color
    private void playerInformationBanner(Player player) {
        System.out.println("\n" + player.getPLAYER_ID() + " player is playing.");
        System.out.println("Current position: " + player.getCurrentCity().getNAME() + " | Color: " +
                player.getCurrentCity().getCOLOR() + " | Cure found: " + player.getCurrentCity().getCureFound() +
                " | Infection cubes: " + player.getCurrentCity());
        System.out.println("Number of outbreaks: " + this.numberOfOutbreaks);
        showCards(player);
    }

    // Manual for player with description of possible actions.
    private void whatToDo() {
        System.out.println("You can do these actions:");
        System.out.println("to show infections type: infections");
        System.out.println("move to city adjacent to your current position - type: move and then choose city");
        System.out.println("fly to another city type: fly and then choose city");
        System.out.println("to cure disease (remove 1 disease cube) in your current city type: cure");
        System.out.println("to find cure for disease type: find cure");
    }

    // Show player's cards
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
            if (this.MAP.getCITIES().get(key).getCureFound()) {
                cured +=1;
            }
        }
        if (cured == this.MAP.getCITIES().getSize()) {
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
        if (this.PLAYERS_DECK.getDECK().isEmpty()) {
            System.out.println("No more cards left in the player's deck. You lost.");
            return true;
        } else if (this.numberOfOutbreaks >= this.MAX_NUMBER_OUTBREAKS) {
            System.out.println("You reached maximum number of outbreaks! You lost.");
            return true;
        }
        return false;
    }
}