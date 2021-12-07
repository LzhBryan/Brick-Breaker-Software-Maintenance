package BrickDestroy;

import javafx.geometry.Point2D;

public class BallFactory {

    public Ball makeBall(String ballType,Point2D ballPos) {
        if (ballType == null)
            return null;

        if (ballType.equalsIgnoreCase("Rubber Ball"))
            return new RubberBall(ballPos);

        return null;
    }
}
