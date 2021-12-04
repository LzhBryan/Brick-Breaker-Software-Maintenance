package BrickDestroy;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public abstract class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    private final Shape brickFace;
    private final String brickName;
    private final Color borderColor;
    private final Color innerColor;
    private final int fullStrength;
    private int strength;
    private boolean brickBroken;
    private final Point2D brickPosition;
    private final Dimension2D brickSize;

    public Brick(String name, Point2D position, Dimension2D size, Color border, Color inner, int strength){
        brickBroken = false;
        this.brickName = name;
        brickFace = makeBrickFace(position, size);
        this.borderColor = border;
        this.innerColor = inner;
        this.fullStrength = this.strength = strength;
        this.brickPosition = position;
        this.brickSize = size;
    }

    protected abstract Shape makeBrickFace(Point2D pos, Dimension2D size);

    public boolean setImpact(){
        if(brickBroken)
            return false;
        impact();
        return brickBroken;
    }

    public boolean setImpact(Point2D position , int dir){
        if(brickBroken)
            return false;
        impact();
        return brickBroken;
    }

    public abstract Shape getBrick();

    public abstract Path getPath();

    public Color getBorderColor(){
        return borderColor;
    }

    public Color getInnerColor(){
        return innerColor;
    }

    public final int findImpact(Ball b){
        if(brickBroken)
            return 0;

        int out  = 0;
        if(brickFace.contains(b.getRight()))
            out = LEFT_IMPACT;

        else if(brickFace.contains(b.getLeft()))
            out = RIGHT_IMPACT;

        else if(brickFace.contains(b.getUp()))
            out = DOWN_IMPACT;

        else if(brickFace.contains(b.getDown()))
            out = UP_IMPACT;

        return out;
    }

    public final boolean isBroken(){
        return !brickBroken;
    }

    // only used in cement
    public void repair() {
        brickBroken = false;
        strength = fullStrength;
    }

    // only used in steel
    public void impact(){
        strength--;
        brickBroken = (strength == 0);
    }

    public Point2D getPos(){
        return brickPosition;
    }

    public double getBrickWidth(){
        return brickSize.getWidth();
    }

    public double getBrickHeight(){
        return brickSize.getHeight();
    }

    public Shape getBrickFace() {
        return brickFace;
    }

    public String getBrickName() {
        return brickName;
    }
}
