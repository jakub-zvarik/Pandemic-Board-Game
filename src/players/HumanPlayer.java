package players;

import carddecks.Deck;
import cards.Card;
import cities.City;
import map.Map;
import support.EditDistance;

import java.util.Scanner;

public class HumanPlayer extends Player {

    public HumanPlayer(String playerColor) {
        super(playerColor);
    }

    // Basic player's actions
    public boolean moveToAdjacentCity() {
        System.out.println("Which city do you want to travel to?");
        for (City city : this.currentCity.getAdjacentCities()) {
            System.out.println("City name: " + city.getNAME() + " and color: " + city.getCOLOR());
        }
        // Maybe take out this, within player class make just checks for move legality
        // and this chat make as a part of chatbot class or gameplay? idk, think about it
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().toLowerCase();
        for (City city : this.currentCity.getAdjacentCities()) {
            int editDistance = EditDistance.levenshteinDistance(userInput, city.getNAME().toLowerCase());
            if (editDistance <= 3 || city.getNAME().contains(userInput)) {
                setCurrentCity(city);
                System.out.println("You moved to " + this.currentCity.getNAME());
                return true;
            }
        }
        return false;
    }

    /*
    !!!!!!! ADD PLAYER THROWING CARD IN DISCARD PILE
    */
    public boolean flyToCity(Map map, Deck DiscardPile) {
        System.out.println("Which city do you want to fly to?");
        for (Card card : this.CARDS) {
            System.out.println(card.getNAME());
        }
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().toLowerCase();
        for (Card card : this.CARDS) {
            int editorialDistance = EditDistance.levenshteinDistance(userInput, card.getNAME());
            if (editorialDistance <= 3 || card.getNAME().contains(userInput)) {
                City city = map.getCITIES().get(card.getNAME());
                this.setCurrentCity(city);
                discard(card, DiscardPile);
                System.out.println("You moved to " + this.currentCity.getNAME());
                return true;
            }
        }
        System.out.println("You do not have the needed city card for this flight.");
        return false;
    }

    public boolean cureCity() {
        if (this.currentCity.getInfectionCubes() > 0) {
            this.currentCity.decreaseInfectionCubes();
            return true;
        }
        System.out.println("There is no disease cube in this city.");
        return false;
    }

    public boolean findCure(Deck discardDeck) {
        int cardCounter = 0;
        String color = this.currentCity.getCOLOR();
        for (Card card : this.CARDS) {
            if (card.getCOLOR().equals(color)) {
                cardCounter += 1;
            }
            if (cardCounter == 5) {
                for (Card cardToDiscard : this.CARDS) {
                    if (cardToDiscard.getCOLOR().equals(color)) {
                        discard(cardToDiscard, discardDeck);
                    }
                    return true;
                }
            }
        }
        System.out.println("You do not have enough cards to cure " + color + " disease.");
        return false;
    }
}