package BrickDestroy.Controllers;

import BrickDestroy.GameLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UserinputController {

    @FXML
    private TextField textField;

    private String username;
    private boolean saveFlag = false;
    private GameLogic gameLogic;

    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public void submit(MouseEvent event) throws IOException {

        username = textField.getText();
        if(!username.isEmpty())
            saveFlag = true;

        if(saveFlag){
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            PrintWriter printWriter = null;

            try {
                fileWriter = new FileWriter("Score_list.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                printWriter = new PrintWriter(bufferedWriter);

                printWriter.printf("\n\t\t%s\t\t\t\t\t%d", username, gameLogic.getScore());;
                System.out.println(username);
                System.out.println(gameLogic.getScore());

                System.out.println("Data Successfully appended into file");
                printWriter.flush();

            } finally {
                printWriter.close();
                bufferedWriter.close();
                fileWriter.close();
            }
        }

        Stage currentStage = (Stage) textField.getScene().getWindow();
        currentStage.close();
    }

}
