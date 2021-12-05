package com.BrickBreaker.Bricks;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

import com.BrickBreaker.Balls.Ball;


abstract public class Brick  {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


// makes a crack upon ball intersection with the brick
//    public class Crack{
//    	
//        private static final int CRACK_SECTIONS = 3;
//        private static final double JUMP_PROBABILITY = 0.7;
//
//        public static final int LEFT = 10;
//        public static final int RIGHT = 20;
//        public static final int UP = 30;
//        public static final int DOWN = 40;
//        public static final int VERTICAL = 100;
//        public static final int HORIZONTAL = 200;
//
//
//
//        private GeneralPath crack;
//
//        private int crackDepth;
//        private int steps;
//
//
//        public Crack(int crackDepth, int steps){
//
//            crack = new GeneralPath();
//            this.crackDepth = crackDepth;
//            this.steps = steps;
//
//        }
//
//
//
//        public GeneralPath draw(){
//
//            return crack;
//        }
//
//        public void reset(){
//            crack.reset();
//        }
//
//        protected void makeCrack(Point2D point, int direction){
//            Rectangle bounds = Brick.this.brickFace.getBounds();
//
//            Point impact = new Point((int)point.getX(),(int)point.getY());
//            Point start = new Point();
//            Point end = new Point();
//
//
//            switch(direction){
//                case LEFT:
//                    start.setLocation(bounds.x + bounds.width, bounds.y);
//                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
//                    Point tmp = makeRandomPoint(start,end,VERTICAL);
//                    makeCrack(impact,tmp);
//
//                    break;
//                case RIGHT:
//                    start.setLocation(bounds.getLocation());
//                    end.setLocation(bounds.x, bounds.y + bounds.height);
//                    tmp = makeRandomPoint(start,end,VERTICAL);
//                    makeCrack(impact,tmp);
//
//                    break;
//                case UP:
//                    start.setLocation(bounds.x, bounds.y + bounds.height);
//                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
//                    tmp = makeRandomPoint(start,end,HORIZONTAL);
//                    makeCrack(impact,tmp);
//                    break;
//                case DOWN:
//                    start.setLocation(bounds.getLocation());
//                    end.setLocation(bounds.x + bounds.width, bounds.y);
//                    tmp = makeRandomPoint(start,end,HORIZONTAL);
//                    makeCrack(impact,tmp);
//
//                    break;
//
//            }
//        }
//
//        protected void makeCrack(Point start, Point end){
//
//            GeneralPath path = new GeneralPath();
//
//
//            path.moveTo(start.x,start.y);
//
//            double w = (end.x - start.x) / (double)steps;
//            double h = (end.y - start.y) / (double)steps;
//
//            int bound = crackDepth;
//            int jump  = bound * 5;
//
//            double x,y;
//
//            for(int i = 1; i < steps;i++){
//
//                x = (i * w) + start.x;
//                y = (i * h) + start.y + randomInBounds(bound);
//
//                if(inMiddle(i,CRACK_SECTIONS,steps))
//                    y += jumps(jump,JUMP_PROBABILITY);
//
//                path.lineTo(x,y);
//
//            }
//
//            path.lineTo(end.x,end.y);
//            crack.append(path,true);
//        }
//
//        private int randomInBounds(int bound){
//            int n = (bound * 2) + 1;
//            return rnd.nextInt(n) - bound;
//        }
//
//        private boolean inMiddle(int i,int steps,int divisions){
//            int low = (steps / divisions);
//            int up = low * (divisions - 1);
//
//            return  (i > low) && (i < up);
//        }
//
//        private int jumps(int bound,double probability){
//
//            if(rnd.nextDouble() > probability)
//                return randomInBounds(bound);
//            return  0;
//
//        }
//
//        private Point makeRandomPoint(Point from,Point to, int direction){
//
//            Point out = new Point();
//            int pos;
//
//            switch(direction){
//                case HORIZONTAL:
//                    pos = rnd.nextInt(to.x - from.x) + from.x;
//                    out.setLocation(pos,to.y);
//                    break;
//                case VERTICAL:
//                    pos = rnd.nextInt(to.y - from.y) + from.y;
//                    out.setLocation(to.x,pos);
//                    break;
//            }
//            return out;
//        }
//
//    }

    public static Random rnd; //Changed rnd to random

    private String name;
    Shape brickBody; //Changed brickFace to brickBody

    private Color borderColor; // Changed border to borderColor
    private Color innerColor; // Changed inner to innerColor

    private int fullStrength;
    private int strength;

    private boolean isbroken; //Changed broken to isbroken

    /**
     * Instantiate the brick object
     * @param name Brick name
     * @param pos Brick position
     * @param size Brick size
     * @param borderColor border color
     * @param innerColor inner wall color
     * @param strength number of impacts on the brick by the ball
     */
    public Brick(String name, Point pos,Dimension size,Color borderColor,Color innerColor,int strength){
        rnd = new Random();
        isbroken = false;
        this.setName(name);
        brickBody = makeBrickFace(pos,size);
        this.borderColor = borderColor;
        this.innerColor = innerColor;
        this.fullStrength = this.strength = strength;

    }
    
    /**
     * makes the shape for the brick face
     * @param pos position of the brick
     * @param size dimensions of the bricks
     * @return shape of brick
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);
    
    /**
     * checks if the impact have been made between ball and brick
     * @param point Point2D object
     * @param dir direction value
     * @return
     */
    public  boolean setImpact(Point2D point , int dir){
        if(isbroken)
            return false;
        impact();
        return  isbroken;
    }
    
    /**
     * 
     * @return gets the shape of the brick
     */
    public abstract Shape getBrick();


    /**
     * 
     * @return gets brick's border color 
     */
    public Color getBorderColor(){
        return  borderColor;
    }
    
    /**
     * 
     * @return get brick's inner color
     */
    public Color getInnerColor(){
        return innerColor;
    }

    /**
     * finds the impact between ball and brick
     * @param b Ball object
     * @return impact direction
     */
    public final int findImpact(Ball b){
        if(isbroken)
            return 0;
        int out  = 0;
        if(brickBody.contains(b.getRight()))
            out = LEFT_IMPACT;
        else if(brickBody.contains(b.getLeft()))
            out = RIGHT_IMPACT;
        else if(brickBody.contains(b.getUp()))
            out = DOWN_IMPACT;
        else if(brickBody.contains(b.getDown()))
            out = UP_IMPACT;
        return out;
    }
    
    /**
     * @return if brick is broken
     */
    public final boolean isBroken(){
        return isbroken;
    }
    
    /**
     * repairs the wall
     */
    public void repair() {
        isbroken = false;
        strength = fullStrength;
    }
    
    /**
     * decreases brick strength upon impact
     */
    public void impact(){
        strength--;
        isbroken = (strength == 0);
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



}





