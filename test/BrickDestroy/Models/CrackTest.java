package BrickDestroy.Models;

import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CrackTest {
    Brick cementBrick = new CementBrick(new Point2D(300, 430), new Dimension2D(60, 20));
    Path path = new Path();
    Crack crack = new Crack(1,35);

    /**
     * A test to check whether the draw method has added path elements into the crack.
     */
    @Test
    void draw() {
        makeCrack();
        assertNotNull(crack);
    }

    /**
     * A test to check if the reset method will clear all the path elements.
     */
    @Test
    void reset() {
        crack.makeCrack(new Point2D(300, 430), 10, cementBrick.getBrickFace());
        makeCrack();
        crack.reset();
        assertTrue(path.getElements().isEmpty());
    }

    /**
     * A test to check if the path elements is within the brick boundary.
     */
    @Test
    void makeCrack() {
        crack.makeCrack(new Point2D(300, 430), 10, cementBrick.getBrickFace());
        Bounds cement = cementBrick.getBrickFace().getBoundsInLocal();
        path = crack.draw();
        String str = path.getElements().get(0).toString();
        String value = str.replaceAll("[a-zA-z]", "").replaceAll("=", "");
        String[] str1 = value.split(", ");
        double x = Double.parseDouble(str1[0]);
        assertTrue(x <= cement.getMaxX());
    }
}