package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    Player player = new Player(new Point2D(300,430), 150,
            10,new Rectangle(0,0, 600, 450));

    /**
     * A test to check if player moving left will update the velocity or not.
     */
    @Test
    void moveLeft() {
        player.moveLeft();
        assertEquals(-5, player.getMoveAmount());
    }

    /**
     * A test to check if player moving right will update the velocity or not.
     */
    @Test
    void moveRight() {
        player.moveRight();
        assertEquals(5, player.getMoveAmount());
    }

    /**
     * A test to check if stopping the player will set velocity to 0 or not.
     */
    @Test
    void stop() {
        player.stop();
        assertEquals(0, player.getMoveAmount());
    }

    /**
     * A test to check if the upper left x of player is returned correctly.
     */
    @Test
    void getUpperLeftX() {
        player.moveTo(new Point2D(150, 430));
        assertEquals(75, player.getUpperLeftX());
    }

    /**
     * A test to check whether the upper left y of player is returned correctly.
     */
    @Test
    void getUpperLeftY() {
        assertEquals(430, player.getUpperLeftY());
    }

    /**
     * A test to check if the width of player is returned correctly.
     */
    @Test
    void getWidth() {
        assertEquals(150, player.getWidth());
    }

    /**
     * A test to check if the height of player is returned correctly.
     */
    @Test
    void getHeight() {
        assertEquals(10, player.getHeight());
    }
}