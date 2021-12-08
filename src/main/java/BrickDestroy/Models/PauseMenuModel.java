package BrickDestroy.Models;

import BrickDestroy.Views.GameBoardView;
import javafx.stage.Stage;

public class PauseMenuModel {

    private final GameLogic gameLogic;
    private final Stage previousStage;
    private final GameBoardView gameBoardView;

    public PauseMenuModel(GameLogic gameLogic, Stage previousStage, GameBoardView gameBoardView){
        this.gameLogic = gameLogic;
        this.previousStage = previousStage;
        this.gameBoardView = gameBoardView;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public Stage getPreviousStage() {
        return previousStage;
    }

    public GameBoardView getGameBoardModel() {
        return gameBoardView;
    }
}
