package BrickDestroy;

import BrickDestroy.Controllers.DebugConsoleController;

public class DebugConsole {

    private GameBoard gameBoard;

    public GameLogicControl getGameLogic() {
        return gameLogic;
    }

    private GameLogicControl gameLogic;
    private DebugConsoleController debugConsoleController;

    public DebugConsole(GameLevels gameLevels, GameBoard gameBoard, GameLogicControl gameLogicControl){
        this.gameBoard = gameBoard;
        this.gameLogic = gameLogicControl;
    }


}
