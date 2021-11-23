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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JComponent implements KeyListener{
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;
    private GameLevels gameLevels;
    private GameLogicControl gameLogic;
    private String message;
    private DebugConsole debugConsole;
    private PauseMenu pauseMenu;

    public GameBoard(JFrame owner){
        super();

        this.initialize();
        message = "";
        gameLogic = new GameLogicControl(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),new Point(300,430));
        gameLevels = new GameLevels(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2, gameLogic);
        debugConsole = new DebugConsole(owner, gameLevels,this, gameLogic);
        pauseMenu = new PauseMenu(this, gameLevels, gameLogic);

        //initialize the first level
        gameLevels.nextLevel();

        // refresh frame per 10 milliseconds
        gameTimer = new Timer(10,e ->{
            gameLogic.move();    // start moving ball and player
            gameLogic.findImpacts(gameLevels); // start detecting ball collision with wall
            message = String.format("Bricks: %d Balls %d", gameLevels.getBrickCount(),gameLogic.getBallCount());

            if(gameLogic.isBallLost()){
                if(gameLogic.ballEnd()){
                    gameLevels.wallReset();
                    message = "Game over";
                }
                gameLogic.ballReset();
                gameTimer.stop();
            }
            else if(gameLevels.isDone()){
                if(gameLevels.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    gameLogic.ballReset();
                    gameLevels.wallReset();
                    gameLevels.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                }
            }
            repaint();
        });

    }

    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }

    @Override
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);
        // set game screen background and text color
        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);

        drawBall(gameLogic.ball,g2d);

        for(Brick b : gameLevels.bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(gameLogic.player,g2d);

        pauseMenu.displayMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());
        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());
        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();
        Shape s = ball.getBallFace();
        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);
        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);
        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();
        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);
        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);
        g2d.setColor(tmp);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                gameLogic.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                gameLogic.player.movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                pauseMenu.setShowPauseMenu(!pauseMenu.isShowPauseMenu());
                //showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!(pauseMenu.isShowPauseMenu()))
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                gameLogic.player.stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        gameLogic.player.stop();
    }

    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}
