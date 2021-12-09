package BrickDestroy.Models;

/**
 * A model that contains data for the gameBoard view and controller.
 */
public class GameBoardModel {

    public static final double DEF_WIDTH = 600;
    public static final double DEF_HEIGHT = 450;

    private String userInput;
    private String gameMessage;
    private boolean gamePausing;
    private boolean gameRunning;
    private boolean gameLost;

    /**
     * Constructor to initialize the variables.
     */
    public GameBoardModel(){
        this.userInput = "";
        this.gameMessage = "";
        this.gameRunning = false;
        this.gamePausing = false;
        this.gameLost = false;
    }

    /**
     * A getter method to return userInput.
     * @return userInput.
     */
    public String getUserInput() {
        return userInput;
    }

    /**
     * A setter method that assigns the parameters to the variables.
     * @param userInput the key that user pressed.
     */
    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    /**
     * A setter method that assigns the parameters to the variables.
     * @param gameMessage the current message displaying the game information.
     */
    public void setGameMessage(String gameMessage) {
        this.gameMessage = gameMessage;
    }

    /**
     * A getter method to return the game pausing state
     * @return game pausing state.
     */
    public boolean isGamePausing() {
        return gamePausing;
    }

    /**
     * A setter method to assign the parameter to the variables.
     * @param gamePausing the game pausing state.
     */
    public void setGamePausing(boolean gamePausing) {
        this.gamePausing = gamePausing;
    }

    /**
     * A getter method to return game running state.
     * @return game running state.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * A setter method to assign the parameters to the game running state.
     * @param gameRunning the game running state.
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * A getter method to obtain the game lost state.
     * @return game lost state.
     */
    public boolean isGameLost() {
        return gameLost;
    }

    /**
     * A setter method to assign the parameters to the game lost state.
     * @param gameLost game lost state.
     */
    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }
}
