package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * A class that create different types of bricks according to the parameters received.
 */
public class BrickFactory {

    /**
     * A method to instantiate the corresponding brick object.
     * @param brickType the name of the brick.
     * @param size the size of the brick.
     * @param position the position of the brick.
     * @return the corresponding brick object.
     */
    public Brick getBricks(String brickType, Dimension2D size, Point2D position){

        if(brickType == null)
            return null;

        if(brickType.equalsIgnoreCase("CLAY"))
            return new ClayBrick(position, size);

        else if(brickType.equalsIgnoreCase("CEMENT"))
            return new CementBrick(position, size);

        else if(brickType.equalsIgnoreCase("STEEL"))
            return new SteelBrick(position, size);

        else if(brickType.equalsIgnoreCase("METAL"))
            return new MetalBrick(position, size);

        return null;
    }
}
