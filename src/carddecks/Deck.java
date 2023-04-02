package carddecks;
// Cards packages
import cards.Card;
// Support package containing file path to CSV with cities and colors
import support.Filepaths;
// Other packages
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
Abstract class laying down blue-print for Deck objects.
Deck is an object composed of cards. There are three different deck types in the
game - Player's deck, Disease deck and Discard piles for both decks. Decks are
initialised mostly from CSV file containing all city names and colors.
*/
public abstract class Deck implements Filepaths {

    protected ArrayList<Card> DECK = new ArrayList<>();

    public Deck() {

    }

    // Public methods
    public ArrayList<Card> getDECK() {
        return this.DECK;
    }

    // Maybe move to player class?
    /*
    Method to check deck size. This decides how many cards can be drawn from the deck.
    Edge cases are 1 or 0 cards. Normally, 2 cards are supposed to be drawn from the
    deck every turn. Returns number of cards that can be drawn in the next turn.
    */
    public int checkDeckSizeBeforeDraw() {
        if (this.DECK.size() == 1) {
            return 1;
        } else if (this.DECK.size() == 0) {
            return 0;
        }
        return 2;
    }

    // Shuffle card deck.
    public void shuffleDeck(ArrayList<Card> deckToShuffle) {
        Collections.shuffle(deckToShuffle);
    }

    /*
    Discard method is used to move card from the top of the Disease deck to the discard pile.
    This feature is crucial for the gameplay, as this is how cities are chosen for infection.
    */
    public void discard(int cardIndex, Deck targetPile) {
        int TOP_CARD = 0;
        targetPile.getDECK().add(TOP_CARD, this.DECK.get(cardIndex));
        this.DECK.remove(cardIndex);
    }

    public void removeCard(int index) {
        this.DECK.remove(index);
    }


    // Protected methods

    /*
    Initialising cards from the CSV file containing cities. Most of the players cards and disease
    cards only contain city name and color. This method is used to wholly initialise the disease
    card deck since this deck only has cards with cities.
    */
    protected ArrayList<Card> initialiseCardsFromFile() throws FileNotFoundException {
        ArrayList<Card> cards = new ArrayList<>();
        Scanner scan = new Scanner(this.CITIES_PATH);
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(",");
            String cityName = line[0];
            String cityColor = line[1];
            Card card = new Card(cityName, cityColor);
            cards.add(card);
        }
        return cards;
    }
}
