package players;

import carddecks.Deck;
import support.EditDistance;
import cities.City;
import cards.Card;

import java.util.ArrayList;

public abstract class Player implements EditDistance {
    protected final String PLAYER_COLOR;
    protected City currentCity;
    protected final ArrayList<Card> CARDS = new ArrayList<>();

    // Init, getters and setters
    public Player(String playerColor) {
        this.PLAYER_COLOR = playerColor;
    }

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
    Player at the end of their turn discard cards from the player deck, if there are any. City cards are
    added into player's hand if they have space, if not,
    */
    public void drawCard(Deck playersDeck, Deck playersDeckOut) {
        // Players always draws 2 cards if there is enough cards
        int CARDS_TO_DRAW = playersDeck.checkDeckSizeBeforeDraw();
        // Check how many cards can player get
        int NUM_CARDS_FOR_PLAYER = playersCardsSlotsAvailable();
        int TOP_CARD_INDEX = 0;
        // Iterate over cards to discard from the player's deck and place them in the deck discard pile
        for (int card = 0; card < CARDS_TO_DRAW; card++) {
            playersDeckOut.getDeck().add(TOP_CARD_INDEX, playersDeck.getDeck().get(TOP_CARD_INDEX));
            playersDeck.getDeck().remove(TOP_CARD_INDEX);
        }
        // If the card drawn is not EPIDEMIC card and player have space for new cards, add it to the player's hand
        int cardPointer = 0;
        for (int cardToPlayer = 0; cardToPlayer < NUM_CARDS_FOR_PLAYER; cardToPlayer++) {
            // Player can't get EPIDEMIC card and can't take more cards than were dealt
            if (!playersDeckOut.getDeck().get(cardPointer).getNAME().contains("EPIDEMIC") &&
                    cardToPlayer <= CARDS_TO_DRAW) {
                this.CARDS.add(TOP_CARD_INDEX, playersDeckOut.getDeck().get(cardPointer));
                playersDeckOut.getDeck().remove(cardPointer);
            } else {
                // Card pointer increased if there is EPIDEMIC card so the next card is drawn if conditions are met
                cardPointer += 1;
            }
        }
    }

    // Protected methods
    protected void discard(Card card, Deck DiscardPile) {
        this.CARDS.remove(card);
        DiscardPile.getDeck().add(0, card);
    }

    // Private methods

    /*
    Method to check how many cards does player already has. This determines how many cards
    can player discard from the deck. It returns the number of open slots up to 2.
    */
    private int playersCardsSlotsAvailable() {
        int MAX_CARDS = 7;
        if (this.CARDS.size() <= MAX_CARDS - 2) {
            return 2;
        } else if (this.CARDS.size() == MAX_CARDS - 1) {
            System.out.println("You can only discard one card!");
            return 1;
        }
        System.out.println("You can't draw any more cards! Discard something.");
        return 0;
    }
}
