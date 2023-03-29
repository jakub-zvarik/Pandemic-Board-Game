package cards;

/*
Abstract class laying down blue-print for Card object. Every Card has name (name of the city)
and color (color of the city).
*/
public abstract class Card {
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
