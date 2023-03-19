package cards;

public abstract class Card {

    private final String NAME;
    private final String COLOR;

    public Card(String name, String color) {
        this.NAME = name;
        this.COLOR = color;
    }

    public String getNAME() {
        return this.NAME;
    }

    public String getCOLOR() {
        return COLOR;
    }
}
