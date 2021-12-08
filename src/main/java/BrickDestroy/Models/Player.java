package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player implements Mobile {
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;
    private static final int DEF_MOVE_AMOUNT = 10; // it was 5

    private final Rectangle playerFace;
    private Point2D ballPoint;
    private final int min;
    private final int max;
    private final int height;
    private final int width;
    private int moveAmount;

    public Player(Point2D ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = (int) container.getX() + (width / 2);
        max = min + (int) container.getWidth() - width;
        this.width = width;
        this.height = height;
    }

    private Rectangle makeRectangle(int width,int height){
        Point2D playerPosition = new Point2D((int)(ballPoint.getX() - (width / 2)), (int)ballPoint.getY());
        return new Rectangle(playerPosition.getX(), playerPosition.getY(), width, height);
    }

    // player controller
    public boolean collideBall(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;
    }

    @Override
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint = new Point2D(x, ballPoint.getY());
        playerFace.setX(ballPoint.getX() - playerFace.getWidth()/2);
        playerFace.setY(ballPoint.getY());
    }

    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop(){
        moveAmount = 0;
    }

    @Override
    public void moveTo(Point2D position){
        ballPoint = position;
        playerFace.setX(ballPoint.getX() - playerFace.getWidth() /2);
        playerFace.setY(ballPoint.getY());
    }

    // player controller ends
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
