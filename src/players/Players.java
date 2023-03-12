package players;

import java.util.Arrays;
import java.util.List;

abstract class Players {
    private final int PLAYER_ID;
    private final String SPECIALISATION;
    private String currentPosition;
    private int numberOfCards;
    private List<String> cards;

    // Init, getters and setters
    public Players(int playerID, String specialisation) {
        this.PLAYER_ID = playerID;
        this.SPECIALISATION = specialisation;
    }

    public int getPLAYER_ID() {
        return this.PLAYER_ID;
    }

    public String getSPECIALISATION() {
        return this.SPECIALISATION;
    }

    public String getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(String newPosition) {
        this.currentPosition = newPosition;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int newNumberOfCards) {
        this.numberOfCards = newNumberOfCards;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> newCards) {
        this.cards = newCards;
    }

    // Basic player's actions
    public void moveToAdjacentCity(String[] adjacentCities, String cityName) {
        if (Arrays.asList(adjacentCities).contains(cityName)) {
            this.setCurrentPosition(cityName);
        } else {
            System.out.println("City you want to go to is not adjacent to your current city!");
        }
    }

    public void flightToCity(String cityName) {
        if (this.cards.contains(cityName)) {
            this.setCurrentPosition(cityName);
            this.cards.remove(cityName);
        } else {
            System.out.println("You do not have a card with the city you want to travel to!");
        }
    }

    public void charterFlightToAnyCity(String cityName) {
        if (this.cards.contains(this.currentPosition)) {
            this.setCurrentPosition(cityName);
            this.cards.remove(cityName);
        } else {
            System.out.println("To use charter flight, you need to hold a card with your current city!");
        }
    }



}
