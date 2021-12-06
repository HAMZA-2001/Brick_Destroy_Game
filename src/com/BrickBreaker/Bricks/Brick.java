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





