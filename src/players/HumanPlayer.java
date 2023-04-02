package players;

import cards.Card;
import carddecks.Deck;

import cities.City;
import map.Map;

import support.EditDistance;
import java.util.Scanner;

/*
HumanPlayer is an extension of Player class, creating HumanPlayer objects. The difference between Player and
HumanPlayer classes are that the HumanPlayer contains actions that need human's interaction to complete.
These are actions involving traveling - player has to choose where to travel. Levenshtein's algorithm
is used here to allow player to make spelling mistakes in city name.
*/
public class HumanPlayer extends Player {

    // Constructor
    public HumanPlayer(String playerColor) {
        super(playerColor);
    }

    // Public methods

    // Basic player's actions

    // Player chooses and moves to city adjacent to their current position
    public boolean moveToAdjacentCity() {
        System.out.println("Which city do you want to travel to?");
        // List adjacent cities and their colors
        for (City city : this.currentCity.getAdjacentCities()) {
            System.out.println("City name: " + city.getNAME() + " and color: " + city.getCOLOR());
        }
        // Reads in user's input and transform it into lower case
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().toLowerCase();
        // Comparing user's input to city names
        for (City city : this.currentCity.getAdjacentCities()) {
            int editDistance = EditDistance.levenshteinDistance(userInput, city.getNAME().toLowerCase());
            if (editDistance <= 2 || city.getNAME().contains(userInput)) {
                // Setting new position
                setCurrentCity(city);
                System.out.println("You moved to " + this.currentCity.getNAME());
                return true;
            }
        }
        return false;
    }

    // Player can fly into different city if they have card with this city in their hand
    public boolean flyToCity(Map map, Deck DiscardPile) {
        System.out.println("Which city do you want to fly to?");
        // Gives player option to choose city according to cards they hold
        for (Card card : this.CARDS) {
            System.out.println(card.getNAME());
        }
        // Taking in user input
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().toLowerCase();
        // Comparing user input to cards they hold
        for (Card card : this.CARDS) {
            int editorialDistance = EditDistance.levenshteinDistance(userInput, card.getNAME());
            if (editorialDistance <= 2 || card.getNAME().contains(userInput)) {
                // Finding city with corresponding name in the world map and setting new position
                City city = map.getCITIES().get(card.getNAME());
                this.setCurrentCity(city);
                // Discarding card and removing it from player's hand
                discard(card, DiscardPile);
                System.out.println("You moved to " + this.currentCity.getNAME());
                return true;
            }
        }
        System.out.println("You do not have the needed city card for this flight.");
        return false;
    }
}