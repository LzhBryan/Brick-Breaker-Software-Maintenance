package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    Brick steelBrick = new SteelBrick(new Point2D(0,0), new Dimension2D(60, 20));

    @Test
    void makeBrickFace() {
        assertNotNull(steelBrick.getBrickFace());
    }


    @Test
    void getPath() {
        assertNull(steelBrick.getPath());
    }

    /**
     * A test to check if the probability of setting impact on steel brick is working as intended
     * as it should only have 40% chance to break the brick with 50 repetitions.
     */
    @RepeatedTest(50)
    void impact() {
        assertTrue(steelBrick.setImpact());
    }
}