package players;

import cities.City;
import cards.Cards;

import java.util.ArrayList;

abstract class Players {
    private final int PLAYER_ID;
    private final String SPECIALISATION;
    private City currentCity;
    private int numberOfCards;
    private final int MAX_CARDS = 7;
    private ArrayList<Cards> cards;

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
    public void moveToAdjacentCity(City cityName) {

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

