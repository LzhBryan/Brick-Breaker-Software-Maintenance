package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * A class which contains all the entities logics and interactions in the game.
 */
public class GameLogic {
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
    private int score;

    /**
     * Constructor to initialize entities related variables, create ball, player and instantiate GameLevels object.
     * @param drawArea this represents the game board screen dimension.
     * @param brickCount the amount of bricks in total.
     * @param lineCount the amount of rows of bricks.
     * @param brickDimensionRatio the ratio of the brick size.
     * @param ballPos the initial position of the ball.
     */
    public GameLogic(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point2D ballPos){
        startPoint = new Point2D(ballPos.getX(), ballPos.getY());
        ballCount = 3;
        ballLost = false;
        rnd = new Random();

        makeBall(ballPos);
        int speedX,speedY;

        speedX = 7;
        speedY = -7;
//        speedX = randomizeSpeed(5, -2);
//        speedY = -randomizeSpeed(3, 0);
        ball.setSpeed(speedX,speedY);
        player = new Player(ballPos,150,10, drawArea);
        this.area = drawArea;

        gameLevels = new GameLevels(this, drawArea, brickCount, lineCount, brickDimensionRatio);
        level = 0;
        score = 0;
    }

    /**
     * A method to obtain the ball object from the ball factory.
     * @param ballPos the initial position of the ball.
     */
    private void makeBall(Point2D ballPos){
        BallFactory ballFactory = new BallFactory();
        ball = ballFactory.makeBall("Rubber Ball", ballPos);
    }

    /**
     *A method to detect any collision between the entities such as brick, ball, player and the border have occurred in the game.
     */
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

    /**
     * A method to check if any brick is collided with the ball. Checks if its crackable brick and call the respective method to set the impact on the brick.
     * @return the brick broken status.
     */
    public boolean collideBrickWall(){
        for(Brick brick : bricks){
            boolean isCrackable = brick.getBrickName().equalsIgnoreCase("Cement Brick")
                                  || brick.getBrickName().equalsIgnoreCase("Metal Brick");

            switch (brick.findImpact(ball)) {
                case Brick.UP_IMPACT -> {
                    score += brickScore(brick);
                    ball.reverseY();
                    return isCrackable ? brick.setImpact(ball.getDown(), Crack.UP) : brick.setImpact();
                }
                case Brick.DOWN_IMPACT -> {
                    score += brickScore(brick);
                    ball.reverseY();
                    return isCrackable ? brick.setImpact(ball.getUp(), Crack.DOWN) : brick.setImpact();
                }

                case Brick.LEFT_IMPACT -> {
                    score += brickScore(brick);
                    ball.reverseX();
                    return isCrackable ? brick.setImpact(ball.getRight(), Crack.RIGHT) : brick.setImpact();
                }
                case Brick.RIGHT_IMPACT -> {
                    score += brickScore(brick);
                    ball.reverseX();
                    return isCrackable ? brick.setImpact(ball.getLeft(), Crack.LEFT) : brick.setImpact();
                }
            }
        }
        return false;
    }

    /**
     * A method to check if the ball collide with the left and right border.
     * @return true if ball collided with either left or right border, otherwise false.
     */
    public boolean collideBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * A method to check if the ball falls into the bottom border.
     * @return the status of ball touching the bottom border.
     */
    public boolean ballFalls() {
        return ball.getPosition().getY() > area.getY() + area.getHeight();
    }

    /**
     * A method to detect if the ball collides with the top border.
     * @return the status of the ball touching the top border.
     */
    public boolean collideTopBorder() {
        return ball.getPosition().getY() < area.getY();
    }

    /**
     * A getter method to return the ball count.
     * @return ball count.
     */
    public int getBallCount(){
        return ballCount;
    }

