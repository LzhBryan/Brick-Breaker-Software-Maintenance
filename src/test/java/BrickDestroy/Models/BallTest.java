package BrickDestroy.Models;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    Ball ball = new RubberBall(new Point2D(300,430));

    /**
     * A test to check the reverse horizontal velocity of ball is working as intended.
     */
    @Test
    void reverseX() {
        ball.setXSpeed(10);
        ball.reverseX();
        assertEquals(-10, ball.getSpeedX());
    }

    /**
     * A test to check the reverse vertical velocity of ball is working as intended.
     */
    @Test
    void reverseY() {
        ball.setYSpeed(10);
        ball.reverseY();
        assertEquals(-10, ball.getSpeedY());
    }
}