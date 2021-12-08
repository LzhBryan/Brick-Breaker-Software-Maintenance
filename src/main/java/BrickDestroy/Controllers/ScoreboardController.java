package BrickDestroy.Controllers;

import BrickDestroy.GameLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class ScoreboardController {

    @FXML
    private ListView<String> scoreList;

    private ArrayList<String> scorelist = new ArrayList<String>();
    private GameLogic gameLogic;

    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public void readScorelist() {
        scoreList.getItems().clear();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Score_list.txt"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                scoreList.getItems().add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Score_list.txt"))) {
                String fileContent = "\t\tExampleUser1\t\t\t0";
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
        readScorelist();
    }

    public void updateScoreboard(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/Userinput-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
        UserinputController userinputController = loader.getController();
        userinputController.init(gameLogic);
    }
}
