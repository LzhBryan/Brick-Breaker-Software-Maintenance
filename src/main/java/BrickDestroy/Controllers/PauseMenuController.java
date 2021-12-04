package BrickDestroy.Controllers;

import BrickDestroy.GameBoard;
import BrickDestroy.GameLogicControl;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PauseMenuController {

    @FXML
    public Button restartButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button continueButton;

    private Stage stage;
    private GameLogicControl gameLogic;
    private GameBoard gameBoard;
    private GraphicsContext gc;

    public void initModel(Stage stage, GameLogicControl gameLogic, GameBoard gameBoard, GraphicsContext gc){
        this.stage = stage;
        this.gameLogic = gameLogic;
        this.gameBoard = gameBoard;
        this.gc = gc;
    }

    @FXML
    protected void resume(MouseEvent event){
        closeStage((Stage) exitButton.getScene().getWindow());
    }

    @FXML
    public void restart(MouseEvent event){
        gameLogic.wallReset();
        gameLogic.ballReset();
        gameBoard.updateMessage("Restarting Game...");
        closeStage((Stage) exitButton.getScene().getWindow());
    }

    @FXML
    public void exit(MouseEvent event){
        stage.close();
    }

    @FXML
    public void closeScreen(KeyEvent event){
        if(event.getCode() == KeyCode.ESCAPE)
            closeStage((Stage)((Node) event.getSource()).getScene().getWindow());
    }

    public void closeStage(Stage stage){
        stage.close();
        this.stage.getScene().getRoot().setEffect(null);
    }
}
