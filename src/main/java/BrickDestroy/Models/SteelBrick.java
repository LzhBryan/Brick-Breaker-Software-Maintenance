package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

/**
 * A concrete Steel Brick class that inherits the methods and behaviours from the abstract Brick class.
 */
public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = Color.rgb(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    /**
     * Constructor that calls the super constructor to initialize the brick data.
     * @param point the position of the brick.
     * @param size the size of the brick.
     */
    public SteelBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
    }

    /**
     * An overriding method to generate the brick shape.
     * @param pos The position of the brick.
     * @param size The size of the brick.
     * @return a rectangle with defined attributes to represent the brick shape.
     */
    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    /**
     * An overriding getter method to return brick shape.
     * @return brick shape.
     */
    @Override
    public Shape getBrick() {
        return super.getBrickFace();
    }

    /**
     * An overriding getter method to return crack path if there exist such path.
     * @return crack path.
     */
    @Override
    public Path getPath() {
        return null;
    }

    /**
     * A method to break the brick when ball has collided to it if the brick is not broken.
     * @return the boolean value that indicates brick broken status.
     */
    public boolean setImpact(){
        if(!super.isBroken())
            return false;
        impact();
        return !super.isBroken();
    }

    /**
     * A method to measure if the new random number generated against a constant probability to determine whether the brick breaks or not.
     */
    public void impact(){
        Random rnd = new Random();
        if(rnd.nextDouble() < STEEL_PROBABILITY)
            super.impact();
    }
}
