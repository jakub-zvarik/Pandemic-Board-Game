package cards;

/*
Card object is used to create different cards in the game. Cards used in this version of the game
have names and colors, usually taking these properties from the cities' names and colors. Only
exceptions are epidemic cards, which have "custom" name and color. This class is used to initialise
Card objects in the Deck objects.
*/
public class Card {
    // Class variables
    private final String NAME;
    private final String COLOR;

    // Constructor
    public Card(String name, String color) {
        this.NAME = name;
        this.COLOR = color;
    }

    // Public methods
    public String getNAME() {
        return this.NAME;
    }

    public String getCOLOR() {
        return COLOR;
    }
}
