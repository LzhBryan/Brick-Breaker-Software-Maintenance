package BrickDestroy.Controllers;

import BrickDestroy.GameLogic;
import BrickDestroy.Models.DebugConsoleModel;
import BrickDestroy.Models.GameBoardModel;
import BrickDestroy.Models.PauseMenuModel;
import BrickDestroy.Models.ScoreModel;
import BrickDestroy.Player;
import BrickDestroy.Views.GameBoardView;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;

public class GameBoardController {

    private final GameLogic gameLogic;
    private final GameBoardModel gameBoardModel;
    private final GameBoardView gameBoardView;

    public GameBoardController(GameBoardModel gameBoardModel, GameBoardView gameBoardView, GameLogic gameLogic) {
        this.gameBoardModel = gameBoardModel;
        this.gameBoardView = gameBoardView;
        this.gameLogic = gameLogic;
        this.gameLogic.nextLevel();
        isKeyPressed(this.gameBoardView.getCanvas());
        isKeyReleased(this.gameBoardView.getCanvas());
        this.gameBoardView.paint(this.gameBoardView.getGraphicsContext());
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate = 0;
        private final static long DELAY_DURATION = 10_000_000;

        @Override
        public void handle(long currentNanoTime) {
            if(currentNanoTime - lastUpdate >= DELAY_DURATION){
                gamePulse(gameLogic);

                if(gameLogic.isBallLost())
                    handleBallLost();

                else if(gameLogic.isDone())
                    levelIncrement();

                handlePlayerMovement(gameLogic.getPlayer());

            }
            lastUpdate = currentNanoTime;
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
            //gameLogic.wallReset();
            levelIncrement();
            displayMessage("Game over");
            //scorePopup(gameBoardModel.getGameStage());
        }
        gameLogic.ballReset();
        animationTimer.stop();
        gameBoardModel.setGameRunning(false);
    }

    public void levelIncrement(){
        scorePopup(gameBoardModel.getGameStage());
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/DebugConsole-view.fxml"));
        Stage debugPanelStage = new Stage();
        debugPanelStage.setX(previousStage.getX() + GameBoardModel.DEF_WIDTH/8);
        debugPanelStage.setY(previousStage.getY() + GameBoardModel.DEF_HEIGHT/3);
        debugPanelStage.initOwner(previousStage);
        debugPanelStage.initModality(Modality.APPLICATION_MODAL);
        debugPanelStage.setTitle("Game Settings");
        try {
            debugPanelStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        debugPanelStage.show();
        DebugConsoleModel debugModel = new DebugConsoleModel(gameLogic);
        DebugConsoleController debugConsoleController = loader.getController();
        debugConsoleController.initModel(debugModel);
    }

    public void showPauseMenu(Stage previousStage) {
        animationTimer.stop();
        gameBoardModel.setGamePausing(true);
        previousStage.getScene().getRoot().setEffect(new GaussianBlur());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/PauseMenu-view.fxml"));
        Stage pauseMenuStage = new Stage(StageStyle.TRANSPARENT);
        pauseMenuStage.setX(previousStage.getX());
        pauseMenuStage.setY(previousStage.getY());
        pauseMenuStage.initOwner(previousStage);
        pauseMenuStage.initModality(Modality.APPLICATION_MODAL);
        pauseMenuStage.setOpacity(0.65);
        try {
            pauseMenuStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pauseMenuStage.show();
        PauseMenuModel pauseMenuModel = new PauseMenuModel(gameLogic, previousStage, gameBoardView);
        PauseMenuController pauseMenuController = loader.getController();
        pauseMenuController.initModel(pauseMenuModel);
    }

    public void scorePopup(Stage currentStage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/ScorePopup-view.fxml"));
        Stage scorePopup = new Stage();
        scorePopup.initOwner(currentStage);
        scorePopup.initModality(Modality.APPLICATION_MODAL);
        try {
            scorePopup.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scorePopup.setTitle("Score for this round!");
        scorePopup.show();
        ScoreModel scoreModel = new ScoreModel(gameLogic);
        ScoreController scoreController = loader.getController();
        scoreController.initModel(scoreModel);
    }

}
