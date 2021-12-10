package BrickDestroy.Controllers;

import BrickDestroy.Models.GameBoardModel;
import BrickDestroy.Models.GameLogic;
import BrickDestroy.MvcManager;
import BrickDestroy.Views.GameBoardView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * A controller class that is responsible for handling any user interaction/input with the home menu view.
 */
public class HomeMenuController {

    private final GameLogic gameLogic = new GameLogic(new Rectangle(0,0,
            GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT),
            30,3,
            (double) 6/2, new Point2D(300,430));
    private final MvcManager mvcManager = new MvcManager(gameLogic);
    private final GameBoardView gameBoardView = new GameBoardView(gameLogic);

    /**
     * A method to change the scene to the game screen when user clicks on the play button.
     * @param event capture the top most node whenever a mouse event occurs.
     */
    public void switchToGameScreen(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameBoardModel gameBoardModel = new GameBoardModel();
        new GameBoardController(gameBoardModel, gameBoardView, gameLogic, mvcManager);
        stage.setScene(gameBoardView.getScene());
    }

    /**
     * A method to display the info screen when user clicks on the "Info" button.
     * @param event capture the top most node whenever a mouse event occurs.
     */
    public void showInfoMenu(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/Info-view.fxml",
                "Info", gameBoardView, currentStage));
        currentStage.show();
    }

    /**
     * A method to load and display the scoreboard FXML when the user clicks on the "Leaderboard" button.
     */
    public void showLeaderboard() {
        Stage newStage = new Stage();
        newStage.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/Scoreboard-view.fxml",
                "ScoreboardMenu", gameBoardView, newStage));
        newStage.setTitle("ScoreBoard");
        newStage.show();
    }

    /**
     * A method to close the program when user clicks on the "Exit" button.
     */
    public void exit() {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}
