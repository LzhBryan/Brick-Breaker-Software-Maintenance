package BrickDestroy.Controllers;

import BrickDestroy.GameLogic;
import BrickDestroy.Models.GameBoardModel;
import BrickDestroy.Views.GameBoardView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomeMenuController {

    @FXML
    private Button InfoButton;
    @FXML
    private Button startMenuButton;
    @FXML
    private Button exitMenuButton;

    public void switchToGameScreen(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameBoardModel gameBoardModel = new GameBoardModel(stage);
        GameLogic gameLogic;
        gameLogic = new GameLogic(new Rectangle(0,0,
                GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT),
                30,3,
                (double) 6/2, new Point2D(300,430));
        GameBoardView gameBoardView = new GameBoardView(gameLogic);
        GameBoardController gameBoardController = new GameBoardController(gameBoardModel, gameBoardView, gameLogic);
        stage.setScene(gameBoardView.getScene());
    }

    public void showInfoMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/Info-view.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(loader.load()));
        currentStage.show();
        InfoController infoController = loader.getController();
        infoController.receiveStage(currentStage);
    }

    public void showLeaderboard(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/Scoreboard-view.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(loader.load()));
        newStage.setTitle("ScoreBoard");
        newStage.show();
        ScoreboardController scoreboardController = loader.getController();
        scoreboardController.readScorelist();
    }

    public void exit(MouseEvent event) {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}
