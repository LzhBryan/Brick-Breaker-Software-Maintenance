package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    Player player = new Player(new Point2D(300,430), 150,
            10,new Rectangle(0,0, 600, 450));

    @Test
    void moveLeft() {
        player.moveLeft();
        assertEquals(-5, player.getMoveAmount());
    }

    @Test
    void moveRight() {
        player.moveRight();
        assertEquals(5, player.getMoveAmount());
    }

    @Test
    void stop() {
        player.stop();
        assertEquals(0, player.getMoveAmount());
    }

    @Test
    void getUpperLeftX() {
        player.moveTo(new Point2D(150, 430));
        assertEquals(75, player.getUpperLeftX());
    }

    @Test
    void getUpperLeftY() {
        assertEquals(430, player.getUpperLeftY());
    }

    @Test
    void getWidth() {
        assertEquals(150, player.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(10, player.getHeight());
    }
}