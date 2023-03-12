package cities;

public class City {
    private final String NAME;
    private final String COLOR;
    private final String[] ADJACENT_CITIES;
    private int infectionCubes;
    private boolean researchCenter;

    // Init, getters and setters
    public City(String name, String color, String[] adjacentCities) {
        this.NAME = name;
        this.COLOR = color;
        this.ADJACENT_CITIES = adjacentCities;
    }

    public String getNAME() {
        return NAME;
    }

    public String setCOLOR() {
        return this.COLOR;
    }

    public String[] getADJACENT_CITIES() {
        return this.ADJACENT_CITIES;
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