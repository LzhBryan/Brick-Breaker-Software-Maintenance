package BrickDestroy.Controllers;

import BrickDestroy.Models.DebugConsoleModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class DebugConsoleController implements Initializable {

    @FXML
    private Slider ballXSpeed;
    @FXML
    private Slider ballYSpeed;

    private DebugConsoleModel debugModel;

    public void initModel(DebugConsoleModel debugModel){
        this.debugModel = debugModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ballXSpeed.valueProperty().addListener(e ->
                debugModel.getGameLogic().setBallXSpeed((int) ballXSpeed.getValue()));

        ballYSpeed.valueProperty().addListener(e ->
                debugModel.getGameLogic().setBallYSpeed((int) ballYSpeed.getValue()));
    }

    public void skipLevels(MouseEvent event) {
        if (debugModel.getGameLogic().hasLevel())
            debugModel.getGameLogic().nextLevel();
    }

    public void resetBalls(MouseEvent event) {
        debugModel.getGameLogic().resetBallCount();
    }
}
