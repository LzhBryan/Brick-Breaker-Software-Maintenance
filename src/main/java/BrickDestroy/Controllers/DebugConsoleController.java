package BrickDestroy.Controllers;

import BrickDestroy.DebugConsole;
import BrickDestroy.GameLogicControl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DebugConsoleController implements Initializable {

    @FXML
    private Slider ballXspeed;
    @FXML
    private Slider ballYspeed;

    private GameLogicControl gameLogic;
    DebugConsole debugConsole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ballXspeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                gameLogic.setBallXSpeed((int) ballXspeed.getValue());
            }
        });

        ballYspeed.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                gameLogic.setBallYSpeed((int) ballYspeed.getValue());
            }
        });
    }

    public void initModel(DebugConsole debugConsole, GameLogicControl gameLogic){
        this.debugConsole = debugConsole;
        this.gameLogic = gameLogic;
    }

    public void skipLevels(MouseEvent event) throws IOException{
        if (gameLogic.hasLevel())
            gameLogic.nextLevel();
    }

    public void resetBalls(MouseEvent event) throws IOException{
        gameLogic.resetBallCount();
    }
}
