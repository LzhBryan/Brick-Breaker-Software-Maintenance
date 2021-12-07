package BrickDestroy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

abstract public class Ball implements Mobile{

    private Circle ballFace;
    private Point2D centerPosition;
    private Point2D up, down, left, right;
    private final Color borderColor;
    private final Color innerColor;
    private int speedX;
    private int speedY;
    private final int radius;

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

    protected abstract Circle makeBall(Point2D center, int radius);

    public Circle createCircle(){
        Circle circle = ballFace;

        circle.setCenterX(getUpperLeftX());
        circle.setCenterY(getUpperLeftY());
        circle.setRadius(radius);

        return circle;
    }

    @Override
    public void move(){
        centerPosition = new Point2D(centerPosition.getX() + speedX, centerPosition.getY() + speedY);
        Circle circle = createCircle();
        ballFace = circle;
        setPoints(circle.getRadius(), circle.getRadius());
    }

    public void setSpeed(int speedX,int speedY){
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setXSpeed(int speedX){
        this.speedX = speedX;
    }

    public void setYSpeed(int speedY){
        this.speedY = speedY;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    // model
    public Color getBorderColor(){
        return borderColor;
    }

    public Color getInnerColor(){
        return innerColor;
    }

    public Point2D getPosition(){
        return centerPosition;
    }
    //
    @Override
    public void moveTo(Point2D point){
        centerPosition = point;
        ballFace = createCircle();
    }

    private void setPoints(double width,double height){
        up = new Point2D(centerPosition.getX(), centerPosition.getY() - (height/2));
        down = new Point2D(centerPosition.getX(), centerPosition.getY() + (height/2));
        left = new Point2D(centerPosition.getX() - (width/2), centerPosition.getY());
        right = new Point2D(centerPosition.getX() + (width/2), centerPosition.getY());
    }

    // model
    public int getRadius() {
        return radius;
    }

    public double getUpperLeftX() {
        return centerPosition.getX() - (double) (radius/2);
    }

    public double getUpperLeftY() {
        return centerPosition.getY() - (double) (radius/2);
    }

    public Point2D getUp() {
        return up;
    }

    public Point2D getDown() {
        return down;
    }

    public Point2D getLeft() {
        return left;
    }

    public Point2D getRight() {
        return right;
    }
}

