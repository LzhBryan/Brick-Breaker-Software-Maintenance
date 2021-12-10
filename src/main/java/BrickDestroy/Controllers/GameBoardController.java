package BrickDestroy.Controllers;

import BrickDestroy.Models.GameLogic;
import BrickDestroy.Models.GameBoardModel;
import BrickDestroy.Models.Player;
import BrickDestroy.MvcManager;
import BrickDestroy.Views.GameBoardView;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A class that handles the game board user input and call the suitable methods to update both the game board model and view.
 */
public class GameBoardController {

    private final GameLogic gameLogic;
    private final GameBoardModel gameBoardModel;
    private final GameBoardView gameBoardView;
    private final MvcManager mvcManager;

    /**
     * Constructor to initialize the level, other objects and display the entities.
     * @param gameBoardModel A model that contains data for the gameBoard view and controller.
     * @param gameBoardView The view class for gameBoard that displays data in the user interface.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     * @param mvcManager A class that manage most of the model, controller and view (FXML) instantiation.
     */
    public GameBoardController(GameBoardModel gameBoardModel, GameBoardView gameBoardView, GameLogic gameLogic, MvcManager mvcManager) {
        this.gameBoardModel = gameBoardModel;
        this.gameBoardView = gameBoardView;
        this.gameLogic = gameLogic;
        this.mvcManager = mvcManager;
        this.gameLogic.nextLevel();
        isKeyPressed(this.gameBoardView.getCanvas());
        isKeyReleased(this.gameBoardView.getCanvas());
        this.gameBoardView.paint();
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

    /**
     * A method that displays the entities, make the ball and player movable, detect collision and display game information.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     */
    public void gamePulse(GameLogic gameLogic){
        gameBoardView.paint();
        gameLogic.startMovement();
        gameLogic.detectCollision();
        displayMessage(String.format("Bricks: %d Balls %d Score %d", gameLogic.getBrickCount(),
                gameLogic.getBallCount(), gameLogic.getScore()));
    }

    /**
     * A method to reduce player score when dropping the ball, and stop the timer.
     */
    public void handleBallLost(){
        if(gameLogic.ballEnd())
            penalty();

        gameLogic.reset();
        animationTimer.stop();
        gameBoardModel.setGameRunning(false);
        gameLogic.reduceScore(100);
    }

    /**
     * A method that increase the level when all bricks are broken.
     */
    public void levelIncrement(){
        if (gameLogic.hasLevel())
           newLevel();
        else
            endingScreen();
    }

    /**
     * A method that move the player according to the user input variable, otherwise stop the player movement.
     * @param player the player in the game.
     */
    public void handlePlayerMovement(Player player){
        if(gameBoardModel.getUserInput().equalsIgnoreCase("A"))
            player.moveLeft();

        else if(gameBoardModel.getUserInput().equalsIgnoreCase("D"))
            player.moveRight();

        else
            player.stop();
    }

    /**
     * A method to update the model and display the game message.
     * @param message the message to be displayed.
     */
    public void displayMessage(String message){
        gameBoardModel.setGameMessage(message);
        gameBoardView.updateMessage(message);
    }

    /**
     * A method to listen to key inputs from user and call the respective methods.
     * @param canvas the canvas from game board view class.
     */
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

    /**
     * A method that checks the game running, pausing and either initialize the game or pause the game whenever user press the spacebar key.
     */
    public void gameTimerAction(){
        if (gameBoardModel.isGameLost()){
            gameBoardModel.setGameLost(false);
            gameLogic.setScore(0);
        }

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

    /**
     * A method that update the user input in the game model whenever user releases the key.
     * @param canvas the canvas from game board view class.
     */
    public void isKeyReleased(Canvas canvas){
        canvas.setOnKeyReleased(event -> gameBoardModel.setUserInput(""));
    }

    /**
     * A method to pop up the debug panel whenever user press ALT + SHIFT + F1.
     * @param previousStage the current running game stage.
     */
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

    /**
     * A method that display the pause menu whenever user clicks on the "ESCAPE" key.
     * @param previousStage the current running game stage.
     */
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

    /**
     * A method that display the score board pop up whenever user press the F key.
     * @param currentStage the current gaming stage.
     */
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

    /**
     * A method to reduce a portion of the user score and display an alert pop up whenever the user lose all the ball.
     */
    public void penalty(){
        gameLogic.wallReset();
        displayMessage("Game over");
        gameLogic.reduceScore(gameLogic.getScore()/5);
        animationTimer.stop();
        gameBoardModel.setGameRunning(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("Results: " + gameLogic.getScore());
        alert.setContentText("Save before pressing spacebar if you wish to");
        alert.show();
        gameBoardModel.setGameLost(true);
    }

    /**
     * A method to reward the user some point and display a popup message if there are remaining ball count as well as moving on to the new level.
     */
    public void newLevel(){
        gameLogic.increaseScore(gameLogic.getBallCount());
        animationTimer.stop();
        gameBoardModel.setGameRunning(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CONGRATS");
        alert.setContentText("You are rewarded with " + 300*gameLogic.getBallCount() + " points");
        alert.show();
        gameLogic.reset();
        gameLogic.wallReset();
        gameLogic.nextLevel();
        gameLogic.resetBallCount();
        displayMessage("Go to Next Level");
    }

    /**
     * A method to load the ending screen fxml when the user has finished the final level of the game.
     */
    public void endingScreen(){
        displayMessage("ALL WALLS DESTROYED");
        animationTimer.stop();
        Stage EndingScreen = new Stage();
        EndingScreen.setScene(mvcManager.switchScenes("/BrickDestroy/FXML/EndingScreen-view.fxml",
                "EndingScreen", gameBoardView, new Stage()));
        EndingScreen.initModality(Modality.APPLICATION_MODAL);
        EndingScreen.setTitle("Ending Screen");
        EndingScreen.show();
    }
}
