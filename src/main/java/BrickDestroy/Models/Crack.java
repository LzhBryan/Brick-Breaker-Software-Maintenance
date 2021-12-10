package BrickDestroy.Models;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import java.util.Random;

/**
 * A class that defines the appearance and behaviour of crackable brick.
 */
public class Crack {
    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private static Random rnd;
    private Path crack;
    private final int crackDepth;
    private final int steps;

    /**
     * Constructor to initialize the data.
     * @param crackDepth how deep the crack goes.
     * @param steps how many cracks in a brick
     */
    public Crack(int crackDepth, int steps){

        crack = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;
        rnd = new Random();
    }

    /**
     * A method to return the crack path.
     * @return the crack path.
     */
    public Path draw(){
        return crack;
    }

    /**
     * A method to remove all the crack path.
     */
    public void reset(){
        crack.getElements().clear();
    }

    /**
     * A method to locate the impact point and bounds of brick, then call the methods to draw the cracks depending on the direction of impact.
     * @param point the impact point where the ball collide with the brick.
     * @param direction the direction where the crack should extend to.
     * @param brickFace the shape of the brick.
     */
    protected void makeCrack(Point2D point, int direction, Shape brickFace){
        Bounds bounds = brickFace.getBoundsInLocal();

        Point2D impact = new Point2D((int)point.getX(),(int)point.getY());
        Point2D start;
        Point2D end;

        switch(direction){
            case LEFT:
                start = new Point2D(bounds.getMaxX(), bounds.getMinY());
                end = new Point2D(bounds.getMaxX(), bounds.getMaxY());
                Point2D tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);
                break;

            case RIGHT:
                start = new Point2D(bounds.getMinX(), bounds.getMinY());
                end = new Point2D(bounds.getMinX(), bounds.getMaxY());
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);
                break;

            case UP:
                start = new Point2D(bounds.getMinX(), bounds.getMaxY());
                end = new Point2D(bounds.getMaxX(), bounds.getMaxY());
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;

            case DOWN:
                start = new Point2D(bounds.getMinX(), bounds.getMinY());
                end = new Point2D(bounds.getMaxX(), bounds.getMinY());
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;
        }
    }

    /**
     * A method to draw the crack lines and store it into a path object.
     * @param start the impact point where the ball collide with the brick.
     * @param end a random point between the bounds of the bricks.
     */
    protected void makeCrack(Point2D start, Point2D end){
        Path path = new Path();
        path.getElements().add(new MoveTo(start.getX(), start.getY()));

        double w = (end.getX() - start.getX()) / (double)steps;
        double h = (end.getY() - start.getY()) / (double)steps;
        int bound = crackDepth;
        int jump  = bound * 5;
        double x,y;

        for(int i = 1; i < steps;i++){
            x = (i * w) + start.getX();
            y = (i * h) + start.getY() + randomInBounds(bound);
            if(inMiddle(i, steps))
                y += jumps(jump);
            path.getElements().add(new LineTo(x,y));
        }
        path.getElements().add(new LineTo(end.getX(),end.getY()));
        crack = path;
    }

    /**
     * A method to obtain a random point within the crack depth variable.
     * @param bound the crack depth
     * @return the random point within the crack depth.
     */
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    /**
     * A method to check if the crack steps are in the middle range of first crack and the last crack steps.
     * @param i the current crack step.
     * @param divisions the total crack steps.
     * @return true, if crack steps are not the first nor the final step, otherwise false.
     */
    private boolean inMiddle(int i, int divisions){
        int low = (CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);
        return  (i > low) && (i < up);
    }

    /**
     * A method to randomize a point and obtain a random a point if it is more than a predefined probability.
     * @param bound the multiplied crack depth.
     * @return the random point within the bound if the probability is greater, else 0;
     */
    private int jumps(int bound){
        if(rnd.nextDouble() > JUMP_PROBABILITY)
            return randomInBounds(bound);
        return  0;
    }

    /**
     * A method to make random points within the brick bounds either in horizontal or vertical direction.
     * @param from the starting point of the bounds.
     * @param to the destination point of the bounds.
     * @param direction horizontal or vertical direction of the crack.
     * @return the random point within the brick bounds.
     */
    private Point2D makeRandomPoint(Point2D from,Point2D to, int direction){

        Point2D out = new Point2D(0,0);
        int pos;

        switch (direction) {
            case HORIZONTAL -> {
                pos = rnd.nextInt((int) (to.getX() - from.getX())) + (int) from.getX();
                out = new Point2D(pos, to.getY());
            }
            case VERTICAL -> {
                pos = rnd.nextInt((int) (to.getY() - from.getY())) + (int) from.getY();
                out = new Point2D(to.getX(), pos);
            }
        }
        return out;
    }
}
