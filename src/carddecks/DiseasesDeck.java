package carddecks;

import java.io.FileNotFoundException;

public class DiseasesDeck extends Deck {

    public DiseasesDeck() throws FileNotFoundException {
        shuffleDeck(this.deck = initialiseCardsFromFile());
    }
}
