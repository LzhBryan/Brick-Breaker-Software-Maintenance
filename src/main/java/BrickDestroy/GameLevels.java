package BrickDestroy;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class GameLevels {
    private static final int LEVELS_COUNT = 4;

    private GameLogicControl gameLogic;
    private Rectangle drawArea;
    private int brickCount;
    private int lineCount;
    private double brickSizeRatio;

    public GameLevels(GameLogicControl gameLogic, Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio ){
        this.gameLogic = gameLogic;
        this.drawArea = drawArea;
        this.brickCount = brickCnt;
        this.lineCount = lineCnt;
        this.brickSizeRatio = brickSizeRatio;
    }

    public Brick[][] makeLevels(){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        for(int i=0; i<LEVELS_COUNT; i++)
            tmp[i] = buildBricks(i);
        return tmp;
    }

    public Brick[] buildBricks(int level) {

        brickCount -= brickCount % lineCount;
        int brickOnLine = brickCount / lineCount;
        double brickLength = drawArea.getWidth() / brickOnLine;
        double brickHeight = brickLength / brickSizeRatio;

        int centerLeft = brickOnLine / 2 - 1;  //4
        int centerRight = brickOnLine / 2 + 1; //6

        Dimension2D brickSize = new Dimension2D((int) brickLength, (int) brickHeight);
        Point2D p;

        brickCount += lineCount / 2;
        Brick[] bricks  = new Brick[brickCount];
        String brickTypes[] = checkBrickType(level);

        int i;
        for (i = 0; i < bricks.length; i++) {
            int line = i / brickOnLine;

            if (line == lineCount)
                break;

            int posX = i % brickOnLine;
            double x = posX * brickLength;

            x = (line % 2 == 0) ? x : (x - (brickLength / 2));

            double y = (line) * brickHeight;

            p = new Point2D(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));

            bricks[i] = b ? gameLogic.makeBrick(p, brickSize, brickTypes[0]) : gameLogic.makeBrick(p, brickSize, brickTypes[1]);
        }

        for(double y = brickHeight;i < bricks.length;i++, y += 2*brickHeight){
            double x = (brickOnLine * brickLength) - (brickLength / 2);
            p = new Point2D(x,y);
            bricks[i] = gameLogic.makeBrick(p, brickSize, brickTypes[0]);
        }

        return bricks;
    }

    public String[] checkBrickType(int level){
        if (level == 0)
            return new String[]{"CLAY", "CLAY"};

        else if (level == 1)
            return new String[]{"CLAY", "CEMENT"};

        else if (level == 2)
            return new String[]{"CLAY", "STEEL"};

        else if (level == 3)
            return new String[]{"STEEL", "CEMENT"};

        return null;
    }
}
