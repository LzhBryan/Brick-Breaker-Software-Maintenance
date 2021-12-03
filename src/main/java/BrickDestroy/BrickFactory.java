package BrickDestroy;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class BrickFactory {

    public Brick getBricks(String brickType, Dimension2D size, Point2D point){
        if(brickType == null){
            return null;
        }

        if(brickType.equalsIgnoreCase("CLAY")){
            return new ClayBrick(point, size);
        }

        else if(brickType.equalsIgnoreCase("CEMENT")){
            return new CementBrick(point, size);
        }

        else if(brickType.equalsIgnoreCase("STEEL")){
            return new SteelBrick(point, size);
        }

        return null;
    }
}
