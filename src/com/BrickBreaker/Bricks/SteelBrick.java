package com.BrickBreaker.Bricks;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class SteelBrick extends Brick {

    private static final String BRICK_NAME = "Steel Brick"; // Changed NAME to BRICK_NAME
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random random;
    private Shape brickBody;
    
    /**
     * Instantiate a SteelBrick object
     * @param point Point object
     * @param size dimensions of the brick
     */
    public SteelBrick(Point point, Dimension size){
        super(BRICK_NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        random = new Random();
        brickBody = super.brickBody;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Shape getBrick() {
        return brickBody;
    }	
    
    /**
     * if the wall have made an impact 
     * @return if the wall is broken
     */
    public boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }
    
    /**
     * impact of made between ball and steel brick
     */
    public void impact(){
        if(random.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

}
