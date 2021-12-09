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

public class HomeMenuController {

    private final GameLogic gameLogic = new GameLogic(new Rectangle(0,0,
            GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT),
            30,3,
            (double) 6/2, new Point2D(300,430));
    private final MvcManager mvcManager = new MvcManager(gameLogic);
    private final GameBoardView gameBoardView = new GameBoardView(gameLogic);

    public void switchToGameScreen(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameBoardModel gameBoardModel = new GameBoardModel();
        new GameBoardController(gameBoardModel, gameBoardView, gameLogic, mvcManager);
        stage.setScene(gameBoardView.getScene());
    }

    public void showInfoMenu(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/Info-view.fxml",
                "Info", gameBoardView, currentStage));
        currentStage.show();
    }

    public void showLeaderboard() {
        Stage newStage = new Stage();
        newStage.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/Scoreboard-view.fxml",
                "ScoreboardMenu", gameBoardView, newStage));
        newStage.setTitle("ScoreBoard");
        newStage.show();
    }

    public void exit() {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}
