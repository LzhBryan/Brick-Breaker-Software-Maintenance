package BrickDestroy.Controllers;

import BrickDestroy.GameLevels;
import BrickDestroy.Models.ScoreModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScoreController{

    @FXML
    private Label score1;

    @FXML
    private Label score2;

    @FXML
    private Label score3;

    @FXML
    private Label score4;

    private  ScoreModel scoreModel;
    private int[] score;

    public void initModel(ScoreModel scoreModel){
        this.scoreModel = scoreModel;
        score = new int[GameLevels.LEVELS_COUNT];
    }

    public void score(){
        score[scoreModel.getGameLogic().getLevel()-1] = scoreModel.getGameLogic().getScore();
        score1.setText(Integer.toString(score[0]));
        score2.setText(Integer.toString(score[1]));
        score3.setText(Integer.toString(score[2]));
        score4.setText(Integer.toString(score[3]));
    }

}
