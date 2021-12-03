package BrickDestroy;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player {
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;
    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point2D ballPoint;
    private int moveAmount;
    private int min;
    private int max;
    private int height;
    private int width;

    public Player(Point2D ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = (int) container.getX() + (width / 2);
        max = min + (int) container.getWidth() - width;
        this.width = width;
        this.height = height;
    }

    private Rectangle makeRectangle(int width,int height){
        Point2D p = new Point2D((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return new Rectangle(p.getX(), p.getY(), width, height);
    }

    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;

        ballPoint = new Point2D(x, ballPoint.getY());
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    public void moveTo(Point2D p){
        ballPoint = p;
        playerFace.setX(ballPoint.getX() - (int)playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    public double getUpperLeftX(){
        return playerFace.getX();
    }

    public double getUpperLeftY(){
        return playerFace.getY();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
