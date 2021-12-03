package BrickDestroy.Controllers;

import BrickDestroy.GameBoard;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController{
    private Stage stage;

    public void switchToGameScreen(MouseEvent event) throws IOException {
        GameBoard gameBoard = new GameBoard();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(gameBoard.getScene());
    }

    public void exit(MouseEvent event) {
        System.out.println("Goodbye " + System.getProperty("user.name"));
        System.exit(0);
    }
}
