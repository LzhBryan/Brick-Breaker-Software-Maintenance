package BrickDestroy.Controllers;

import BrickDestroy.DebugConsole;
import BrickDestroy.GameBoard;
import BrickDestroy.GameLogicControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DebugConsoleController implements Initializable {

    @FXML
    private Slider ballXSpeed;
    @FXML
    private Slider ballYSpeed;

    private GameLogicControl gameLogic;
    private Stage previousStage;
    private FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ballXSpeed.valueProperty().addListener((observableValue, number, t1) ->
                gameLogic.setBallXSpeed((int) ballXSpeed.getValue()));

        ballYSpeed.valueProperty().addListener((observableValue, number, t1) ->
                gameLogic.setBallYSpeed((int) ballYSpeed.getValue()));
    }

    public void initModel(GameLogicControl gameLogic, Stage previousStage, FXMLLoader loader){
        this.gameLogic = gameLogic;
        this.previousStage = previousStage;
        this.loader = loader;
    }

    public void skipLevels(MouseEvent event) {
        if (gameLogic.hasLevel())
            gameLogic.nextLevel();
    }

    public void resetBalls(MouseEvent event) {
        gameLogic.resetBallCount();
    }
}
