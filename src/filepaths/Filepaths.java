package filepaths;

import java.io.File;

public interface Filepaths {

    final String FILENAME = "cities.csv";
    final File PATH = new File(new File(FILENAME).getAbsolutePath());

}
