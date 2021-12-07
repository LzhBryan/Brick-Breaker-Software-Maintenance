package BrickDestroy.Views;

import BrickDestroy.Ball;
import BrickDestroy.Brick;
import BrickDestroy.GameLogic;
import BrickDestroy.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public class GameBoardView {

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final GameLogic gameLogic;

    public GameBoardView(GameLogic gameLogic){
        canvas = new Canvas(600, 450);
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        this.gameLogic = gameLogic;
    }

    public void paint(GraphicsContext gc){

        gc.clearRect(0,0, 600, 450);
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
        if (path == null || path.getElements().isEmpty())
            return;

            gc.beginPath();
            String[] moveToValue = obtainPathValues(path.getElements().get(0).toString());
            gc.moveTo(Double.parseDouble(moveToValue[0]), Double.parseDouble(moveToValue[1]));

            for(int i = 1; i < Brick.DEF_STEPS; i++){
                String [] lineToValue = obtainPathValues(path.getElements().get(i).toString());
                gc.lineTo(Double.parseDouble(lineToValue[0]), Double.parseDouble(lineToValue[1]));
            }

            gc.fill();
            gc.closePath();
            gc.stroke();
    }

    public String[] obtainPathValues(String path){
        String str = path;
        str = str.replaceAll("[a-zA-z]", "").replaceAll("=", "");
        return str.split(", ");
    }

    public void drawPlayer(Player player, GraphicsContext gc){
        gc.setFill(Player.INNER_COLOR);
        gc.fillRect(player.getUpperLeftX(),player.getUpperLeftY(),player.getWidth(),player.getHeight());
        gc.setStroke(Player.BORDER_COLOR);
        gc.strokeRect(player.getUpperLeftX(),player.getUpperLeftY(),player.getWidth(),player.getHeight());
    }

    public void updateMessage(String gameMessage) {
        paint(graphicsContext);
        drawString(gameMessage, graphicsContext);
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Scene getScene(){
        return new Scene(new Pane(canvas));
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
