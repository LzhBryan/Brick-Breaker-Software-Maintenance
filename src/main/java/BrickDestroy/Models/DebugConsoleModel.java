package BrickDestroy.Models;

import BrickDestroy.GameLogic;

public class DebugConsoleModel {

    private final GameLogic gameLogic;

    public DebugConsoleModel(GameLogic gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

}
