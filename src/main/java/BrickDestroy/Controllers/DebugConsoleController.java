package BrickDestroy.Controllers;

import BrickDestroy.Models.DebugConsoleModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A controller class that is responsible for handling any user interaction/input with the debug console view.
 */
public class DebugConsoleController implements Initializable {

    @FXML
    private Slider ballXSpeed;
    @FXML
    private Slider ballYSpeed;

    private DebugConsoleModel debugModel;

    /**
     * A method to obtain the DebugConsoleModel object.
     * @param debugModel A model object which contains data about the debug console.
     */
    public void initModel(DebugConsoleModel debugModel){
        this.debugModel = debugModel;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ballXSpeed.valueProperty().addListener(e ->
                debugModel.getGameLogic().setBallXSpeed((int) ballXSpeed.getValue()));

        ballYSpeed.valueProperty().addListener(e ->
                debugModel.getGameLogic().setBallYSpeed((int) ballYSpeed.getValue()));
    }

    /**
     * A method to skip the game levels when user click on the "Skip level" button.
     */
    public void skipLevels() {
        if (debugModel.getGameLogic().hasLevel())
            debugModel.getGameLogic().nextLevel();
    }

    /**
     * A method to reset the ball count when user clicks on the "Reset Ball" button.
     */
    public void resetBallCount() {
        debugModel.getGameLogic().resetBallCount();
    }
}
