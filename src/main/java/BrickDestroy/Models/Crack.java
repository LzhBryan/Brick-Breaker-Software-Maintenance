package BrickDestroy.Models;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import java.util.Random;

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

    public Crack(int crackDepth, int steps){

        crack = new Path();
        this.crackDepth = crackDepth;
        this.steps = steps;
        rnd = new Random();
    }

    public Path draw(){
        return crack;
    }

    public void reset(){
        crack.getElements().clear();
    }

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

    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    private boolean inMiddle(int i, int divisions){
        int low = (Crack.CRACK_SECTIONS / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    private int jumps(int bound){

        if(rnd.nextDouble() > Crack.JUMP_PROBABILITY)
            return randomInBounds(bound);
        return  0;

    }

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
