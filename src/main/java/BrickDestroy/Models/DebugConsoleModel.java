package BrickDestroy.Models;

/**
 * A model that contains data for the debug console.
 */
public class DebugConsoleModel {

    private final GameLogic gameLogic;

    /**
     * Constructor to receive parameters and assign it to the variables.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     */
    public DebugConsoleModel(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    /**
     * A getter method to obtain gameLogic object.
     * @return gameLogic object.
     */
    public GameLogic getGameLogic() {
        return gameLogic;
    }

}
