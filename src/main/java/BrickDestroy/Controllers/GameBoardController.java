package BrickDestroy.Controllers;

import BrickDestroy.Models.GameLogic;
import BrickDestroy.Models.GameBoardModel;
import BrickDestroy.Models.Player;
import BrickDestroy.MvcManager;
import BrickDestroy.Views.GameBoardView;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameBoardController {

    private final GameLogic gameLogic;
    private final GameBoardModel gameBoardModel;
    private final GameBoardView gameBoardView;
    private final MvcManager mvcManager;

    public GameBoardController(GameBoardModel gameBoardModel, GameBoardView gameBoardView, GameLogic gameLogic, MvcManager mvcManager) {
        this.gameBoardModel = gameBoardModel;
        this.gameBoardView = gameBoardView;
        this.gameLogic = gameLogic;
        this.mvcManager = mvcManager;
        this.gameLogic.nextLevel();
        isKeyPressed(this.gameBoardView.getCanvas());
        isKeyReleased(this.gameBoardView.getCanvas());
        this.gameBoardView.paint(this.gameBoardView.getGraphicsContext());
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        private long fpsFlag = 0;
        private final static long DELAY_DURATION = 10_000_000;

        @Override
        public void handle(long currentNanoTime) {
            if(currentNanoTime - fpsFlag >= DELAY_DURATION){
                gamePulse(gameLogic);

                if(gameLogic.isBallLost())
                    handleBallLost();

                else if(gameLogic.isDone())
                    levelIncrement();

                handlePlayerMovement(gameLogic.getPlayer());

            }
            fpsFlag = currentNanoTime;
        }
    };

    public void gamePulse(GameLogic gameLogic){
        gameBoardView.paint(gameBoardView.getGraphicsContext());
        gameLogic.startMovement();
        gameLogic.detectCollision();
        displayMessage(String.format("Bricks: %d Balls %d Score %d", gameLogic.getBrickCount(),
                gameLogic.getBallCount(), gameLogic.getScore()));
    }

    public void handleBallLost(){
        if(gameLogic.ballEnd()){
            gameLogic.wallReset();
            displayMessage("Game over");
        }
        gameLogic.ballReset();
        animationTimer.stop();
        gameBoardModel.setGameRunning(false);
    }

    public void levelIncrement(){
        if (gameLogic.hasLevel()) {
            gameLogic.ballReset();
            gameLogic.wallReset();
            gameLogic.nextLevel();
            displayMessage("Go to Next Level");
            animationTimer.stop();
            gameBoardModel.setGameRunning(false);
        }
        else {
            displayMessage("ALL WALLS DESTROYED");
            animationTimer.stop();
        }
    }

    public void handlePlayerMovement(Player player){
        if(gameBoardModel.getUserInput().equalsIgnoreCase("A"))
            player.moveLeft();

        else if(gameBoardModel.getUserInput().equalsIgnoreCase("D"))
            player.moveRight();

        else
            player.stop();
    }

    public void displayMessage(String message){
        gameBoardModel.setGameMessage(message);
        gameBoardView.updateMessage(message);
    }

    public void isKeyPressed(Canvas canvas){
        canvas.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.A)
                gameBoardModel.setUserInput("A");

            else if (e.getCode() == KeyCode.D)
                gameBoardModel.setUserInput("D");

            else if (e.getCode() == KeyCode.SPACE)
                gameTimerAction();

            else if (e.getCode() == KeyCode.F1){
                if(e.isAltDown() && e.isShiftDown())
                    showDebugPanel((Stage)((Node)e.getSource()).getScene().getWindow());
            }

            else if (e.getCode() == KeyCode.ESCAPE)
                showPauseMenu((Stage)((Node)e.getSource()).getScene().getWindow());

            else if (e.getCode() == KeyCode.F)
                scoreBoard((Stage)((Node)e.getSource()).getScene().getWindow());
        });
    }

    public void gameTimerAction(){
        if(gameBoardModel.isGameRunning()){
            if (gameBoardModel.isGamePausing()) {
                gameBoardModel.setGamePausing(false);
                animationTimer.start();
            }
            else {
                gameBoardModel.setGamePausing(true);
                animationTimer.stop();
            }
        }
        else{
            gameBoardModel.setGameRunning(true);
            animationTimer.start();
        }
    }

    public void isKeyReleased(Canvas canvas){
        canvas.setOnKeyReleased(event -> gameBoardModel.setUserInput(""));
    }

    public void showDebugPanel(Stage previousStage) {
        Stage debugPanelStage = new Stage();
        debugPanelStage.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/DebugConsole-view.fxml",
                "DebugConsole", gameBoardView, previousStage));
        debugPanelStage.setX(previousStage.getX() + GameBoardModel.DEF_WIDTH/8);
        debugPanelStage.setY(previousStage.getY() + GameBoardModel.DEF_HEIGHT/3);
        debugPanelStage.initOwner(previousStage);
        debugPanelStage.initModality(Modality.APPLICATION_MODAL);
        debugPanelStage.setTitle("Game Settings");
        debugPanelStage.show();
    }

    public void showPauseMenu(Stage previousStage) {
        animationTimer.stop();
        gameBoardModel.setGamePausing(true);
        previousStage.getScene().getRoot().setEffect(new GaussianBlur());
        Stage pauseMenuStage = new Stage(StageStyle.TRANSPARENT);
        pauseMenuStage.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/PauseMenu-view.fxml",
                "PauseMenu", gameBoardView, previousStage));
        pauseMenuStage.setX(previousStage.getX());
        pauseMenuStage.setY(previousStage.getY());
        pauseMenuStage.initOwner(previousStage);
        pauseMenuStage.initModality(Modality.APPLICATION_MODAL);
        pauseMenuStage.setOpacity(0.65);
        pauseMenuStage.show();
    }

    public void scoreBoard(Stage currentStage){
        animationTimer.stop();
        gameBoardModel.setGamePausing(true);
        Stage scoreboard = new Stage();
        scoreboard.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/Scoreboard-view.fxml",
                "Scoreboard", gameBoardView, currentStage));
        scoreboard.setX(currentStage.getX());
        scoreboard.setY(currentStage.getY());
        scoreboard.initOwner(currentStage);
        scoreboard.initModality(Modality.APPLICATION_MODAL);
        scoreboard.setTitle("Scoreboard!");
        scoreboard.show();
    }
}
