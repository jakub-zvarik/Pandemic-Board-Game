package carddecks;

import cards.Card;
import cards.EpidemicCard;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayersDeck extends Deck {

    private final int EPIDEMIC_CARDS;

    public PlayersDeck(int EPIDEMIC_CARDS) throws FileNotFoundException {
        this.EPIDEMIC_CARDS = EPIDEMIC_CARDS;
        shuffleDeck(this.deck = initialisePlayersDeck());
    }

    /*
    NOTE - Redo this - 4 piles, add 4 epidemics, shuffle, combine the piles
    Initialise player deck - take all the cities and colors from the csv file and add them into
    ArrayList. Also add 6 epidemic cards into this deck
    */
    private ArrayList<Card> initialisePlayersDeck() throws FileNotFoundException {
        ArrayList<Card> cards = initialiseCardsFromFile();
        for (int card = 0; card < this.EPIDEMIC_CARDS; card ++) {
            EpidemicCard epidemicCard = new EpidemicCard("EPIDEMIC", "Poison Green");
            cards.add(epidemicCard);
        }
        return cards;
    }
}