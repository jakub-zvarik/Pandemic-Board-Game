package support;

import java.io.File;

/*
This is 'support' interface that stores any necessary filenames and their paths used in the game.
Interface was chosen for this so users can change this in one place.
*/
public interface Filepaths {

    // Cities CSV File
    String CITIES_FILENAME = "cities.csv";
    File CITIES_PATH = new File(new File(CITIES_FILENAME).getAbsolutePath());

}
