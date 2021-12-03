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
    private Button exitButton;

    @FXML
    private Button continueButton;

    private Stage stage;
    private GameLogicControl gameLogic;
    private GameBoard gameBoard;
    private Stage pauseMenuStage;
    private GraphicsContext gc;

    public void initModel(Stage stage, GameLogicControl gameLogic, GameBoard gameBoard, GraphicsContext gc){
        this.stage = stage;
        this.gameLogic = gameLogic;
        this.gameBoard = gameBoard;
        this.gc = gc;
    }

    @FXML
    protected void resume(MouseEvent event){
        pauseMenuStage = (Stage) exitButton.getScene().getWindow();
        pauseMenuStage.close();
        stage.getScene().getRoot().setEffect(null);
    }

    @FXML
    public void restart(MouseEvent event){
        gameBoard.setMessage("Restarting Game...");
        gameLogic.wallReset();
        gameLogic.ballReset();
        pauseMenuStage = (Stage) exitButton.getScene().getWindow();
        pauseMenuStage.close();
        stage.getScene().getRoot().setEffect(null);
        gameBoard.paint(gc);
    }

    @FXML
    public void exit(MouseEvent event){
        stage.close();
    }

    @FXML
    public void close(KeyEvent event){
        if(event.getCode() == KeyCode.ESCAPE){
            pauseMenuStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            pauseMenuStage.close();
            stage.getScene().getRoot().setEffect(null);
        }
    }
}
