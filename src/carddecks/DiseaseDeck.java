package carddecks;

import java.io.FileNotFoundException;

/*
Disease deck is a Deck object composed of Cards objects with city names and colors.
Cards are initialised from the CSV file containing all cities and their colors.
*/
public class DiseaseDeck extends Deck {

    public DiseaseDeck() throws FileNotFoundException {
        shuffleDeck(this.deck = initialiseCardsFromFile());
    }

    /*
    Discard method is used to move card from the top of the Disease deck to the discard pile.
    This feature is crucial for the gameplay, as this is how cities are chosen for infection.
    */
    public void discard(DiscardPile discardPile) {
        discardPile.getDeck().add(0, this.deck.get(0));
        this.deck.remove(0);
    }

}
