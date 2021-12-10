package BrickDestroy.Views;

import BrickDestroy.Models.Ball;
import BrickDestroy.Models.Brick;
import BrickDestroy.Models.GameLogic;
import BrickDestroy.Models.Player;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

/**
 * The view class for gameBoard that displays data in the user interface.
 */
public class GameBoardView {

    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private final GameLogic gameLogic;

    /**
     * Constructor to initialize the canvas, canvas and the GameLogic object.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     */
    public GameBoardView(GameLogic gameLogic){
        canvas = new Canvas(600, 450);
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        this.gameLogic = gameLogic;
    }

    /**
     * A method responsible for clearing the game screen, calling methods to display the game entities such as brick, ball, player, message and crack.
     */
    public void paint(){
        graphicsContext.clearRect(0,0, 600, 450);
        drawBall(gameLogic.getBall());

        for(Brick b : gameLogic.getBricks())
            if(b.isBroken()) {
                drawBrick(b);
                drawCrack(b.getPath());
            }
        drawPlayer(gameLogic.getPlayer());
    }

    /**
     * A method responsible for displaying the current game message.
     * @param gameMessage the message to be displayed.
     */
    public void drawString(String gameMessage){
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillText(gameMessage, 230, 225);
    }

    /**
     * A method responsible for displaying the ball in the game.
     * @param ball the ball in the game.
     */
    public void drawBall(Ball ball){
        graphicsContext.setFill(ball.getInnerColor());
        graphicsContext.fillOval(ball.getUpperLeftX(), ball.getUpperLeftY(), ball.getRadius(), ball.getRadius());
        graphicsContext.setStroke(ball.getBorderColor());
        graphicsContext.strokeOval(ball.getUpperLeftX(), ball.getUpperLeftY(), ball.getRadius(), ball.getRadius());
    }

    /**
     * A method responsible for displaying the bricks in the game.
     * @param brick the bricks in the game.
     */
    private void drawBrick(Brick brick){
        graphicsContext.setFill(brick.getInnerColor());
        graphicsContext.fillRect(brick.getPos().getX(), brick.getPos().getY(), brick.getBrickWidth(), brick.getBrickHeight());
        graphicsContext.setStroke(brick.getBorderColor());
        graphicsContext.strokeRect(brick.getPos().getX(), brick.getPos().getY(), brick.getBrickWidth(), brick.getBrickHeight());
    }

    /**
     * A method to displaying the cracks on the crackable bricks.
     * @param path the path that contains the lines that form the cracks, if it is not null.
     */
    public void drawCrack(Path path){
        if (path == null || path.getElements().isEmpty())
            return;

        graphicsContext.beginPath();
        String[] moveToValue = obtainPathValues(path.getElements().get(0).toString());
        graphicsContext.moveTo(Double.parseDouble(moveToValue[0]), Double.parseDouble(moveToValue[1]));

        for(int i = 1; i < Brick.DEF_STEPS; i++){
            String [] lineToValue = obtainPathValues(path.getElements().get(i).toString());
            graphicsContext.lineTo(Double.parseDouble(lineToValue[0]), Double.parseDouble(lineToValue[1]));
        }

        graphicsContext.fill();
        graphicsContext.closePath();
        graphicsContext.stroke();
    }

    /*
    public void drawCrack(Path path){
        if (path == null || path.getElements().isEmpty())
            return;

        graphicsContext.beginPath();
        String[] str;
        String crackPath, value;
        double x, y;
        for(int i = 0; i < 35; i++){
            crackPath = path.getElements().get(i).toString();
            value = crackPath.replaceAll("[a-zA-z]", "").replaceAll("=", "");
            str = value.split(", ");
            x = Double.parseDouble(str[0]);
            y = Double.parseDouble(str[1]);
            if (i==0)
                graphicsContext.moveTo(x, y);
            else
                graphicsContext.lineTo(x, y);
        }
        graphicsContext.fill();
        graphicsContext.closePath();
        graphicsContext.stroke();
    }
     */

    /**
     * A method responsible to remove unwanted letters in the string and split the string into two values.
     * @param path the path that contains all 35 lines of crack elements.
     * @return an array of string which contains the correct data format.
     */
    public String[] obtainPathValues(String path){
        String str = path;
        str = str.replaceAll("[a-zA-z]", "").replaceAll("=", "");
        return str.split(", ");
    }

    /**
     * A method responsible for displaying the player in the game.
     * @param player the player in the game.
     */
    public void drawPlayer(Player player){
        graphicsContext.setFill(Player.INNER_COLOR);
        graphicsContext.fillRect(player.getUpperLeftX(),player.getUpperLeftY(),player.getWidth(),player.getHeight());
        graphicsContext.setStroke(Player.BORDER_COLOR);
        graphicsContext.strokeRect(player.getUpperLeftX(),player.getUpperLeftY(),player.getWidth(),player.getHeight());
    }

    /**
     * A method to refresh and redisplay the game message.
     * @param gameMessage the message to be displayed.
     */
    public void updateMessage(String gameMessage) {
        paint();
        drawString(gameMessage);
    }

    /**
     * A getter method to obtain a scene that contains canvas within a pane.
     * @return a scene that contains canvas within a pane.
     */
    public Scene getScene(){
        return new Scene(new Pane(canvas));
    }

    /**
     * A getter method to obtain canvas.
     * @return canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }
}
