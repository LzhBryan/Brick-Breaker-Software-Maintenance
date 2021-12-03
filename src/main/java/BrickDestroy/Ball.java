package BrickDestroy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

abstract public class Ball{

    private Shape ballFace;
    private Point2D center;
    Point2D up, down, left, right;
    private Color border, inner;
    private int speedX;
    private int speedY;
    private int radius;

    public Ball(Point2D center, int radiusA, Color inner, Color border){
        this.center = center;
        this.radius = radiusA;

        up = new Point2D(0,0);
        down = new Point2D(0,0);
        left = new Point2D(0,0);
        right = new Point2D(0,0);

        ballFace = makeBall(center, radiusA, radiusA);

        this.border = border;
        this.inner = inner;

        speedX = 0;
        speedY = 0;
    }

    protected abstract Circle makeBall(Point2D center, int radiusA, int radiusB);

    public Circle createCircle(){
        Circle tmp = (Circle) ballFace;
        double radius = tmp.getRadius();

        tmp.setCenterX(getUpperLeftX());
        tmp.setCenterY(getUpperLeftY());
        tmp.setRadius(radius);

        return tmp;
    }

    public void move(){
        center = new Point2D(center.getX() + speedX, center.getY() + speedY);
        Circle temp = createCircle();
        ballFace = temp;
        setPoints(temp.getRadius(), temp.getRadius());
    }

    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
        speedY = s;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

    public void moveTo(Point2D p){
        center = p;
        ballFace = createCircle();
    }

    private void setPoints(double width,double height){
        up = new Point2D(center.getX(), center.getY() - (height/2));
        down = new Point2D(center.getX(), center.getY() + (height/2));
        left = new Point2D(center.getX() - (width/2), center.getY());
        right = new Point2D(center.getX() + (width/2), center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }

    public int getRadius() {
        return radius;
    }

    public double getUpperLeftX() {
        return center.getX() - (radius/2);
    }

    public double getUpperLeftY() {
        return center.getY() - (radius/2);
    }
}

