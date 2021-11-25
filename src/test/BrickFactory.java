package test;

import java.awt.*;

public class BrickFactory {

    public Brick getBricks(String brickType, Dimension size, Point point){
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
