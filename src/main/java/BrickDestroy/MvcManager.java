package BrickDestroy;

import BrickDestroy.Controllers.DebugConsoleController;
import BrickDestroy.Controllers.InfoController;
import BrickDestroy.Controllers.PauseMenuController;
import BrickDestroy.Controllers.ScoreboardController;
import BrickDestroy.Models.DebugConsoleModel;
import BrickDestroy.Models.GameLogic;
import BrickDestroy.Models.PauseMenuModel;
import BrickDestroy.Views.GameBoardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MvcManager {

    private final GameLogic gameLogic;

    public MvcManager(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public Scene switchScenes(String path, String model, GameBoardView gameBoardView, Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            Scene scene = new Scene(loader.load());
            checkModel(model, loader, gameBoardView, stage);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkModel(String model, FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        if(model.equalsIgnoreCase("DebugConsole"))
            debugConsole(loader, gameBoardView, stage);

        else if (model.equalsIgnoreCase("PauseMenu"))
            pauseMenu(loader, gameBoardView, stage);

        else if(model.equalsIgnoreCase("Scoreboard"))
            scoreboard(loader, gameBoardView, stage);

        else if(model.equalsIgnoreCase("Info"))
            info(loader, gameBoardView, stage);

        else if(model.equalsIgnoreCase("ScoreboardMenu"))
            scoreboardMenu(loader, gameBoardView, stage);
    }

    public void debugConsole(FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        DebugConsoleModel debugModel = new DebugConsoleModel(gameLogic);
        DebugConsoleController debugConsoleController = loader.getController();
        debugConsoleController.initModel(debugModel);
    }

    public void pauseMenu(FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        PauseMenuModel pauseMenuModel = new PauseMenuModel(gameLogic, stage, gameBoardView);
        PauseMenuController pauseMenuController = loader.getController();
        pauseMenuController.initModel(pauseMenuModel);
    }

    public void scoreboard(FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        ScoreboardController scoreboardController = loader.getController();
        scoreboardController.init(gameLogic);
        scoreboardController.readScoreList();
    }

    public void info(FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        InfoController infoController = loader.getController();
        infoController.receiveStage(stage);
    }

    public void scoreboardMenu(FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        ScoreboardController scoreboardController = loader.getController();
        scoreboardController.readScoreList();
    }
}
