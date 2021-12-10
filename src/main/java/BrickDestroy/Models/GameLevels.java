package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

/**
 * A class that handles all the levels making in the game.
 */
public class GameLevels {
    public static final int LEVELS_COUNT = 6;

    private final GameLogic gameLogic;
    private final Rectangle drawArea;
    private final int brickCount;
    private final int lineCount;
    private final double brickSizeRatio;
    private final Brick[][] levels;

    /**
     * A constructor to initialize the data and call method to start making levels in the game.
     * @param gameLogic GameLogic object which contains all the entities logics and interactions in the game.
     * @param drawArea this represents the game board screen dimension.
     * @param brickCnt how many bricks to be built.
     * @param lineCnt how many rows of bricks to be built.
     * @param brickSizeRatio the ratio of the brick size.
     */
    public GameLevels(GameLogic gameLogic, Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio ){
        this.gameLogic = gameLogic;
        this.drawArea = drawArea;
        this.brickCount = brickCnt;
        this.lineCount = lineCnt;
        this.brickSizeRatio = brickSizeRatio;
        levels = makeLevels();
    }

    /**
     * A method to build all the levels and store into a two-dimensional array.
     * @return the two-dimensional array.
     */
    public Brick[][] makeLevels(){
        Brick[][] levels = new Brick[LEVELS_COUNT][];
        for(int i=0; i<LEVELS_COUNT; i++)
            levels[i] = buildBricks(i);
        return levels;
    }

    /**
     * A method to build a particular level of brick walls in the game.
     * @param level the level to determine what kind of bricks needed to build the brick wall.
     * @return an array that contains the entire brick wall.
     */
    public Brick[] buildBricks(int level) {

        int brickCount = this.brickCount;
        brickCount -= brickCount % lineCount;
        int brickOnLine = brickCount / lineCount;
        double brickLength = drawArea.getWidth() / brickOnLine;
        double brickHeight = brickLength / brickSizeRatio;
        int centerLeft = brickOnLine / 2 - 1; //4
        int centerRight = brickOnLine / 2 + 1; //6

        Dimension2D brickSize = new Dimension2D((int) brickLength, (int) brickHeight);
        Point2D brickPosition;

        brickCount += lineCount / 2;
        Brick[] bricks  = new Brick[brickCount];
        String[] brickTypes = checkBrickType(level);

        int i;
        for (i = 0; i < bricks.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCount)
                break;

            int posX = i % brickOnLine;
            double x = posX * brickLength;
            x = (line % 2 == 0) ? x : (x - (brickLength / 2));
            double y = (line) * brickHeight;

            brickPosition = new Point2D(x, y);
            boolean changeBrick = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            bricks[i] = changeBrick ? gameLogic.makeBrick(brickPosition, brickSize, brickTypes[0]) : gameLogic.makeBrick(brickPosition, brickSize, brickTypes[1]);
        }

        for(double y = brickHeight;i < bricks.length;i++, y += 2*brickHeight){
            double x = (brickOnLine * brickLength) - (brickLength / 2);
            brickPosition = new Point2D(x,y);
            bricks[i] = gameLogic.makeBrick(brickPosition, brickSize, brickTypes[0]);
        }

        return bricks;
    }

    /**
     * A method to determine what types of bricks to be built depending on the level.
     * @param level the level to be built.
     * @return a string array that contains the type of bricks to be built for that particular level.
     */
    public String[] checkBrickType(int level){
        if (level == 0)
            return new String[]{"CLAY", "CLAY"};

        else if (level == 1)
            return new String[]{"CLAY", "CEMENT"};

        else if (level == 2)
            return new String[]{"CLAY", "STEEL"};

        else if (level == 3)
            return new String[]{"STEEL", "CEMENT"};

        else if (level == 4)
            return new String[]{"STEEL", "STEEL"};

        else if (level == 5)
            return new String[]{"CEMENT", "METAL"};

        return null;
    }

    /**
     * A getter method to obtain the two-dimensional bricks array that contains all the walls in every level.
     * @return two-dimensional bricks array that contains all the walls in every level.
     */
    public Brick[][] getLevels() {
        return levels;
    }
}
