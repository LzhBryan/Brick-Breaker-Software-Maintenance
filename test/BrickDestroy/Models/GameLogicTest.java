package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {

    GameLogic gameLogic = new GameLogic(new Rectangle(0,0,
            GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT),
            30,3,
            (double) 6/2, new Point2D(300,430));

    /**
     * A test to check the status of ball collision when it hits the left border of the screen.
     */
    @Test
    void collideBorder() {
        gameLogic.getBall().moveTo(new Point2D(0, 200));
        gameLogic.setBallXSpeed(-10);
        gameLogic.setBallYSpeed(10);
        gameLogic.startMovement();
        assertTrue(gameLogic.collideBorder());
    }

    /**
     * A test to move the ball to the bottom border of the screen and check the status of ball touching the bottom border of the screen.
     */
    @Test
    void ballFalls() {
        gameLogic.getBall().moveTo(new Point2D(40,450));
        gameLogic.getBall().setSpeed(10, 10);
        gameLogic.getBall().move();
        assertTrue(gameLogic.ballFalls());
    }

    /**
     * A test to move the ball to the top border of the screen and check the status of ball touching the top border of the screen.
     */
    @Test
    void collideTopBorder() {
        gameLogic.getBall().moveTo(new Point2D(300, 0));
        gameLogic.getBall().setSpeed(10, -10);
        gameLogic.getBall().move();
        assertTrue(gameLogic.collideTopBorder());
    }

    /**
     * A test to check if the reset function will move player and the ball to original location
     * as well as updating the ball status.
     */
    @Test
    void reset() {
        gameLogic.reset();
        double x = gameLogic.getPlayer().getUpperLeftX() + (double)gameLogic.getPlayer().getWidth()/2;
        double y = gameLogic.getPlayer().getUpperLeftY() + (double)gameLogic.getPlayer().getHeight()/2;
        assertAll(
                () -> assertEquals(new Point2D(300,430), gameLogic.getBall().getPosition()),
                () -> assertEquals(new Point2D(300,435), new Point2D(x,y)),
                () -> assertFalse(gameLogic.isBallLost())

        );
    }

    /**
     * A test to check the ball end method should return true when ball count is 0.
     */
    @Test
    void ballEnd() {
        gameLogic.setBallCount(0);
        assertTrue(gameLogic.ballEnd());
    }

    /**
     * A test to check the reset ball count should reset the ball count back to 3.
     */
    @Test
    void resetBallCount() {
        gameLogic.resetBallCount();
        assertEquals(3, gameLogic.getBallCount());
    }

    /**
     * A test to check the brick object has been instantiated and returned correctly.
     */
    @Test
    void makeBrick() {
        Brick brick = gameLogic.makeBrick(new Point2D(0,0), new Dimension2D(60, 20), "CLAY");
        assertNotNull(brick);
    }

    /**
     * A test to check the range of the randomized speed of the ball.
     */
    @Test
    void randomizeSpeed() {
        int speed = gameLogic.randomizeSpeed(5, -3);
        assertTrue(speed < 2);
    }

    /**
     * A test to check if a clay brick is hit, player score should increment by 1.
     */
    @Test
    void brickScore() {
        int score = gameLogic.brickScore(new ClayBrick(new Point2D(0,0), new Dimension2D(60,20)));
        assertEquals(1, score);
    }

    /**
     * A test to set up the brick wall, reset the wall and check the ball count.
     */
    @Test
    void wallReset() {
        gameLogic.nextLevel();
        gameLogic.wallReset();
        assertEquals(3, gameLogic.getBallCount());
    }
}