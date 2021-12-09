package BrickDestroy.Models;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class MetalBrick extends Brick{
    private static final String NAME = "Metal Brick";
    private static final Color DEF_INNER = Color.rgb(119,119,119);
    private static final Color DEF_BORDER = Color.rgb(238,238,238);
    private static final int METAL_STRENGTH = 3;
    private static final double METAL_PROBABILITY = 0.4;

    private final Crack crack;
    private Shape brickFace;
    private Path crackPath;

    public MetalBrick(Point2D point, Dimension2D size) {
        super(NAME,point,size,DEF_BORDER,DEF_INNER,METAL_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);
        brickFace = makeBrickFace(point, size);
    }

    @Override
    protected Shape makeBrickFace(Point2D pos, Dimension2D size) {
        return new Rectangle(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(!super.isBroken())
            return false;
        impact();
        if(super.isBroken()){
            crack.makeCrack(point, dir, brickFace);
            updateBrick();
            return false;
        }
        return true;
    }

    private void updateBrick(){
        if(super.isBroken())
            crackPath = crack.draw();
    }

    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }

    @Override
    public Shape getBrick() {
        return null;
    }

    @Override
    public Path getPath() {
        return crackPath;
    }

    public void impact(){
        Random rnd = new Random();
        if(rnd.nextDouble() < METAL_PROBABILITY)
            super.impact();
    }
}
