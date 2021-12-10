package BrickDestroy.Controllers;

import BrickDestroy.Models.GameLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

/**
 * A controller class that is responsible for handling any user interaction/input with the scoreboard view.
 */
public class ScoreboardController {
    @FXML
    private ListView<String> scoreList;
    @FXML
    private ListView<String> usernameList;
    private GameLogic gameLogic;

    /**
     * A method to initialize the gameLogic object.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     */
    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    /**
     * A method to read the usernames and scores in the txt file, then add it into the listview object.
     */
    public void readScoreList() {
        ArrayList<Integer> score = new ArrayList<>();
        ArrayList<String> username = new ArrayList<>();
        int i =0;

        usernameList.getItems().clear();
        scoreList.getItems().clear();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Score_list.txt"))) {
            String input = bufferedReader.readLine();
            while (input != null) {
                String[] str = input.split(", ");
                username.add(i, str[0]);
                score.add(i, Integer.parseInt(str[1]));
                i++;
                input = bufferedReader.readLine();
            }
            bubbleSort(score, username, i);
            for(int j = 0; j < score.size(); j++){
                scoreList.getItems().add(score.get(j).toString());
                usernameList.getItems().add(username.get(j));
            }
        } catch (FileNotFoundException e) {
           writeNewFile();
        } catch (IOException e) {
            // Exception handling
        }
    }

    /**
     * A method that re-read the score list from txt file again if user clicks on the "refresh" button.
     */
    public void refresh(){
        readScoreList();
    }

    /**
     * A method to load the user input FXML file when user clicks on the "Update Scoreboard" button, also checks if the user has played the game or not.
     * @throws IOException throws exception if the FXML file could not be loaded.
     */
    public void updateScoreboard() throws IOException {
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

    /**
     * A method that use bubble sort algorithm to sort the users scores and usernames.
     * @param scores the scores that are read from the txt file.
     * @param usernames the usernames that are read from the txt file.
     * @param length the length of both the array list.
     */
    public void bubbleSort(ArrayList<Integer> scores, ArrayList<String> usernames, int length){
        for(int i=0; i<length-1; i++){
            for(int j=0; j<length-i-1; j++){
                if(scores.get(j) < scores.get(j+1)){
                    int temp = scores.get(j);
                    scores.set(j, scores.get(j+1));
                    scores.set(j+1, temp);

                    String temp1 = usernames.get(j);
                    usernames.set(j, usernames.get(j+1));
                    usernames.set(j+1, temp1);
                }
            }
        }
    }

    /**
     * A method that writes a new file if such txt file could not be found.
     */
    public void writeNewFile(){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Score_list.txt"))) {
            String fileContent = "ExampleUser1, 0";
            try {
                bufferedWriter.write(fileContent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
