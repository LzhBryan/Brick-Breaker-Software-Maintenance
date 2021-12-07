package BrickDestroy.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.ArrayList;

public class ScoreboardController{

    @FXML
    private ListView<String> scoreList;

    private ArrayList<String> scorelist = new ArrayList<String>();

    public void readScorelist(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("Score_list.txt"))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                scoreList.getItems().add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Score_list.txt"))) {
                String fileContent = "Highscore";
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

}
