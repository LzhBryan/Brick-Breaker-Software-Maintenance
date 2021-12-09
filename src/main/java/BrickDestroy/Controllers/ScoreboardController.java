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
import java.util.ArrayList;

public class ScoreboardController {
    @FXML
    private ListView<String> scoreList;
    @FXML
    private ListView<String> usernameList;
    private GameLogic gameLogic;

    public void init(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

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
            for(int j = 0; j< score.size(); j++){
                scoreList.getItems().add(score.get(j).toString());
                usernameList.getItems().add(username.get(j));
            }
        } catch (FileNotFoundException e) {
           writeNewFile();
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

    public void bubbleSort(ArrayList<Integer> a, ArrayList<String> b, int length){
        for(int i=0; i<length-1; i++){
            for(int j=0; j<length-i-1; j++){
                if(a.get(j) < a.get(j+1)){
                    int temp = a.get(j);
                    a.set(j, a.get(j+1));
                    a.set(j+1, temp);

                    String temp1 = b.get(j);
                    b.set(j, b.get(j+1));
                    b.set(j+1, temp1);
                }
            }
        }
    }

    public void writeNewFile(){
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
    }
}
