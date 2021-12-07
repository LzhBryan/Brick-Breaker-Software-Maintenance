package BrickDestroy.Models;

import BrickDestroy.GameLogic;

public class ScoreModel {

    private final GameLogic gameLogic;
    private int[] score;

    public ScoreModel(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }
}
