package com.BrickBreaker.Bricks;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {


    private static final String BRICK_NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickBody;


    public CementBrick(Point point, Dimension size){
        super(BRICK_NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS, this);
        brickBody = super.brickBody;
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }


    @Override
    public Shape getBrick() {
        return brickBody;
    }

    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickBody,false);
            brickBody = gp;
        }
    }

    public void repair(){
        super.repair();
        crack.reset();
        brickBody = super.brickBody;
    }
}
