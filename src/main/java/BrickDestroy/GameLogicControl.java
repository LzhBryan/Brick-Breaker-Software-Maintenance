package BrickDestroy;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class GameLogicControl {
    private final Random rnd;
    private final Rectangle area;
    private final Player player;
    private final Point2D startPoint;
    private final GameLevels gameLevels;
    private Ball ball;
    private Brick[] bricks;
    private boolean ballLost;
    private int brickCount;
    private int ballCount;
    private int level;

    public GameLogicControl(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point2D ballPos){
        startPoint = new Point2D(ballPos.getX(), ballPos.getY());
        ballCount = 3;
        ballLost = false;
        rnd = new Random();

        makeBall(ballPos);
        int speedX,speedY;

        speedX = 9;
        speedY = -7;
//        speedX = randomizeSpeed(5, -2);
//        speedY = randomizeSpeed(3, 0);
        ball.setSpeed(speedX,speedY);
        player = new Player(ballPos,150,10, drawArea);
        this.area = drawArea;

        gameLevels = new GameLevels(this, drawArea, brickCount, lineCount, brickDimensionRatio);
        level = 0;
    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public void detectCollision(){

        if(player.collideBall(ball))
            ball.reverseY();

        else if(collideBrickWall())
            ReduceBrickCount();

        else if(collideBorder())
            ball.reverseX();

        else if(collideTopBorder())
            ball.reverseY();

        else if(ballFalls()){
            ballCount--;
            ballLost = true;
        }
    }

    public boolean collideBrickWall(){

        for(Brick brick : bricks){
            boolean isCement = brick.getBrickName().equalsIgnoreCase("Cement Brick");

            switch (brick.findImpact(ball)) {
                case Brick.UP_IMPACT -> {
                    ball.reverseY();
                    return isCement ? brick.setImpact(ball.getDown(), Crack.UP) : brick.setImpact();
                }
                case Brick.DOWN_IMPACT -> {
                    ball.reverseY();
                    return isCement ? brick.setImpact(ball.getUp(), Crack.DOWN) : brick.setImpact();
                }

                case Brick.LEFT_IMPACT -> {
                    ball.reverseX();
                    return isCement ? brick.setImpact(ball.getRight(), Crack.RIGHT) : brick.setImpact();
                }
                case Brick.RIGHT_IMPACT -> {
                    ball.reverseX();
                    return isCement ? brick.setImpact(ball.getLeft(), Crack.LEFT) : brick.setImpact();
                }
            }
        }
        return false;
    }

    public boolean collideBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public boolean ballFalls() {
        return ball.getPosition().getY() > area.getY() + area.getHeight();
    }

    public boolean collideTopBorder() {
        return ball.getPosition().getY() < area.getY();
    }

    public int getBallCount(){
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        speedX = 9;
        speedY = -7;
//        speedX = randomizeSpeed(5, -2);
//        speedY = randomizeSpeed(3, 0);
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    public void startMovement(){
        player.move();
        ball.move();
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
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
        setBallCount(3);
    }

    public Brick makeBrick(Point2D point, Dimension2D size, String type){
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBricks(type, size, point);
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        bricks = gameLevels.getLevels()[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel(){
        return level < gameLevels.getLevels().length;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer() {
        return player;
    }

    public int randomizeSpeed(int range, int constant){
        int speed;
        do{
            speed = rnd.nextInt(range) + constant;
        }while(speed == 0);

        return speed;
    }
}
