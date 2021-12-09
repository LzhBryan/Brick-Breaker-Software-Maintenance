package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A concrete Rubber ball class that inherits the methods and behaviours from the abstract Ball class.
 */
public class RubberBall extends Ball {
    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = Color.rgb(255, 219, 88);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();

    /**
     * Constructor that calls the super constructor to initialize the ball data.
     * @param center The center position of the ball to be spawned in the game.
     */
    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }

    /**
     * An overriding method that creates the ball appearance based on the parameters.
     * @param center The center point of the ball to be spawned in the game in (x,y) coordinates format.
     * @param radius The radius of the ball.
     * @return a circle object that has the (x,y) coordinates and the radius being defined.
     */
    @Override
    protected Circle makeBall(Point2D center, int radius) {
        double x = center.getX() - (double) (radius / 2);
        double y = center.getY() - (double) (radius / 2);

        return new Circle(x,y,radius);
    }
}
