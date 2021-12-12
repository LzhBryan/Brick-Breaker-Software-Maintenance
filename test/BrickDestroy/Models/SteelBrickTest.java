package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    Brick steelBrick = new SteelBrick(new Point2D(0,0), new Dimension2D(60, 20));
    Brick cementBrick = new CementBrick(new Point2D(0,0), new Dimension2D(60, 20));

    /**
     * A test to check whether the brick face of steel brick is null or not.
     */
    @Test
    void makeBrickFace() {
        assertNotNull(steelBrick.getBrickFace());
    }


    /**
     * A test to check the crack path of steel brick is null or not.
     */
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

    /**
     * A test to check the cement brick broken status.
     */
    @Test
    void isBroken() {
        cementBrick.impact();
        assertTrue(cementBrick.setImpact());
    }

    /**
     * A test to check whether the width of cement brick is returned correctly.
     */
    @Test
    void getBrickWidth() {
        assertEquals(60 ,cementBrick.getBrickWidth());
    }

    /**
     * A test to check whether the height of cement brick is returned correctly.
     */
    @Test
    void getBrickHeight() {
        assertEquals(20, cementBrick.getBrickHeight());
    }

    /**
     * A test to check whether the repair of brick after setting impact.
     */
    @Test
    void repair() {
        cementBrick.impact();
        cementBrick.repair();
        assertFalse(cementBrick.setImpact());
    }
}