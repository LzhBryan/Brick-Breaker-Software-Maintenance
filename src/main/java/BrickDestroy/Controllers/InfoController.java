package BrickDestroy.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class InfoController {

    private Stage previousStage;

    public void receiveStage(Stage stage){
        previousStage = stage;
    }

    public void returnHomeMenu(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/HomeMenu-view.fxml"));
        previousStage.setScene(new Scene(loader.load()));
        previousStage.show();
    }
}