    /**
     * A setter method to assign the parameter to the ball count variable.
     * @param ballCount the new ball count to be assigned.
     */
    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    /**
     * A getter method to obtain the ball lost status.
     * @return ball lost status.
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * A method to reset the player and ball back to the initial position after the ball drops, as well as resetting the ball speed.
     */
    public void reset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        speedX = 7;
        speedY = -7;
//        speedX = randomizeSpeed(5, -2);
//        speedY = -randomizeSpeed(3, 0);
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    /**
     * A method to start the movement of both ball and player.
     */
    public void startMovement(){
        player.move();
        ball.move();
    }

    /**
     * A method to check if ball count = 0.
     * @return true if ball count reaches 0, otherwise false.
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * A setter method to assign the parameter speed to the horizontal speed of ball.
     * @param s the horizontal speed to be assigned.
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * A setter method to assign the parameter speed to the vertical speed of ball.
     * @param s the vertical speed to be assigned.
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * A method to reset the ball count back to 3.
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    /**
     * A getter method to obtain the number of bricks.
     * @return an array of bricks in a particular level.
     */
    public Brick[] getBricks() {
        return bricks;
    }

    /**
     * A getter method to obtain the amount of the brick
     * @return the amount of brick.
     */
    public int getBrickCount(){
        return brickCount;
    }

    /**
     * A method to decrement the amount of brick after a brick is destroyed.
     */
    public void ReduceBrickCount() {
        this.brickCount--;
    }

    /**
     * A method to repair all the bricks within a level and reset the ball count back to 3 after reaching a new level or game over.
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        setBallCount(3);
    }

    /**
     * A method to make the intended types of brick.
     * @param point the position to make the new brick.
     * @param size the size of the brick.
     * @param type the name of the brick.
     * @return the intended brick object with the assigned position and size.
     */
    public Brick makeBrick(Point2D point, Dimension2D size, String type){
        BrickFactory brickFactory = new BrickFactory();
        return brickFactory.getBricks(type, size, point);
    }

    /**
     * A method to check if the amount of brick in a level reaches 0.
     * @return true if brick count is 0, otherwise false.
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * A method to move to the next level.
     */
    public void nextLevel(){
        bricks = gameLevels.getLevels()[level++];
        this.brickCount = bricks.length;
    }

    /**
     * A method to check has the level reaches the end.
     * @return the status of the level reaches the end or not.
     */
    public boolean hasLevel(){
        return level < gameLevels.getLevels().length;
    }

    /**
     * A getter method to return the ball.
     * @return ball.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * A getter method to return the player.
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * A method to randomly assign the velocity of the ball.
     * @param range the range for the bound of random number generator.
     * @param constant the constant to be added on the velocity.
     * @return the randomized speed.
     */
    public int randomizeSpeed(int range, int constant){
        int speed;
        do
            speed = rnd.nextInt(range) + constant;
        while(speed == 0);
        return speed;
    }

    /**
     * A getter method to obtain the score.
     * @return score.
     */
    public int getScore() {
        return score;
    }

    /**
     * A method to decrement the score based on the penalty when a ball drops or all balls are lost.
     * @param penalty how much to reduce the game score.
     */
    public void reduceScore(int penalty){
        score -= penalty;
    }

    /**
     * A setter method to assign the parameter score to the score variable. It is used to set the score to 0 when game over or user start playing the game.
     * @param score the intended score to be assigned.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * A method to increase the score by 300 multiply by the remaining ball count.
     * @param ballCount the amount of ball left in a particular level.
     */
    public void increaseScore(int ballCount){
        score += 300*ballCount;
    }

    /**
     * A method to obtain the score for each different type of brick.
     * @param brick the brick that collided with the ball.
     * @return the score based on which type of brick collided with the ball.
     */
    public int brickScore(Brick brick){
        if(brick.getBrickName().equalsIgnoreCase("Clay Brick"))
            return 1;

        else if(brick.getBrickName().equalsIgnoreCase("Steel Brick"))
            return 2;

        else if(brick.getBrickName().equalsIgnoreCase("Cement Brick"))
            return 3;

        else if(brick.getBrickName().equalsIgnoreCase("Metal Brick"))
            return 4;

        return 0;
    }
}