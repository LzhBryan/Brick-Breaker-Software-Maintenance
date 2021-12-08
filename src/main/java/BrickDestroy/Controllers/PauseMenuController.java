package BrickDestroy.Controllers;

import BrickDestroy.Models.PauseMenuModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PauseMenuController {

    @FXML
    public Button restartButton;
    @FXML
    private Button continueButton;

    private PauseMenuModel pauseMenuModel;

    public void initModel(PauseMenuModel pauseMenuModel){
        this.pauseMenuModel = pauseMenuModel;
    }

    @FXML
    protected void resume(MouseEvent event){
        closeStage((Stage) continueButton.getScene().getWindow());
    }

    @FXML
    public void restart(MouseEvent event){
        pauseMenuModel.getGameLogic().wallReset();
        pauseMenuModel.getGameLogic().ballReset();
        pauseMenuModel.getGameBoardModel().updateMessage("Restarting Game...");
        closeStage((Stage) restartButton.getScene().getWindow());
    }

    @FXML
    public void exit(MouseEvent event){
        pauseMenuModel.getPreviousStage().close();
    }

    @FXML
    public void closeScreen(KeyEvent event){
        if(event.getCode() == KeyCode.ESCAPE)
            closeStage((Stage)((Node) event.getSource()).getScene().getWindow());
    }

    public void closeStage(Stage stage){
        stage.close();
        pauseMenuModel.getPreviousStage().getScene().getRoot().setEffect(null);
    }
}
