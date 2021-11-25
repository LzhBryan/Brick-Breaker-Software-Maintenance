/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;

public class GameLevels {
    private static final int LEVELS_COUNT = 4;

//    private static final int CLAY = 1;
//    private static final int STEEL = 2;
//    private static final int CEMENT = 3;

    Brick[] bricks;
    GameLogicControl gameLogic;
    private Brick[][] levels;
    private int level, brickCount;

    public GameLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, GameLogicControl gameLogic){
        this.gameLogic = gameLogic;
        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, String brickType){

         //if brickCount is not divisible by line count,brickCount is adjusted to the biggest
         // multiple of lineCount smaller then brickCount

        //Parameters:
        //drawArea = new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT)
        //brickCnt = 30
        //lineCnt = 3
        //brickSizeRatio = 6/2
        //type = new Point(300,430)

        brickCnt -= brickCnt % lineCnt;
        // 30 -= 30 % 3 which remains
        int brickOnLine = brickCnt / lineCnt;
        // Bricks in a row
        double brickLen = drawArea.getWidth() / brickOnLine;
        // A single Brick length
        double brickHgt = brickLen / brickSizeRatio;
        // A single Brick Height

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];
        // Initialize brick array with the size of 30
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            // loop count divide by bricks in a row (10)
            if(line == lineCnt) // if reach 30 bricks, break
                break;
            double x = (i % brickOnLine) * brickLen;
            // x position of the current brick in a row
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            // if brick count reaches 10, halve the size of brick
            double y = (line) * brickHgt;
            // get y coordinates of brick rows
            p.setLocation(x,y);
            // set location coordinates
            tmp[i] = makeBrick(p,brickSize,brickType);
            // make brick according to type of bricks
            //System.out.println(i);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
            // Since i terminated with the value of 30, i continues to run once and terminate
            // brick in second row lacks another halve size of brick
            // so this for loop adds another brick into the last brick of every even rows
        }
        return tmp;
        // must return full size of tmp
    }

    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, String brickTypeA, String brickTypeB){

          //if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          //multiple of lineCount smaller then brickCount

        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;  //4
        int centerRight = brickOnLine / 2 + 1; //6

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            // line % 2 == 0 proc when its second row of bricks
            // i % 2 == 0 even number of bricks
            // line % 2 != 0 proc when its first and third row of bricks
            // posX > centerLeft && posX <= centerRight means brick 5-6
            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,brickTypeA) : makeBrick(p,brickSize,brickTypeB);
            // first condition: even rows of bricks will alternate the colors of bricks,
            // second condition is the odd rows of bricks, brick 15 and 16 will make second condition turn true
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,brickTypeA);
        }
        return tmp;
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"CLAY");
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"CLAY","CEMENT");
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"CLAY","STEEL");
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"STEEL","CEMENT");
        return tmp;
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public int getBrickCount(){
        return brickCount;
    }

    public void ReduceBrickCount() {
        this.brickCount--;
    }

    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        gameLogic.setBallCount(3);
    }

    public Brick makeBrick(Point point, Dimension size, String type){
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBricks(type, size, point);
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel(){
        return level < levels.length;
    }

}
