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

    private GameLogicControl gameLogic;

    public GameLevels(GameLogicControl gameLogic){
        this.gameLogic = gameLogic;
    }

    private Brick[] makeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, String brickTypeA, String brickTypeB, boolean isSingleType){
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

        int centerLeft = brickOnLine / 2 - 1;  //4
        int centerRight = brickOnLine / 2 + 1; //6

        brickCnt += lineCnt / 2;

        // Initialize brick array with the size of 30
        Brick[] tmp  = new Brick[brickCnt];
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            // loop count divide by bricks in a row (10)
            if(line == lineCnt) // if reach 31st brick (i=30), break
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            // x position of the current brick in a row

            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            // if reaches 11th brick (i=10), halve the size of brick

            double y = (line) * brickHgt;
            // get y coordinates of brick rows

            p.setLocation(x,y);
            // set location coordinates

            // make brick according to type of bricks
            if(isSingleType){
                tmp[i] = gameLogic.makeBrick(p,brickSize,brickTypeA);
            }
            else{
                // line % 2 == 0 proc when its second row of bricks
                // i % 2 == 0 even number of bricks
                // line % 2 != 0 proc when its first and third row of bricks
                // posX > centerLeft && posX <= centerRight means brick 5-6
                boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
                tmp[i] = b ?  gameLogic.makeBrick(p,brickSize,brickTypeA) : gameLogic.makeBrick(p,brickSize,brickTypeB);
                // first condition: even rows of bricks will alternate the colors of bricks,
                // second condition is the odd rows of bricks, brick 15 and 16 will make second condition turn true
            }
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            if(isSingleType){
                tmp[i] = new ClayBrick(p,brickSize);
            }
            else{
                tmp[i] = gameLogic.makeBrick(p, brickSize, brickTypeA);
            }
            // Since i terminated with the value of 30, i continues to run once and terminate
            // brick in second row lacks another halve size of brick
            // so this for loop adds another brick into the last brick of every even rows
        }
        return tmp;
        // must return full size of tmp
    }

    public Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"CLAY", null, true);
        tmp[1] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"CLAY","CEMENT", false);
        tmp[2] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"CLAY","STEEL", false);
        tmp[3] = makeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,"STEEL","CEMENT", false);
        return tmp;
    }
}
