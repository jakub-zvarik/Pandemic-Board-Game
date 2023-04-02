package cities;

/*
City object represents city in the game. Every city has multiple properties represented by
class variables. It uses array of City objects to represent adjacent cities. Infection cubes
are counted to introduce outbreak if needed. curedDisease keeps track of whether the disease
of city's color was cured. Object City is used to construct map of the world, which is done in
the Map class.
*/
public class City {
    private final String NAME;
    private final String COLOR;
    private City[] adjacentCities;
    private int infectionCubes = 0;
    private boolean cureFound = false;

    // Constructor
    public City(String name, String color) {
        this.NAME = name;
        this.COLOR = color;
    }

    // Public methods

    // Getters
    public String getNAME() {
        return NAME;
    }

    public String getCOLOR() {
        return this.COLOR;
    }

    public City[] getAdjacentCities() {
        return this.adjacentCities;
    }

    public int getInfectionCubes() {
        return this.infectionCubes;
    }

    public boolean getCureFound() {
        return cureFound;
    }

    // Setters
    public void setAdjacentCities(City[] adjacentCities) {
        this.adjacentCities = adjacentCities;
    }
    public void setInfectionCubes(int numberOfCubes) {
        this.infectionCubes = numberOfCubes;
    }

    public void increaseInfectionCubes() {
        this.infectionCubes += 1;
    }

    public void decreaseInfectionCubes() {
        this.infectionCubes -= 1;
    }

    public void setCuredDisease() {
        this.cureFound = true;
    }

}