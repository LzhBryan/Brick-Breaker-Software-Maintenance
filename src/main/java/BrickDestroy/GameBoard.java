package BrickDestroy;

import BrickDestroy.Controllers.DebugConsoleController;
import BrickDestroy.Controllers.PauseMenuController;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class GameBoard {
    public static final double DEF_WIDTH = 600;
    public static final double DEF_HEIGHT = 450;

    private final GameLogicControl gameLogic;
    public String userInput = "";
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private String gameMessage;
    private boolean gamePause = false;
    private boolean gameRunning;

    public GameBoard(){
        canvas = new Canvas(600, 450);
        graphicsContext = canvas.getGraphicsContext2D();
        gameLogic = new GameLogicControl(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,(double) 6/2, new Point2D(300,430));
        //debugConsole = new DebugConsole(gameLevels,this, gameLogic);
        gameLogic.nextLevel();
        canvas.setFocusTraversable(true);

        gameRunning = false;
        isKeyPressed(canvas);
        isKeyReleased(canvas);
        paint(graphicsContext);
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate = 0;
        private final static long DELAY_DURATION = 10_000_000;

        @Override
        public void handle(long currentNanoTime)
        {
            if(currentNanoTime - lastUpdate >= DELAY_DURATION){

                paint(graphicsContext);
                gameLogic.startMovement();
                gameLogic.detectCollision();
                gameMessage = String.format("Bricks: %d Balls %d", gameLogic.getBrickCount(),gameLogic.getBallCount());

                if(gameLogic.isBallLost()){
                    if(gameLogic.ballEnd()){
                        gameLogic.wallReset();
                        gameMessage = "Game over";
                    }
                    gameLogic.ballReset();
                    paint(graphicsContext);
                    animationTimer.stop();
                    gameRunning = false;
                }
                else if(gameLogic.isDone()) {
                    if (gameLogic.hasLevel()) {
                        gameLogic.ballReset();
                        gameLogic.wallReset();
                        gameLogic.nextLevel();
                        updateMessage("Go to Next Level");
                        animationTimer.stop();
                        gameRunning = false;
                    } else {
                        updateMessage("ALL WALLS DESTROYED");
                        animationTimer.stop();
                    }
                }

                if(userInput.equalsIgnoreCase("A"))
                    gameLogic.getPlayer().moveLeft();

                else if(userInput.equalsIgnoreCase("D"))
                    gameLogic.getPlayer().moveRight();

                else
                    gameLogic.getPlayer().stop();

            }
            lastUpdate = currentNanoTime;
        }
    };

    public void paint(GraphicsContext gc){
        gc.clearRect(0,0, 600, 450);

        drawString(gameMessage, gc);
        drawBall(gameLogic.getBall(), gc);

        for(Brick b : gameLogic.getBricks())
            if(b.isBroken()) {
                drawBrick(b, gc);
                drawCrack(b.getPath(), gc);
            }

        drawPlayer(gameLogic.getPlayer(), gc);
    }

    public void drawString(String gameMessage, GraphicsContext gc){
        gc.setFill(Color.BLUE);
        gc.fillText(gameMessage, 250, 225);
    }

    public void drawBall(Ball ball, GraphicsContext gc){
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ball.getUpperLeftX(), ball.getUpperLeftY(), ball.getRadius(), ball.getRadius());
        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ball.getUpperLeftX(), ball.getUpperLeftY(), ball.getRadius(), ball.getRadius());
    }

    private void drawBrick(Brick brick, GraphicsContext gc){
        gc.setFill(brick.getInnerColor());
        gc.fillRect(brick.getPos().getX(), brick.getPos().getY(), brick.getBrickWidth(), brick.getBrickHeight());
        gc.setStroke(brick.getBorderColor());
        gc.strokeRect(brick.getPos().getX(), brick.getPos().getY(), brick.getBrickWidth(), brick.getBrickHeight());
    }

    public void drawCrack(Path path, GraphicsContext gc){

        if (path != null && !path.getElements().isEmpty()){

            gc.beginPath();

            String[] moveToValue = obtainPathValues(path.getElements().get(0).toString());
            gc.moveTo(Double.parseDouble(moveToValue[0]),Double.parseDouble(moveToValue[1]));

            for(int i = 1; i < Brick.DEF_STEPS; i++){
                String [] lineToValue = obtainPathValues(path.getElements().get(i).toString());
                gc.lineTo(Double.parseDouble(lineToValue[0]),Double.parseDouble(lineToValue[1]));
            }

            gc.fill();
            gc.closePath();
            gc.stroke();
        }
    }

    public String[] obtainPathValues(String path){
        path = path.replaceAll("[a-zA-z]", "").replaceAll("=", "");
        return path.split(", ");
    }

    public void drawPlayer(Player player, GraphicsContext gc){
        gc.setFill(Player.INNER_COLOR);
        gc.fillRect(player.getUpperLeftX(),player.getUpperLeftY(),player.getWidth(),player.getHeight());
        gc.setStroke(Player.BORDER_COLOR);
        gc.strokeRect(player.getUpperLeftX(),player.getUpperLeftY(),player.getWidth(),player.getHeight());
    }

    public void updateMessage(String gameMessage) {
        this.gameMessage = gameMessage;
        paint(graphicsContext);
    }

    public void isKeyPressed(Canvas canvas){
        canvas.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.A)
                userInput = "A";

            else if (e.getCode() == KeyCode.D)
                userInput = "D";

            else if (e.getCode() == KeyCode.SPACE){
                userInput = "SPACE";
                    if(gameRunning){
                        if (gamePause) {
                            gamePause = false;
                            animationTimer.start();
                        }
                        else {
                            gamePause = true;
                            animationTimer.stop();
                        }
                    }

                    else{
                        gameRunning = true;
                        animationTimer.start();
                    }
            }

            else if (e.getCode() == KeyCode.F1){
                if(e.isAltDown() && e.isShiftDown())
                    showDebugPanel((Stage)((Node)e.getSource()).getScene().getWindow());
            }

            else if (e.getCode() == KeyCode.ESCAPE)
                showPauseMenu((Stage)((Node)e.getSource()).getScene().getWindow());
        });
    }

    public void isKeyReleased(Canvas canvas){
        canvas.setOnKeyReleased(event -> userInput = "");
    }

    public Scene getScene(){
        return new Scene(new Pane(canvas));
    }

    public void showDebugPanel(Stage previousStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/DebugConsole-view.fxml"));
        Stage debugPanelStage = new Stage();
        debugPanelStage.setX(previousStage.getX() + DEF_WIDTH/8);
        debugPanelStage.setY(previousStage.getY() + DEF_HEIGHT/3);
        debugPanelStage.initOwner(previousStage);
        debugPanelStage.initModality(Modality.APPLICATION_MODAL);
        debugPanelStage.setTitle("Game Settings");
        try {
            debugPanelStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        debugPanelStage.show();
        DebugConsoleController debugConsoleController = loader.getController();
        debugConsoleController.initModel(gameLogic, previousStage, loader);
    }

    public void showPauseMenu(Stage previousStage) {
        animationTimer.stop();
        previousStage.getScene().getRoot().setEffect(new GaussianBlur());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/PauseMenu-view.fxml"));
        Stage pauseMenuStage = new Stage(StageStyle.TRANSPARENT);
        pauseMenuStage.setX(previousStage.getX());
        pauseMenuStage.setX(previousStage.getY());
        pauseMenuStage.initOwner(previousStage);
        pauseMenuStage.initModality(Modality.APPLICATION_MODAL);
        pauseMenuStage.setOpacity(0.65);
        try {
            pauseMenuStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pauseMenuStage.show();
        PauseMenuController pauseMenuController = loader.getController();
        pauseMenuController.initModel(previousStage, gameLogic, this, graphicsContext);
    }
}
