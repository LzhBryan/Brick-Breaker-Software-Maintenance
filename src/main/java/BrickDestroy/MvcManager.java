package BrickDestroy;

import BrickDestroy.Controllers.*;
import BrickDestroy.Models.DebugConsoleModel;
import BrickDestroy.Models.GameLogic;
import BrickDestroy.Models.PauseMenuModel;
import BrickDestroy.Views.GameBoardView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class that manage most of the model, controller and view (FXML) instantiation.
 */
public class MvcManager {

    private final GameLogic gameLogic;

    /**
     * MvcManager constructor
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     */
    public MvcManager(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    /**
     * A method to load the fxml and call the checkModel method.
     * @param path The path to the FXML view in under resources directory.
     * @param model The model to be instantiated.
     * @param gameBoardView The view class for gameBoard.
     * @param stage The current stage or previous stage depending on what kind of operation to be carried out.
     * @return the scene that has loaded the FXML file.
     */
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

    /**
     * A method to check the model received from parameters and call the respective methods.
     * @param model The model name to be instantiated.
     * @param loader The loader that loads the FXML file in the switchScenes method.
     * @param gameBoardView The view class for gameBoard that handles the user interface related methods.
     * @param stage The current stage or previous stage.
     */
    public void checkModel(String model, FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        if(model.equalsIgnoreCase("DebugConsole"))
            debugConsole(loader);

        else if (model.equalsIgnoreCase("PauseMenu"))
            pauseMenu(loader, gameBoardView, stage);

        else if(model.equalsIgnoreCase("Scoreboard"))
            scoreboard(loader);

        else if(model.equalsIgnoreCase("Info"))
            info(loader, stage);

        else if(model.equalsIgnoreCase("ScoreboardMenu"))
            scoreboardMenu(loader);
    }

    /**
     * A method to creating DebugConsoleModel object and pass in the gameLogic object, assign the controller and pass in the model into the controller methods.
     * @param loader the loader that loads the respective DebugConsole-view FXML file.
     */
    public void debugConsole(FXMLLoader loader){
        DebugConsoleModel debugModel = new DebugConsoleModel(gameLogic);
        DebugConsoleController debugConsoleController = loader.getController();
        debugConsoleController.initModel(debugModel);
    }

    /**
     * A method to creating PauseMenuModel object and pass in the gameLogic object, assign the controller and pass in the model into the controller methods.
     * @param loader the loader that loads teh respective the PauseMenu-view FXMl file.
     * @param gameBoardView The view class for gameBoard that handles the user interface related methods.
     * @param stage The game stage.
     */
    public void pauseMenu(FXMLLoader loader, GameBoardView gameBoardView, Stage stage){
        PauseMenuModel pauseMenuModel = new PauseMenuModel(gameLogic, stage, gameBoardView);
        PauseMenuController pauseMenuController = loader.getController();
        pauseMenuController.initModel(pauseMenuModel);
    }

    /**
     * A method to assign the Scoreboard controller and pass in the gameLogic object as well as calling the method to read score list from a text file.
     * @param loader the loader that loads teh respective the Scoreboard-view FXMl file.
     */
    public void scoreboard(FXMLLoader loader){
        ScoreboardController scoreboardController = loader.getController();
        scoreboardController.init(gameLogic);
        scoreboardController.readScoreList();
    }

    /**
     * A method to assign the infoController and pass in the stage parameter.
     * @param loader the loader that loads teh respective the Info-view FXMl file.
     * @param stage the HomeMenu stage.
     */
    public void info(FXMLLoader loader, Stage stage){
        InfoController infoController = loader.getController();
        infoController.initStage(stage);
    }

    /**
     * A method which is only called while user is in the home menu page to assign the controller and call the method that reads the score list from a txt file.
     * @param loader the loader that loads teh respective the Scoreboard-view FXMl file.
     */
    public void scoreboardMenu(FXMLLoader loader){
        ScoreboardController scoreboardController = loader.getController();
        scoreboardController.readScoreList();
    }
}
