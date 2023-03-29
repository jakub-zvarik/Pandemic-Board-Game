package players;

import cities.City;

public class ArtificialPlayer extends Player {


    public ArtificialPlayer(String playerColor) {
        super(playerColor);
    }

    // Public methods
    public void turn() {

    }

    // Private methods
    private int evaluateGame() {
        return 0;
    }

    private int chooseBestAction() {
        return 0;
    }

    private boolean moveToAdjacent(City city) {
        setCurrentCity(city);
        System.out.println(this.PLAYER_COLOR + " moved to " + this.currentCity.getNAME());
        return true;
    }
}