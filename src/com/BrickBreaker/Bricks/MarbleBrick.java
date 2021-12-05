package com.BrickBreaker.Bricks;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class MarbleBrick extends Brick{


	    private static final String BRICK_NAME = "Cement Brick";
	    private static final Color DEF_INNER = new Color(255, 246, 227);
	    private static final Color DEF_BORDER = new Color(217, 199, 175);
	    private static final int CEMENT_STRENGTH = 3;

	    private Crack crack;
	    private Shape brickBody;

	    /**
	     * Instantiate a MarbleBrick object
	     * @param point Point object
	     * @param size dimensions of the brick
	     */
	    public MarbleBrick(Point point, Dimension size){
	        super(BRICK_NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
	        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS, this);
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

	    /**
	     * {@inheritDoc}
	     */
	    @Override
	    public Shape getBrick() {
	        return brickBody;
	    }
	    
	    /**
	     * updates the brick
	     */
	    private void updateBrick(){
	        if(!super.isBroken()){
	            GeneralPath gp = crack.draw();
	            gp.append(super.brickBody,false);
	            brickBody = gp;
	        }
	    }
	    
	    /**
	     * repairs the wall
	     */
	    public void repair(){
	        super.repair();
	        crack.reset();
	        brickBody = super.brickBody;
	    }
	}

