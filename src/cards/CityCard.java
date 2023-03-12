package cards;

public class CityCard extends Cards {

    private final String COLOR;

    public CityCard(String name, String color) {
        super(name);
        this.COLOR = color;
    }

    public String getCOLOR() {
        return this.COLOR;
    }

}
