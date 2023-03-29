package carddecks;
// Card packages
import cards.Card;
import cards.EpidemicCard;
// Others
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
Player's deck is a Deck object with changed a changed manner in which the deck is created.
In player's deck there are also epidemic cards, which are not part of the CSV file with
cities and their properties.
*/
public class PlayersDeck extends Deck {


    public PlayersDeck() throws FileNotFoundException {
        this.deck = initialisePlayersDeck();
    }

    /*
    Player's deck is initialised by creating ArrayList (or deck) of Card objects. These
    cards are then shuffled and divided into 4 piles. Epidemic card is added into each
    pile. Each pile is then shuffled. In the last step, the piles are add together into
    one deck, composing the final Player's deck.
    */
    private ArrayList<Card> initialisePlayersDeck() throws FileNotFoundException {
        // Initialise city cards
        ArrayList<Card> cityCards = initialiseCardsFromFile();
        shuffleDeck(cityCards);
        // Divide city cards into 4 piles
        int WHOLE_DECK = cityCards.size();
        int QUARTER = WHOLE_DECK / 4;
        int HALF = QUARTER * 2;
        int THREE_QUARTERS = QUARTER * 3;
        ArrayList<Card> pile1 = new ArrayList<>(cityCards.subList(0, QUARTER));
        ArrayList<Card> pile2 = new ArrayList<>(cityCards.subList(QUARTER, HALF));
        ArrayList<Card> pile3 = new ArrayList<>(cityCards.subList(HALF, THREE_QUARTERS));
        ArrayList<Card> pile4 = new ArrayList<>(cityCards.subList(THREE_QUARTERS, WHOLE_DECK));
        // Add Epidemic card to every pile
        pile1.add(new EpidemicCard("EPIDEMIC", "Poison Green"));
        pile2.add(new EpidemicCard("EPIDEMIC", "Poison Green"));
        pile3.add(new EpidemicCard("EPIDEMIC", "Poison Green"));
        pile4.add(new EpidemicCard("EPIDEMIC", "Poison Green"));
        // Shuffle decks
        shuffleDeck(pile1);
        shuffleDeck(pile2);
        shuffleDeck(pile3);
        shuffleDeck(pile4);
        // Add all piles into one
        ArrayList<Card> PLAYERS_DECK = new ArrayList<>();
        PLAYERS_DECK.addAll(pile1);
        PLAYERS_DECK.addAll(pile2);
        PLAYERS_DECK.addAll(pile3);
        PLAYERS_DECK.addAll(pile4);
        // Returns the final player's deck
        return PLAYERS_DECK;
    }
}