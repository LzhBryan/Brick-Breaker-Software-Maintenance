package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

/**
 * An abstract model class that contains the data and behaviour of all bricks in the game.
 */
public abstract class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private final Shape brickFace;
    private final String brickName;
    private final Color borderColor;
    private final Color innerColor;
    private final int fullStrength;
    private int strength;
    private boolean brickBroken;
    private final Point2D brickPosition;
    private final Dimension2D brickSize;

    /**
     * A constructor to initialize the brick data.
     * @param name The name of the brick.
     * @param position The position of the brick when spawned in game.
     * @param size The size of brick.
     * @param border The border color of the brick.
     * @param inner The inner color of the brick.
     * @param strength How many times a ball needs to collide with the break in order to break it.
     */
    public Brick(String name, Point2D position, Dimension2D size, Color border, Color inner, int strength){
        brickBroken = false;
        this.brickName = name;
        brickFace = makeBrickFace(position, size);
        this.borderColor = border;
        this.innerColor = inner;
        this.fullStrength = this.strength = strength;
        this.brickPosition = position;
        this.brickSize = size;
    }

    /**
     * An abstract method to generate the brick shape.
     * @param pos The position of the brick.
     * @param size The size of the brick.
     */
    protected abstract Shape makeBrickFace(Point2D pos, Dimension2D size);

    /**
     * A method to break the brick when ball has collided to it if the brick is not broken.
     * @return the boolean value that indicates brick broken status.
     */
    public boolean setImpact(){
        if(brickBroken)
            return false;
        impact();
        return brickBroken;
    }

    /**
     * A method to break the brick when ball has collided to it if the brick is not broken.
     * @param position the position where the ball is in contact with the brick.
     * @param dir the direction of the collision impact.
     * @return the boolean value that indicates brick broken status.
     */
    public boolean setImpact(Point2D position , int dir){
        if(brickBroken)
            return false;
        impact();
        return brickBroken;
    }

    /**
     * Abstract method to return brick of shape type.
     */
    public abstract Shape getBrick();

    /**
     * Abstract method to return the path.
     */
    public abstract Path getPath();

    /**
     * A getter method to return border color of the brick.
     * @return border color of brick.
     */
    public Color getBorderColor(){
        return borderColor;
    }

    /**
     * A getter method to return inner color of the brick.
     * @return inner color of brick.
     */
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * A method to determine the collision impact of the ball when it collides with the brick.
     * @param ball The ball object.
     * @return the impact value.
     */
    public final int findImpact(Ball ball){
        if(brickBroken)
            return 0;

        int out  = 0;
        if(brickFace.contains(ball.getRight()))
            out = LEFT_IMPACT;

        else if(brickFace.contains(ball.getLeft()))
            out = RIGHT_IMPACT;

        else if(brickFace.contains(ball.getUp()))
            out = DOWN_IMPACT;

        else if(brickFace.contains(ball.getDown()))
            out = UP_IMPACT;

        return out;
    }

    /**
     * A getter method to return the inverted broken status of the brick.
     * @return the inverted broken status of the brick.
     */
    public final boolean isBroken(){
        return !brickBroken;
    }

    /**
     * A method to restore the brick to its original state.
     */
    public void repair() {
        brickBroken = false;
        strength = fullStrength;
    }

    /**
     * A method to reduce the health points of the brick by 1.
     */
    public void impact(){
        strength--;
        brickBroken = (strength == 0);
    }

    /**
     * A getter method to return the position of the brick.
     * @return position of the brick.
     */
    public Point2D getPos(){
        return brickPosition;
    }

    /**
     * A getter method to return the width of the brick.
     * @return width of the brick.
     */
    public double getBrickWidth(){
        return brickSize.getWidth();
    }

    /**
     * A getter method to return the height of the brick.
     * @return height of the brick.
     */
    public double getBrickHeight(){
        return brickSize.getHeight();
    }

    /**
     * A getter method to return the brick face.
     * @return brick face.
     */
    public Shape getBrickFace() {
        return brickFace;
    }

    /**
     * A getter method to return the name of the brick.
     * @return name of the brick.
     */
    public String getBrickName() {
        return brickName;
    }
}
