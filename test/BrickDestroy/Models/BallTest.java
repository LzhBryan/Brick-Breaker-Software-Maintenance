package BrickDestroy.Models;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BallTest {

    Ball ball = new RubberBall(new Point2D(300,430));

    @Test
    void reverseX() {
        ball.setXSpeed(10);
        ball.reverseX();
        Assertions.assertEquals(-10, ball.getSpeedX());
    }

    @Test
    void reverseY() {
        ball.setYSpeed(10);
        ball.reverseY();
        Assertions.assertEquals(-10, ball.getSpeedY());
    }
}