package BrickDestroy.Models;

import BrickDestroy.Views.GameBoardView;
import javafx.stage.Stage;

/**
 * A model class that contains data for the pause menu.
 */
public class PauseMenuModel {

    private final GameLogic gameLogic;
    private final Stage previousStage;
    private final GameBoardView gameBoardView;

    /**
     * Constructor to receive parameters and assign it to the variables.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     * @param previousStage The stage before pause menu pops up.
     * @param gameBoardView The view class for gameBoard.
     */
    public PauseMenuModel(GameLogic gameLogic, Stage previousStage, GameBoardView gameBoardView){
        this.gameLogic = gameLogic;
        this.previousStage = previousStage;
        this.gameBoardView = gameBoardView;
    }

    /**
     * A getter method to obtain gameLogic object.
     * @return gameLogic object.
     */
    public GameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     * A getter method to obtain stage object.
     * @return stage object.
     */
    public Stage getPreviousStage() {
        return previousStage;
    }

    /**
     * A getter method to obtain gameBoardView object.
     * @return gameBoardView object.
     */
    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }
}
