package BrickDestroy.Models;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLevelsTest {

    private final GameLogic gameLogic = new GameLogic(new Rectangle(0,0,
            GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT),
            30,3,
            (double) 6/2, new Point2D(300,430));
    GameLevels gameLevels = new GameLevels(gameLogic, new Rectangle(0,0,
            GameBoardModel.DEF_WIDTH, GameBoardModel.DEF_HEIGHT),30, 3, 3);

    /**
     * A test to check if the method buildBricks return null or not.
     */
    @Test
    void buildBricks() {
        Brick[] brick = gameLevels.buildBricks(1);
        assertNotNull(brick);
    }

    /**
     * A test to check if the first level brick type only contains clay brick.
     */
    @Test
    void checkBrickType() {
        String[] type = gameLevels.checkBrickType(0);
        assertAll(
                () -> assertEquals("CLAY", type[0]),
                () -> assertEquals("CLAY", type[1])
        );
    }

    /**
     * A test to check if the two-dimensional array contains bricks or not.
     */
    @Test
    void getLevels() {
        Brick[][] bricks = gameLevels.makeLevels();
        assertNotNull(bricks);
    }

    /**
     * A test to check if the starting brick position drawn on the screen is at the top left of the screen.
     */
    @Test
    void brickStartingPosition(){
        Brick[][] bricks = gameLevels.makeLevels();
        assertEquals(0, bricks[0][0].getPos().getX());
    }
}