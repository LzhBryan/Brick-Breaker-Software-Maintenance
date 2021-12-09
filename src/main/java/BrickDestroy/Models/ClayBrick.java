package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * A concrete Clay Brick class that inherits the methods and behaviours from the abstract Brick class.
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = Color.rgb(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    /**
     * Constructor that calls the super constructor to initialize the brick data.
     * @param point the position of the brick.
     * @param size the size of the brick.
     */
    public ClayBrick(Point2D point, Dimension2D size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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
}
