package BrickDestroy.Models;

import BrickDestroy.GameBoard;
import BrickDestroy.GameLogicControl;
import javafx.stage.Stage;

public class PauseMenuModel {

    private final GameLogicControl gameLogic;
    private final Stage previousStage;
    private final GameBoard gameBoard;

    public PauseMenuModel(GameLogicControl gameLogic, Stage previousStage, GameBoard gameBoard){
        this.gameLogic = gameLogic;
        this.previousStage = previousStage;
        this.gameBoard = gameBoard;
    }

    public GameLogicControl getGameLogic() {
        return gameLogic;
    }

    public Stage getPreviousStage() {
        return previousStage;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
