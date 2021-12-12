package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A model class that defines the player data and behaviour.
 */
public class Player implements Mobile {
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;
    public static final int DEF_MOVE_AMOUNT = 5; // it was 5

    private final Rectangle playerFace;
    private Point2D ballPoint;
    private final int min;
    private final int max;
    private final int height;
    private final int width;
    private int moveAmount;

    /**
     * Constructor to initialize the player data and make the player rectangle.
     * @param ballPoint the initial ball point spawned in the game.
     * @param width the width of the player rectangle to be created.
     * @param height the height of the player rectangle to be created.
     * @param container this represents the screen dimension.
     */
    public Player(Point2D ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = (int) container.getX() + (width / 2);
        max = min + (int) container.getWidth() - width;
        this.width = width;
        this.height = height;
    }

    /**
     * A method to generate a rectangle that represents the player in game.
     * @param width the width of the player rectangle to be created.
     * @param height the height of the player rectangle to be created.
     * @return a rectangle object.
     */
    private Rectangle makeRectangle(int width,int height){
        Point2D playerPosition = new Point2D((int)(ballPoint.getX() - (width / 2)), (int)ballPoint.getY());
        return new Rectangle(playerPosition.getX(), playerPosition.getY(), width, height);
    }

    /**
     * A method that checks if the player collides with the ball.
     * @param ball the ball in the game.
     * @return true if ball collides with player, else false.
     */
    public boolean collideBall(Ball ball){
        return playerFace.contains(ball.getPosition()) && playerFace.contains(ball.getDown()) ;
    }

    /**
     * A method to handle player movement by adding velocity into x coordinate while y coordinate remains.
     */
    @Override
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint = new Point2D(x, ballPoint.getY());
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    /**
     * When user press A key, this method will set the velocity to negative.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * When user press D key, this method will set the velocity to positive.
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * When user did not press any key, this method will set the velocity to 0.
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * A method to reset and move the player rectangle to the initial spawn point.
     * @param position the initial spawn point.
     */
    @Override
    public void moveTo(Point2D position){
        ballPoint = position;
        playerFace.setX(ballPoint.getX() - playerFace.getWidth() /2);
        playerFace.setY(ballPoint.getY());
    }

    /**
     * A getter method to obtain the upper left x coordinate of the player rectangle.
     * @return upper left x coordinate of the player rectangle.
     */
    public double getUpperLeftX(){
        return playerFace.getX();
    }

    /**
     * A getter method to obtain the upper left y coordinate of the player rectangle.
     * @return upper left y coordinate of the player rectangle.
     */
    public double getUpperLeftY(){
        return playerFace.getY();
    }

    /**
     * A getter method to obtain the width of the player rectangle.
     * @return width of the player rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * A getter method to obtain the height of the player rectangle.
     * @return height of the player rectangle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * A getter method to obtain the velocity of player. (for junit testing)
     * @return velocity of player.
     */
    public int getMoveAmount() {
        return moveAmount;
    }
}
