package players;

import cities.City;
import cards.Cards;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Players {
    private final String PLAYER_COLOR;
    private final String SPECIALISATION;
    private City currentCity;
    private int numberOfCards;
    private final int MAX_CARDS = 7;
    private ArrayList<Cards> cards;

    // Init, getters and setters
    public Players(String playerColor, String specialisation) {
        this.PLAYER_COLOR = playerColor;
        this.SPECIALISATION = specialisation;
    }

    public String getPLAYER_ID() {
        return this.PLAYER_COLOR;
    }

    public String getSPECIALISATION() {
        return this.SPECIALISATION;
    }

    public City getCurrentCity() {
        return this.currentCity;
    }

    public void setCurrentCity(City newPosition) {
        this.currentCity = newPosition;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int newNumberOfCards) {
        this.numberOfCards = newNumberOfCards;
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCard(Cards newCard) {
        if (this.numberOfCards <= this.MAX_CARDS) {
            this.cards.add(newCard);
            this.numberOfCards += 1;
        } else {
            System.out.println("You have maximum number of cards on your hand!");
        }
    }

    // Basic player's actions
    public boolean moveToAdjacentCity() {
        Scanner scanner = new Scanner(System.in);
        int cityNumber = 0;
        for (City city : this.currentCity.getAdjacentCities()) {
            cityNumber += 1;
            System.out.println("City number " + cityNumber + " - city name: " + city.getNAME() + " and color: "
                    + city.getCOLOR());
        }
        System.out.println("Where do you want to go? Type in the number of the city or 0 to go back.");
        String userInput = scanner.next();
        if (Integer.parseInt(userInput) <= this.currentCity.getAdjacentCities().length && Integer.parseInt(userInput) > 0) {
            setCurrentCity(this.currentCity.getAdjacentCityIndex(Integer.parseInt(userInput) - 1));
            System.out.println("You moved to " + this.currentCity.getNAME());
            return true;
        } else if (Integer.parseInt(userInput) == 0) {
            System.out.println("Back to possible moves...");
            return false;
        } else {
            System.out.println("Wrong input.");
            return false;
        }
    }

    public void flightToCity(City cityName) {
        for (Cards card : this.cards) {
            if(card.getNAME().equals(cityName.getNAME())) {
                this.setCurrentCity(cityName);
            } else {
                System.out.println("You do not have the needed city card for this flight.");
            }
        }
    }

    public void charterFlightToAnyCity(City cityName) {

    }
}

