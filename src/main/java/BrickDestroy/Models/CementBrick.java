package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * A concrete Cement Brick class that inherits the methods and behaviours from the abstract Brick class.
 */
public class CementBrick extends Brick {

    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = Color.rgb(147, 147, 147);
    private static final Color DEF_BORDER = Color.rgb(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;
    private Path crackPath;

    /**
     * Constructor that calls the super constructor to initialize the brick data.
     * @param point the position of the brick.
     * @param size the size of the brick.
     */
    public CementBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);
        brickFace = makeBrickFace(point, size);
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
     * An overriding method to set impact and crack the brick.
     * @param point the position of the brick.
     * @param dir the direction of the collision impact.
     * @return false if brick is broken, else return true.
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(!super.isBroken())
            return false;
        super.impact();
        if(super.isBroken()){
            crack.makeCrack(point, dir, brickFace);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * An overriding getter method to return brick shape.
     * @return brick shape.
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * An overriding getter method to return crack path if there exist such path.
     * @return crack path.
     */
    @Override
    public Path getPath() {
        return crackPath;
    }

    /**
     * A method to obtain the cracked path values.
     */
    private void updateBrick(){
        if(super.isBroken())
            crackPath = crack.draw();
    }

    /**
     * A method to reset the health state of the brick.
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }
}
