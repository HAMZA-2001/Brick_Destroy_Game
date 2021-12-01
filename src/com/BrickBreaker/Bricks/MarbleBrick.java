package com.BrickBreaker.Bricks;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class MarbleBrick extends Brick{


	    private static final String NAME = "Cement Brick";
	    private static final Color DEF_INNER = new Color(255, 246, 227);
	    private static final Color DEF_BORDER = new Color(217, 199, 175);
	    private static final int CEMENT_STRENGTH = 3;

	    private Crack crack;
	    private Shape brickFace;


	    public MarbleBrick(Point point, Dimension size){
	        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
	        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS, this);
	        brickFace = super.brickFace;
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
	        return brickFace;
	    }

	    private void updateBrick(){
	        if(!super.isBroken()){
	            GeneralPath gp = crack.draw();
	            gp.append(super.brickFace,false);
	            brickFace = gp;
	        }
	    }

	    public void repair(){
	        super.repair();
	        crack.reset();
	        brickFace = super.brickFace;
	    }
	}

