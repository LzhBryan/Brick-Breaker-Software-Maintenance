package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class is the Ball model which contains all the data and behaviour of Ball.
 */
abstract public class Ball implements Mobile {

    private Circle ballFace;
    private Point2D centerPosition;
    private Point2D up, down, left, right;
    private final Color borderColor;
    private final Color innerColor;
    private int speedX;
    private int speedY;
    private final int radius;

    /**
     * This constructor will initialize and define the ball appearance along with the position in the game.
     * @param centerPosition The center point of the ball to be spawned in the game in (x,y) coordinates format.
     * @param radius The radius of the ball created.
     * @param innerColor The color that defines the ball interior part.
     * @param borderColor The color that defines the ball exterior part.
     */
    public Ball(Point2D centerPosition, int radius, Color innerColor, Color borderColor){
        this.centerPosition = centerPosition;
        this.radius = radius;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        ballFace = makeBall(this.centerPosition, radius);

        this.borderColor = borderColor;
        this.innerColor = innerColor;

        speedX = 0;
        speedY = 0;
    }

    /**
     * This method will generate the appearance of the ball.
     * @param center The center point of the ball to be spawned in the game in (x,y) coordinates format.
     * @param radius The radius of the ball.
     * @return A circle object to represent the ball appearance.
     */
    protected abstract Circle makeBall(Point2D center, int radius);

    /**
     * This method will create a circle object that represent the ball appearance.
     * @return A circle that with assigned position.
     */
    public Circle createCircle(){
        Circle circle = ballFace;

        circle.setCenterX(getUpperLeftX());
        circle.setCenterY(getUpperLeftY());
        circle.setRadius(radius);

        return circle;
    }

    /**
     * This method handles the ball movement by adding the velocity into the x,y coordinates of the ball.
     */
    @Override
    public void move(){
        centerPosition = new Point2D(centerPosition.getX() + speedX, centerPosition.getY() + speedY);
        Circle circle = createCircle();
        ballFace = circle;
        setPoints(circle.getRadius());
    }

    /**
     * This method will set the ball velocity in both horizontal and vertical direction.
     * @param speedX The horizontal velocity of the ball.
     * @param speedY The vertical velocity of the ball.
     */
    public void setSpeed(int speedX,int speedY){
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /**
     * A setter method to update the horizontal velocity of the ball.
     * @param speedX The new horizontal velocity value.
     */
    public void setXSpeed(int speedX){
        this.speedX = speedX;
    }

    /**
     * A setter method to update the vertical velocity of the ball.
     * @param speedY The new vertical velocity value.
     */
    public void setYSpeed(int speedY){
        this.speedY = speedY;
    }

    /**
     * A method to reverse the horizontal velocity of the ball.
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * A method to reverse the vertical velocity of the ball.
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * A getter method to obtain the border color of the ball.
     * @return border color of the ball.
     */
    public Color getBorderColor(){
        return borderColor;
    }

    /**
     * A getter method to obtain the inner color of the ball.
     * @return inner color of the ball.
     */
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * A getter method to return the center position of the ball.
     * @return center position of the ball in (x,y) format.
     */
    public Point2D getPosition(){
        return centerPosition;
    }

    /**
     * A method to update and move the center position of the ball.
     * @param point The point the ball is supposed to move to.
     */
    @Override
    public void moveTo(Point2D point){
        centerPosition = point;
        ballFace = createCircle();
    }

    /**
     * A method to update the (x,y) coordinates of the ball in the four directions.
     * @param radius the radius of the ball.
     */
    private void setPoints(double radius){
        up = new Point2D(centerPosition.getX(), centerPosition.getY() - (radius/2));
        down = new Point2D(centerPosition.getX(), centerPosition.getY() + (radius/2));
        left = new Point2D(centerPosition.getX() - (radius/2), centerPosition.getY());
        right = new Point2D(centerPosition.getX() + (radius/2), centerPosition.getY());
    }

    /**
     * A getter method to obtain the radius of the ball.
     * @return radius of the ball.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * A method to obtain the upper left x coordinates of the ball.
     * @return upper left x coordinates of the ball.
     */
    public double getUpperLeftX() {
        return centerPosition.getX() - (double) (radius/2);
    }

    /**A method to obtain the upper left y coordinates of the ball.
     * @return upper left y coordinates of the ball.
     */
    public double getUpperLeftY() {
        return centerPosition.getY() - (double) (radius/2);
    }

    /**
     * A getter method to obtain the top position of the ball.
     * @return top position of the ball.
     */
    public Point2D getUp() {
        return up;
    }

    /**
     * A getter method to obtain the bottom position of the ball.
     * @return bottom position of the ball.
     */
    public Point2D getDown() {
        return down;
    }

    /**
     * A getter method to obtain the left position of the ball.
     * @return left position of the ball.
     */
    public Point2D getLeft() {
        return left;
    }

    /**
     * A getter method to obtain the right position of the ball.
     * @return right position of the ball.
     */
    public Point2D getRight() {
        return right;
    }

    /**
     * A getter method to obtain the horizontal velocity. (for junit testing)
     * @return the horizontal velocity.
     */
    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }
}

