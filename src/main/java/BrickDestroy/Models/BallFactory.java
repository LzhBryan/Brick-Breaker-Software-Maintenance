package BrickDestroy.Models;

import javafx.geometry.Point2D;

/**
 * A class make different kinds of ball and return it to the caller method.
 */
public class BallFactory {

    /**
     * A method to return the type of ball object depending on the parameters value.
     * @param ballType the type/name of ball to be instantiated.
     * @param ballPos the initial position to be defined when a ball is created.
     * @return the corresponding ball object.
     */
    public Ball makeBall(String ballType, Point2D ballPos) {
        if (ballType == null)
            return null;

        if (ballType.equalsIgnoreCase("Rubber Ball"))
            return new RubberBall(ballPos);

        return null;
    }
}
