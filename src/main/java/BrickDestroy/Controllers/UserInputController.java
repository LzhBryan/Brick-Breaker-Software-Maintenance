package BrickDestroy.Controllers;

import BrickDestroy.Models.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A controller class that is responsible for handling any user interaction/input with the user input view.
 */
public class UserInputController {
    @FXML
    private TextField textField;
    private GameLogic gameLogic;

    /**
     * A method to initialize the gameLogic object.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     */
    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    /**
     * A method that invokes when user clicks on the "submit" button to open and write the username and score into the txt file.
     * @throws IOException throws exception if could not open the txt file.
     */
    public void submit() throws IOException {

        String username = textField.getText();
        if (!username.isEmpty()) {
            try (FileWriter fileWriter = new FileWriter("Score_list.txt", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                 printWriter.printf("\n%s, %d", username, gameLogic.getScore());
                 System.out.println("Data Successfully appended into file");
                 printWriter.flush();
            }
        }
        Stage currentStage = (Stage) textField.getScene().getWindow();
        currentStage.close();
    }
}
