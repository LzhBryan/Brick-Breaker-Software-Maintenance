package BrickDestroy.Controllers;

import BrickDestroy.Models.GameLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.*;

public class ScoreboardController {

    @FXML
    private ListView<String> scoreList;
    @FXML
    private ListView<String> username;

    private GameLogic gameLogic;

    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public void readScoreList() {
        username.getItems().clear();
        scoreList.getItems().clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Score_list.txt"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] str = line.split(", ");
                username.getItems().add(str[0]);
                scoreList.getItems().add(str[1]);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Score_list.txt"))) {
                String fileContent = "\t\tExampleUser1,\t\t\t0";
                try {
                    bufferedWriter.write(fileContent);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            // Exception handling
        }
    }

    public void refresh(){
        readScoreList();
    }

    public void updateScoreboard(MouseEvent event) throws IOException {
        if(gameLogic != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/UserInput-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
            UserInputController userinputController = loader.getController();
            userinputController.init(gameLogic);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning alert");
            alert.setHeaderText("You have not played the game!");
            alert.setContentText("Press F while playing the game to record your score!");
            alert.showAndWait();
        }
    }
}
