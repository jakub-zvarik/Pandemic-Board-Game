package cities;

import cards.Cards;

public class City {
    private final String NAME;
    private final String COLOR;
    private City[] adjacentCities;
    private int infectionCubes;
    private boolean researchCenter;

    // Init, getters and setters
    public City(String name, String color) {
        this.NAME = name;
        this.COLOR = color;
    }

    public String getNAME() {
        return NAME;
    }

    public String getCOLOR() {
        return this.COLOR;
    }

    public City[] getAdjacentCities() {
        return this.adjacentCities;
    }

    public City getAdjacentCityIndex(int index) {
        return this.adjacentCities[index];
    }

    public void setAdjacentCities(City[] adjacentCities) {
        this.adjacentCities = adjacentCities;
    }

    public int getInfectionCubes() {
        return this.infectionCubes;
    }

    public void setInfectionCubes(int newInfectionCubes) {
        this.infectionCubes = newInfectionCubes;
    }

    public boolean getResearchCenter() {
        return this.researchCenter;
    }

    public void setResearchCenter(boolean newResearchCenter) {
        this.researchCenter = newResearchCenter;
    }

}