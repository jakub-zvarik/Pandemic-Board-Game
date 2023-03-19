package carddecks;

import cards.Card;
import cards.CityCard;
import filepaths.Filepaths;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public abstract class Deck implements Filepaths {

    protected ArrayList<Card> deck = new ArrayList<>();

    public Deck() {

    }

    // Public methods
    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    // NOTE - make sure about these border cases
    // Check how many cards are left in the deck. Every turn 2 cards are drawn from the deck, 1 can be drawn too.
    public int checkDeckSizeBeforeDraw() {
        if (this.deck.size() == 1) {
            return 1;
        } else if (this.deck.size() == 0) {
            System.out.println("No cards left in the deck!");
            return 0;
        }
        return 2;
    }


    // Protected methods

    /*
    Initialising cards from the CSV file containing cities. Most of the players cards and disease
    cards only contain city name and color. This method is used to wholly initialise the disease
    card deck since this deck only has cards with cities.
    */
    protected ArrayList<Card> initialiseCardsFromFile() throws FileNotFoundException {
        ArrayList<Card> cards = new ArrayList<>();
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] reading = scan.nextLine().split(",");
            String cityName = reading[0];
            String cityColor = reading[1];
            CityCard card = new CityCard(cityName, cityColor);
            cards.add(card);
        }
        return cards;
    }

    protected void shuffleDeck(ArrayList<Card> deckToShuffle) {
        Collections.shuffle(deckToShuffle);
    }

}
