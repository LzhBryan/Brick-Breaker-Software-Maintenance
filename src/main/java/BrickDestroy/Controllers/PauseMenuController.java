package BrickDestroy.Controllers;

import BrickDestroy.Models.PauseMenuModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * A controller class that is responsible for handling any user interaction/input with the pause menu view.
 */
public class PauseMenuController {

    @FXML
    public Button restartButton;
    @FXML
    private Button continueButton;

    private PauseMenuModel pauseMenuModel;

    /**
     * A method to receive the pause menu model.
     * @param pauseMenuModel the model which contains data for the pause menu.
     */
    public void initModel(PauseMenuModel pauseMenuModel){
        this.pauseMenuModel = pauseMenuModel;
    }

    /**
     * A method to redirect the user back to the game screen.
     */
    @FXML
    protected void resume(){
        closeStage((Stage) continueButton.getScene().getWindow());
    }

    /**
     * A method to reset the game, including the ball, wall and game message. And redirect user back to the game screen.
     */
    @FXML
    public void restart(){
        pauseMenuModel.getGameLogic().wallReset();
        pauseMenuModel.getGameLogic().ballReset();
        pauseMenuModel.getGameBoardModel().updateMessage("Restarting Game...");
        closeStage((Stage) restartButton.getScene().getWindow());
    }

    /**
     * Exit the entire program.
     */
    @FXML
    public void exit(){
        pauseMenuModel.getPreviousStage().close();
    }

    /**
     * A method to close the popup whenever user pressed the escape key.
     * @param event An event which indicates that a keystroke occurred in a Node.
     */
    @FXML
    public void closeScreen(KeyEvent event){
        if(event.getCode() == KeyCode.ESCAPE)
            closeStage((Stage)((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * A method to close the pause menu and remove the gaussian blur of the game screen.
     * @param stage The home menu stage.
     */
    public void closeStage(Stage stage){
        stage.close();
        pauseMenuModel.getPreviousStage().getScene().getRoot().setEffect(null);
    }
}
