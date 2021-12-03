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

    Shape brickFace;
    private String name;
    private Color border, inner;
    private int fullStrength, strength;
    private boolean broken;
    private Point2D brickPosition;
    private Dimension2D brickSize;

    public Brick(String name, Point2D pos, Dimension2D size, Color border, Color inner, int strength){
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
        this.brickPosition = pos;
        this.brickSize = size;
    }

    protected abstract Shape makeBrickFace(Point2D pos,Dimension2D size);

    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    public abstract Shape getBrick();

    public abstract Path getPath();

    public Color getBorderColor(){
        return  border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right)) {
            out = LEFT_IMPACT;
            System.out.println(b.right);
        }
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    public final boolean isBroken(){
        return broken;
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    public void impact(){
        strength--;
        broken = (strength == 0);
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
}
