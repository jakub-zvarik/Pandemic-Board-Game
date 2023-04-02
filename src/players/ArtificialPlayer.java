package players;

/*
!Subject of future work!

This class will contain evaluation method's and action for player agent. This should be able
to evaluate current game, take into consideration cards that already has been discarded (this is
why there is discard deck for player cards already implemented in the game) and make decisions
based on these evaluations. It should be able to work with human player as part of team.
*/
public class ArtificialPlayer extends Player {

    // Constructor
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

}