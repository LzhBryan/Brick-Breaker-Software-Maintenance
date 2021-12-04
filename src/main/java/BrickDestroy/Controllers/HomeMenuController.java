package BrickDestroy.Controllers;

import BrickDestroy.GameBoard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeMenuController {

    @FXML
    private Button startMenuButton;
    @FXML
    private Button exitMenuButton;

    public void switchToGameScreen(MouseEvent event) {
        GameBoard gameBoard = new GameBoard();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(gameBoard.getScene());
    }

    public void exit(MouseEvent event) {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}
