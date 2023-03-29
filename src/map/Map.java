package map;

import cities.City;

import support.Filepaths;
import support.MyHashMap;

import java.io.FileNotFoundException;
import java.util.*;

/*
Map is used to construct map of the world (game board with cities). To construct
the map, all cities are loaded into hashmap from CSV file. Names of the cities
are used as the keys, City objects with properties from the CSV are values.
When the map si successfully initialised, next step performed in the Map class
is to interconnect the City objects. That is done by adding City objects from
the created hashmap into adjacent cities arrays, which are class variable for
every City object.
*/
public class Map implements Filepaths {

    private final int NUMBER_OF_CITIES = 48;
    private final MyHashMap<String, City> CITIES;

    // Constructor
    public Map() throws FileNotFoundException {
        // Initialise map
        this.CITIES = loadCities();
        // Add adjacent cities
        loadAdjacentCities(CITIES);
    }

    // Return HashMap object with all cities
    public MyHashMap<String, City> getCITIES() {
        return CITIES;
    }

    // Getters
    public int getNUMBER_OF_CITIES() {
        return NUMBER_OF_CITIES;
    }

    /*
    Loading cities into hashmap
    Reads through the CSV file (structure: city name, city color, adjacent cities)
    Takes only city name and city color to create City object for every city
    These objects are saved into hashmap, with keys being names of the cities
    */
    private MyHashMap<String, City> loadCities() throws FileNotFoundException {
        MyHashMap<String, City> cities = new MyHashMap<>(NUMBER_OF_CITIES);
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(",");
            // 1st and 2nd columns in the CSV are city name and city color
            String cityName = line[0];
            String cityColor = line[1];
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
    private void loadAdjacentCities(MyHashMap<String, City> loadedCities) throws FileNotFoundException {
        Scanner scan = new Scanner(this.PATH);
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(",");
            // Subtract first 2 columns (city name and city color) and only get adjacent cities
            int NUM_ADJACENT_CITIES = line.length - 2;
            City[] adjacentCities = new City[NUM_ADJACENT_CITIES];
            int indexInNewArray = 0;
            String cityName = line[0];
            for (int adjacentCity = 2; adjacentCity < line.length; adjacentCity++) {
                adjacentCities[indexInNewArray] = loadedCities.get(line[adjacentCity]);
                indexInNewArray += 1;
            }
            loadedCities.get(cityName).setAdjacentCities(adjacentCities);
        }
    }
}
