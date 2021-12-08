package BrickDestroy.Controllers;

import BrickDestroy.Models.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInputController {
    @FXML
    private TextField textField;
    private GameLogic gameLogic;

    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public void submit(MouseEvent event) throws IOException {

        String username = textField.getText();
        if (!username.isEmpty()) {
            try (FileWriter fileWriter = new FileWriter("Score_list.txt", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

                printWriter.printf("\n\t\t%s\t\t\t\t\t%d", username, gameLogic.getScore());

                System.out.println("Data Successfully appended into file");
                printWriter.flush();
            }
        }
        Stage currentStage = (Stage) textField.getScene().getWindow();
        currentStage.close();
    }
}
