package map;

import cards.Card;
import cities.City;
import filepaths.Filepaths;

import java.io.FileNotFoundException;
import java.util.*;

public class Map implements Filepaths {

    private final HashMap<String, City> CITIES;

    // Constructor
    public Map() throws FileNotFoundException {
        // Initialise map
        this.CITIES = loadCities();
        loadAdjacentCities(CITIES);
    }

    public HashMap<String, City> getCITIES() {
        return CITIES;
    }

    /*
    This method is used to move cards around from deck to deck.
    */
    private void moveCards(ArrayList<Card> sourceDeck, ArrayList<Card> targetDeck, int numberOfCards) {
        for (int card = 0; card < numberOfCards; card++) {
            targetDeck.add(sourceDeck.get(card));
        }
    }

    /*
    Loading cities into hashmap
    Reads through the CSV file (structure: city name, city color, adjacent cities)
    Takes only city name and city color to create City object for every city
    These objects are saved into hashmap, with keys being names of the cities
    */
    private HashMap<String, City> loadCities() throws FileNotFoundException {
        HashMap<String, City> cities = new HashMap<>();
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] reading = scan.nextLine().split(",");
            String cityName = reading[0];
            String cityColor = reading[1];
            City cityObject = new City(cityName, cityColor);
            cities.put(cityName, cityObject);
        }
        return cities;
    }

    /*
    Loading adjacent cities from hashmap and csv file
    CSV structure - city name, city color, adjacent cities
    Takes hashmap of cities as parameter. Takes name of the adjacent city (key), looks it up
    in the hashmap and adds City object associated with the key into array. Once all adjacent
    cities for one particular city are added, the array sent into City object setter of adjacent
    cities. Every city is associated with another City objects and these object are created only
    once.
    */
    private void loadAdjacentCities(HashMap<String, City> loadedCities) throws FileNotFoundException {
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] reading = scan.nextLine().split(",");
            // Subtract first 2 columns (city name and city color) and only get adjacent cities
            int NUM_ADJACENT_CITIES = reading.length - 2;
            City[] adjacentCities = new City[NUM_ADJACENT_CITIES];
            int indexInNewArray = 0;
            String cityName = reading[0];
            for (int adjacentCity = 2; adjacentCity < reading.length; adjacentCity++) {
                adjacentCities[indexInNewArray] = loadedCities.get(reading[adjacentCity]);
                indexInNewArray += 1;
            }
            loadedCities.get(cityName).setAdjacentCities(adjacentCities);
        }
    }
}
