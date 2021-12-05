package com.BrickBreaker.Balls;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 *
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * Instantiate a Ball object
     * @param center Point2D object
     * @param radiusA upper radius
     * @param radiusB bottom radius
     * @param inner inner Color
     * @param border border Color
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        setUp(new Point2D.Double());
        setDown(new Point2D.Double());
        left = new Point2D.Double();
        setRight(new Point2D.Double());

        getUp().setLocation(center.getX(),center.getY()-(radiusB / 2));
        getDown().setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        getRight().setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }
    
    /**
     * makes the shape of the ball
     * @param center Point2D object of the center of the ball
     * @param radiusA upper radius
     * @param radiusB bottom radius
     * @return a Shape object 
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);
    
    /**
     * moves the ball
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }
    
    /**
     * set up ball's speed
     * @param x speed at x 
     * @param y speed at y
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }
    
    /**
     * set up x speed
     * @param s value of the speed to be set
     */
    public void setXSpeed(int s){
        speedX = s;
    }
    
    /**
     * set up y speed
     * @param s value of the speed to be set
     */
    public void setYSpeed(int s){
        speedY = s;
    }
    
    /**
     * reverses the direction of the ball along x axis
     */
    public void reverseX(){
        speedX *= -1;
    }
    
    /**
     * reverses the direction of the ball along y axis
     */
    public void reverseY(){
        speedY *= -1;
    }
    
    /**
     * gets the border color
     * @return border color
     */
    public Color getBorderColor(){
        return border;
    }
    
    /**
     * gets the inner color
     * @return inner color
     */
    public Color getInnerColor(){
        return inner;
    }
    
    /**
     * @return center position
     */
    public Point2D getPosition(){
        return center;
    }
    
    /**
     * @return the shape of ballFace
     */
    public Shape getBallFace(){
        return ballFace;
    }
    
    /**
     * move the ball to point p
     * @param p Point object for the point the ball is to be moved
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }
    
    /**
     * sets the ball Points
     * @param width
     * @param height
     */
    private void setPoints(double width,double height){
        getUp().setLocation(center.getX(),center.getY()-(height / 2));
        getDown().setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        getRight().setLocation(center.getX()+(width / 2),center.getY());
    }
    
    /**
     * @return speedX
     */
    public int getSpeedX(){
        return speedX;
    }
    
    /**
     * @return speedY
     */
    public int getSpeedY(){
        return speedY;
    }

	/**
	 * @return down
	 */
	public Point2D getDown() {
		return down;
	}

	/**
	 * @param down the down to be set
	 */
	public void setDown(Point2D down) {
		this.down = down;
	}

	/**
	 * @return the up
	 */
	public Point2D getUp() {
		return up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(Point2D up) {
		this.up = up;
	}

	/**
	 * @return the right
	 */
	public Point2D getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(Point2D right) {
		this.right = right;
	}
	
	/**
	 * @return the left
	 */
	public Point2D getLeft() {
		return right;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(Point2D right) {
		this.right = right;
	}



}
