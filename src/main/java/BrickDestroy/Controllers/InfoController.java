package BrickDestroy.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * A controller class that is responsible for handling any user interaction/input with the info view.
 */
public class InfoController {

    private Stage previousStage;

    /**
     * A method to assign the parameters to the variables.
     * @param stage the previous home menu stage.
     */
    public void initStage(Stage stage){
        previousStage = stage;
    }

    /**
     * A method to direct user back to the home menu screen.
     * @throws IOException throws exception when loader could not load the FXML file.
     */
    public void returnHomeMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/HomeMenu-view.fxml"));
        previousStage.setScene(new Scene(loader.load()));
        previousStage.show();
    }
}
