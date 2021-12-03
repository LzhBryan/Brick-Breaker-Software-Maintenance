package BrickDestroy;

import BrickDestroy.Controllers.DebugConsoleController;
import BrickDestroy.Controllers.PauseMenuController;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public static final Color BG_COLOR = Color.WHITE;

    private GameLogicControl gameLogic;
    public String input = "";
    private Canvas canvas;
    private GraphicsContext gc;
    private String message;
    private boolean gamePause = false;
    private DebugConsole debugConsole;
    private GameLevels gameLevels;
    private boolean gameRunning;

    public GameBoard(){
        canvas = new Canvas(600, 450);
        gc = canvas.getGraphicsContext2D();
        gameLogic = new GameLogicControl(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2, new Point2D(300,430));
        debugConsole = new DebugConsole(gameLevels,this, gameLogic);
        gameLogic.nextLevel();
        canvas.setFocusTraversable(true);

        gameRunning = false;
        isKeyPressed(canvas);
        isKeyReleased(canvas);
        paint(gc);
    }

    AnimationTimer animationTimer = new AnimationTimer() {
        private long lastUpdate = 0;
        final private static long DELAY_DURATION = 10_000_000;

        @Override
        public void handle(long currentNanoTime)
        {
            if(currentNanoTime - lastUpdate >= DELAY_DURATION){

                paint(gc);
                gameLogic.move();
                gameLogic.findImpacts();
                message = String.format("Bricks: %d Balls %d", gameLogic.getBrickCount(),gameLogic.getBallCount());

                if(gameLogic.isBallLost()){
                    if(gameLogic.ballEnd()){
                        gameLogic.wallReset();
                        message = "Game over";
                    }
                    gameLogic.ballReset();
                    animationTimer.stop();
                }
                else if(gameLogic.isDone()) {
                    if (gameLogic.hasLevel()) {
                        message = "Go to Next Level";
                        animationTimer.stop();
                        gameLogic.ballReset();
                        gameLogic.wallReset();
                        gameLogic.nextLevel();
                    } else {
                        message = "ALL WALLS DESTROYED";
                        animationTimer.stop();
                    }
                }

                if(input.equalsIgnoreCase("A"))
                    gameLogic.player.moveLeft();

                else if(input.equalsIgnoreCase("D"))
                    gameLogic.player.movRight();

                else
                    gameLogic.player.stop();

            }
            lastUpdate = currentNanoTime;
        }
    };

    public void paint(GraphicsContext gc){
        gc.clearRect(0,0, 600, 450);

        drawString(message, gc);
        drawBall(gameLogic.ball, gc);

        for(Brick b : gameLogic.bricks)
            if(!b.isBroken()) {
                drawBrick(b, gc);
                drawCrack(b.getPath(), gc);
            }

        drawPlayer(gameLogic.player, gc);
    }

    public void drawString(String message, GraphicsContext gc){
        gc.setFill(Color.BLUE);
        gc.fillText(message, 250, 225);
    }

    public void drawBall(Ball ball, GraphicsContext gc){
        gc.setFill(ball.getInnerColor());
        gc.fillOval(ball.getUpperLeftX(),ball.getUpperLeftY(),ball.getRadius(),ball.getRadius());
        gc.setStroke(ball.getBorderColor());
        gc.strokeOval(ball.getUpperLeftX(),ball.getUpperLeftY(),ball.getRadius(),ball.getRadius());
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

    public void setMessage(String message) {
        this.message = message;
    }

    public void isKeyPressed(Canvas canvas){
        canvas.setOnKeyPressed(e-> {
            if(e.getCode() == KeyCode.A){
                input = "A";
            }

            else if (e.getCode() == KeyCode.D){
                input = "D";
            }

            else if (e.getCode() == KeyCode.SPACE){
                input = "SPACE";
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

            else if(e.getCode() == KeyCode.ENTER){
                animationTimer.start();
            }

            else if (e.getCode() == KeyCode.F1){
                if(e.isAltDown() && e.isShiftDown())
                    showDebugPanel((Stage)((Node)e.getSource()).getScene().getWindow());
            }

            else if (e.getCode() == KeyCode.ESCAPE){
                showPauseMenu((Stage)((Node)e.getSource()).getScene().getWindow());
            }
        });
    }

    public void isKeyReleased(Canvas canvas){
        canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                input = "";
            }
        });
    }

    public Scene getScene(){
        return new Scene(new Pane(canvas));
    }

    public void showDebugPanel(Stage previousStage){
        Stage stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/DebugConsole-view.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.setX(previousStage.getX() + DEF_WIDTH/8);
            stage.setY(previousStage.getY() + DEF_HEIGHT/3);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(previousStage);
            stage.setTitle("Game Settings");
            stage.show();
            DebugConsoleController debugConsoleController = loader.getController();
            debugConsoleController.initModel(debugConsole, gameLogic);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void showPauseMenu(Stage previousStage){
        animationTimer.stop();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/PauseMenu-view.fxml"));

        previousStage.getScene().getRoot().setEffect(new GaussianBlur());

        Stage stage1 = new Stage(StageStyle.TRANSPARENT);
        stage1.setX(previousStage.getX());
        stage1.setY(previousStage.getY());
        stage1.initOwner(previousStage);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setOpacity(0.65);

        try {
            stage1.setScene(new Scene(loader.load(), Color.TRANSPARENT));
            stage1.show();
            PauseMenuController pauseMenuController = loader.getController();
            pauseMenuController.initModel(previousStage, gameLogic, this, gc);
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
