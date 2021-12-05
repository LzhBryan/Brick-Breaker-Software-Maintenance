package BrickDestroy.Models;

import BrickDestroy.GameLogicControl;

public class DebugConsoleModel {

    private final GameLogicControl gameLogic;

    public DebugConsoleModel(GameLogicControl gameLogic){
        this.gameLogic = gameLogic;
    }

    public GameLogicControl getGameLogic() {
        return gameLogic;
    }

}
