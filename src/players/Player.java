package players;

import carddecks.Deck;
import support.EditDistance;
import cities.City;
import cards.Card;

import java.util.ArrayList;

/*
Player abstract class lays down blue-print for other Player classes. Here, common methods and class variables
are stored. Every Player object has color, current position, and array of cards (player's hand). Methods are
performing some of basic check and actions.
*/
public abstract class Player implements EditDistance {

    protected final String PLAYER_COLOR;
    protected City currentCity;
    protected final ArrayList<Card> CARDS = new ArrayList<>();

    // Cards settings
    protected final int MAX_CARDS = 7;
    protected final int NUM_STARTING_CARDS = 4;
    protected final int CARDS_TO_FIND_CURE = 5;

    // Public methods

    // Constructor
    public Player(String playerColor) {
        this.PLAYER_COLOR = playerColor;
    }

    // Getters and setter
    public String getPLAYER_ID() {
        return this.PLAYER_COLOR;
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


    // Player draws starting cards. Player get 4 cards from the player's deck, EPIDEMIC cards are filtered out.
    public void startingCards(Deck playersDeck) {
        int cardIndexPointer = 0;
        while (this.CARDS.size() < this.NUM_STARTING_CARDS) {
            // Check if card is not EPIDEMIC card and if not, add into player's hand
            if (!playersDeck.getDECK().get(cardIndexPointer).getNAME().equals("EPIDEMIC")) {
                this.CARDS.add(0, playersDeck.getDECK().get(cardIndexPointer));
                playersDeck.getDECK().remove(cardIndexPointer);
            } else {
                cardIndexPointer += 1;
            }
        }
    }

    /*
    Player at the end of their turn discard cards from the player deck, if there are any. City cards are
    added into player's hand if they have space, if not,
    */
    public void drawCard(Deck playersDeck, Deck playersDeckPile) {
        // Players always draws 2 cards if there is enough cards
        int CARDS_TO_DRAW = playersDeck.checkDeckSizeBeforeDraw();
        // Check how many cards can player get
        int NUM_CARDS_FOR_PLAYER = playersCardsSlotsAvailable();
        int TOP_CARD_INDEX = 0;
        // Iterate over cards to discard from the player's deck and place them in the deck discard pile
        for (int card = 0; card < CARDS_TO_DRAW; card++) {
            playersDeckPile.getDECK().add(TOP_CARD_INDEX, playersDeck.getDECK().get(TOP_CARD_INDEX));
            playersDeck.getDECK().remove(TOP_CARD_INDEX);
        }
        // If the card drawn is not EPIDEMIC card and player have space for new cards, add it to the player's hand
        int cardPointer = 0;
        for (int cardToPlayer = 0; cardToPlayer < NUM_CARDS_FOR_PLAYER; cardToPlayer++) {
            // Player can't get EPIDEMIC card and can't take more cards than were dealt
            if (!playersDeckPile.getDECK().get(cardPointer).getNAME().contains("EPIDEMIC") &&
                    cardToPlayer <= CARDS_TO_DRAW) {
                this.CARDS.add(TOP_CARD_INDEX, playersDeckPile.getDECK().get(cardPointer));
                playersDeckPile.getDECK().remove(cardPointer);
            } else {
                // Card pointer increased if there is EPIDEMIC card so the next card is drawn if conditions are met
                cardPointer += 1;
            }
        }
    }

    /*
    CureCity method allows player to decrease number of infection cubes in city. If cure has been found,
    infection cubes are set to 0 right away. If cure has not been found yet, infection cubes are decreased
    by 1. If there is no infection cube in the current city, player gets this information.
    */
    public boolean cureCity() {
        // Check for infection cubes and if cure is found
        if (this.currentCity.getInfectionCubes() > 0 && this.currentCity.getCureFound()) {
            this.currentCity.setInfectionCubes(0);
            System.out.println("New number of infection cubes in " + this.currentCity.getNAME() + " is " +
                    this.currentCity.getInfectionCubes());
            return true;
        }
        // If cure not found yet, decrease infection cubes by 1
        else if (this.currentCity.getInfectionCubes() > 0) {
            this.currentCity.decreaseInfectionCubes();
            System.out.println("New number of infection cubes in " + this.currentCity.getNAME() + " is " +
                    this.currentCity.getInfectionCubes());
            return true;
        }
        // Information if city has no infection cards
        System.out.println("There is no disease cube in this city.");
        return false;
    }

    /*
    Method to find a cure. First, check if player has enough cards for this step is performed. If yes,
    array list of cards to remove is created, where all the cards to discard are stored. This array
    is then used to discard the cards from player's hand.
    */
    public boolean findCure(Deck discardDeck) {
        if (cardsCounterFindCure()) {
            // Check and place all cards that will be removed from player's hand to array
            ArrayList<Card> cardsToRemove = new ArrayList<>();
            while (cardsToRemove.size() != this.CARDS_TO_FIND_CURE) {
                for (Card card : this.CARDS) {
                    if (card.getCOLOR().equals(this.currentCity.getCOLOR())) {
                        cardsToRemove.add(card);
                    }
                }
            }
            // Discard cards into discard deck
            for (Card card : cardsToRemove) {
                this.discard(card, discardDeck);
            }
            System.out.println("Congrats! You found cure for " + this.currentCity.getCOLOR() + " disease!");
            System.out.println("Now you can cure all infection cubes in city at once by command 'cure'!");
            return true;

        }
        // If player does not have enough cards to research cure
        System.out.println("You do not have enough cards to cure " + this.currentCity.getCOLOR() + " disease.");
        return false;
    }

    // Protected methods

    // Method to discard card from player's hand into the discard pile. Card is removed from the player's hand.
    protected void discard(Card card, Deck DiscardPile) {
        DiscardPile.getDECK().add(0, card);
        this.CARDS.remove(card);
    }

    // Private methods

    /*
    Method to check how many cards does player already has. This determines how many cards
    can player draw from the deck. It returns the number of open slots up to 2.
    */
    private int playersCardsSlotsAvailable() {
        // If player has enough slots to take 2 cards
        if (this.CARDS.size() <= this.MAX_CARDS - 2) {
            return 2;
        }
        // Else if player has only one slot available
        else if (this.CARDS.size() == this.MAX_CARDS - 1) {
            return 1;
        }
        // Otherwise player can't draw more cards
        System.out.println("You can't draw any more cards! Discard something.");
        return 0;
    }

    // Card counter is checking if player has enough cards to research cure for disease.
    private boolean cardsCounterFindCure() {
        int cardCounter = 0;
        for (Card card : this.CARDS) {
            // Check if card's color is the same as city color and increase counter if yes
            if (card.getCOLOR().equals(this.getCurrentCity().getCOLOR())) {
                cardCounter += 1;
            }
            // If enough cards were found, return true
            if (cardCounter == this.CARDS_TO_FIND_CURE) {
                return true;
            }
        }
        return false;
    }
}