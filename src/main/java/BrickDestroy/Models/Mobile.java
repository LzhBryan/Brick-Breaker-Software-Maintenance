package BrickDestroy.Models;

import javafx.geometry.Point2D;

/**
 * An interface that has two moving methods.
 */
public interface Mobile {
    /**
     * Add velocity and update the position of the (x,y) coordinates of the object
     */
    void move();

    /**
     * Update the position of the object.
     * @param point the position of the object.
     */
    void moveTo(Point2D point);
}
