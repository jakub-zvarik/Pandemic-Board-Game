package players;

import carddecks.Deck;
import cards.EpidemicCard;
import cities.City;
import cards.Card;
import map.Map;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {
    private final String PLAYER_COLOR;
    private final String SPECIALISATION;
    private City currentCity;
    private final int MAX_CARDS = 7;
    private final ArrayList<Card> CARDS = new ArrayList<>();

    // Init, getters and setters
    public Player(String playerColor, String specialisation) {
        this.PLAYER_COLOR = playerColor;
        this.SPECIALISATION = specialisation;
    }

    public String getPLAYER_ID() {
        return this.PLAYER_COLOR;
    }

    public String getSPECIALISATION() {
        return this.SPECIALISATION;
    }

    public City getCurrentCity() {
        return this.currentCity;
    }

    public void setCurrentCity(City newPosition) {
        this.currentCity = newPosition;
    }


    public ArrayList<Card> getCards() {
        return CARDS;
    }

    /*
    Player draws starting cards. Players get 4 cards each and these cards can't be EPIDEMIC cards.
    */
    public void startingCards(Deck playersDeck) {
        int cardIndexPointer = 0;
        while (this.CARDS.size() < 4) {
            // Check if card is not EPIDEMIC card and if not, add into players cards
            if (!playersDeck.getDeck().get(cardIndexPointer).getNAME().equals("EPIDEMIC")) {
                this.CARDS.add(0,playersDeck.getDeck().get(cardIndexPointer));
                playersDeck.getDeck().remove(cardIndexPointer);
            } else {
                cardIndexPointer += 1;
            }
        }
    }

    /*
    Player at the end of their turn draw cards from the player deck, if there are any. City cards are
    added into player's hand if they have space, if not,
    */
    public void drawCard(Deck playersDeck, Deck playersDeckOut) {
        // Players always draw 2 cards if there is enough cards
        int CARDS_TO_DRAW = playersDeck.checkDeckSizeBeforeDraw();
        // Check how many cards can player get
        int NUM_CARDS_FOR_PLAYER = playersCardsSlotsAvailable();
        int TOP_CARD_INDEX = 0;
        // Iterate over cards to draw from the player's deck and place them in the deck of drawn cards
        for (int draw = 0; draw < CARDS_TO_DRAW; draw++) {
            playersDeckOut.getDeck().add(TOP_CARD_INDEX, playersDeck.getDeck().get(TOP_CARD_INDEX));
            playersDeck.getDeck().remove(TOP_CARD_INDEX);
        }

        // If the card drawn is not EPIDEMIC card and player have space for new cards, add it to the player's hand
        int cardPointer = 0;
        for (int cardToPlayer = 0; cardToPlayer < NUM_CARDS_FOR_PLAYER; cardToPlayer++) {
            // Player can't get EPIDEMIC card and can't draw more cards than were dealt
            if (!playersDeckOut.getDeck().get(cardPointer).getNAME().contains("EPIDEMIC") &&
                    cardToPlayer <= CARDS_TO_DRAW) {
                this.CARDS.add(TOP_CARD_INDEX, playersDeckOut.getDeck().get(cardPointer));
                playersDeckOut.getDeck().remove(cardPointer);
            } else {
                cardPointer += 1;
            }
        }
    }

    // Basic player's actions
    public boolean moveToAdjacentCity() {
        Scanner scanner = new Scanner(System.in);
        int cityNumber = 0;
        for (City city : this.currentCity.getAdjacentCities()) {
            cityNumber += 1;
            System.out.println("City number " + cityNumber + " - city name: " + city.getNAME() + " and color: "
                    + city.getCOLOR());
        }
        System.out.println("Where do you want to go? Type in the number of the city or 0 to go back.");
        String userInput = scanner.next();
        if (Integer.parseInt(userInput) <= this.currentCity.getAdjacentCities().length && Integer.parseInt(userInput) > 0) {
            setCurrentCity(this.currentCity.getAdjacentCityIndex(Integer.parseInt(userInput) - 1));
            System.out.println("You moved to " + this.currentCity.getNAME());
            return true;
        } else if (Integer.parseInt(userInput) == 0) {
            System.out.println("Back to possible moves...");
            return false;
        } else {
            System.out.println("Wrong input.");
            return false;
        }
    }

    public boolean flightToCity(String cityName, Map map) {
        City city = map.getCITIES().get(cityName);
        for (Card card : this.CARDS) {
            if(card.getNAME().equals(city.getNAME())) {
                this.setCurrentCity(city);
                return true;
            } else {
                System.out.println("You do not have the needed city card for this flight.");
            }
        }
        return false;
    }

    public void charterFlightToAnyCity(City cityName) {

    }

    // Private methods

    /*
    Method to check how many cards does player already has. According to that, legality of new
    drawn is checked. This determines how many cards can player draw from the deck.
    */
    private int playersCardsSlotsAvailable() {
        if (this.CARDS.size() <= this.MAX_CARDS - 2) {
            return 2;
        } else if (this.CARDS.size() == this.MAX_CARDS - 1) {
            System.out.println("You can only draw one card!");
            return 1;
        }
        System.out.println("You can't draw any card, you don't have any slots available!");
        return 0;
    }
}

