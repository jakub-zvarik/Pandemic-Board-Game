package carddecks;

import java.io.FileNotFoundException;

/*
Disease deck is a Deck object composed of Cards objects with city names and colors.
Cards are initialised from the CSV file containing all cities and their colors.
*/
public class DiseaseDeck extends Deck {

    public DiseaseDeck() throws FileNotFoundException {
        shuffleDeck(this.DECK = initialiseCardsFromFile());
    }
}
