package BrickDestroy.Models;

public class GameBoardModel {

    public static final double DEF_WIDTH = 600;
    public static final double DEF_HEIGHT = 450;

    private String userInput;
    private String gameMessage;
    private boolean gamePausing;
    private boolean gameRunning;

    public GameBoardModel(){
        this.userInput = "";
        this.gameMessage = "";
        this.gameRunning = false;
        this.gamePausing = false;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public void setGameMessage(String gameMessage) {
        this.gameMessage = gameMessage;
    }

    public boolean isGamePausing() {
        return gamePausing;
    }

    public void setGamePausing(boolean gamePausing) {
        this.gamePausing = gamePausing;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }
}