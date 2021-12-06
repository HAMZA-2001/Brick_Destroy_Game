/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.BrickBreaker.Entities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import com.BrickBreaker.Balls.Ball;
import com.BrickBreaker.Balls.RubberBall;
import com.BrickBreaker.Bricks.Brick;
import com.BrickBreaker.Bricks.Crack;



public class Wall {

//    private static final int LEVELS_COUNT = 5;
//
//    private static final int CLAY = 1;
//    private static final int STEEL = 2;
//    private static final int CEMENT = 3;
//    private static final int MARBLE = 4;

    private Random randomNumber; //was rnd before
    private Rectangle area;

    private Brick[] bricks;
    private Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;
    
    //Added
    private String brickName;
    private int score;
    
    Point ballPos;
    
    /**
     * Instantiate the Wall object
     * @param drawArea rectangle object which draws the wall 
     * @param brickCount number of bricks
     * @param lineCount number of lines
     * @param brickDimensionRatio ratio of the dimensions of the bricks
     * @param ballPos position of the ball
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);
        LevelDesign levelDesign = new LevelDesign();
        levels = levelDesign.makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        setLevel(0);

        ballCount = 3;
        ballLost = false;

        randomNumber = new Random();

        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = randomNumber.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -randomNumber.nextInt(3);
        }while(speedY == 0);

        getBall().setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
        
        this.ballPos = ballPos;
        this.setScore(score);


    }


    /**
     * Creates a RubberBall instance 
     * @param ballPos position of the ball to be set
     */
    private void makeBall(Point2D ballPos){
    	ball = new RubberBall(ballPos);
    }

    /**
     * moves both the player and the ball
     */
    public void move(){
        player.move();
        getBall().move();
    }

    /**
     * find the impact position of the ball with the wall and changes balls position upon impact which updates the score 
     */
    public void findImpacts(){
        if(player.impact(getBall())){
            ball.reverseY();
        }
        else if(impactWall()){

            brickCount--;
            //Added
            
            if(brickName.equals("ClayBrick")) {
            	setScore(getScore() + 1);
            }
            else if(brickName.equals("CementBrick")) {
            	setScore(getScore() + 2);
            }
            else if(brickName.equals("SteelBrick")) {
            	setScore(getScore() + 3);
            }
            else if(brickName.equals("MarbleBrick")) {
            	score+=5;
            }
            	
        }
        else if(impactBorder()) {
        	ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
        	ball.reverseY();
        }
        else if(getBall().getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }
    
    /**
     * iterates over the list of bricks and rebounce the ball upon impact and sets up a crack when required
     * @return if the brick and the ball have made the impact
     */
    private boolean impactWall(){
        for(Brick b : getBricks()){
            switch(b.findImpact(getBall())) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                	brickName = b.getClass().getSimpleName();
                    getBall().reverseY();
                    return b.setImpact(getBall().getDown(), Crack.UP);
                case Brick.DOWN_IMPACT:
                	brickName = b.getClass().getSimpleName();
                    getBall().reverseY();
                    return b.setImpact(getBall().getUp(),Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                	brickName = b.getClass().getSimpleName();
                    getBall().reverseX();
                    return b.setImpact(getBall().getRight(),Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                	brickName = b.getClass().getSimpleName();
                    getBall().reverseX();
                    return b.setImpact(getBall().getLeft(),Crack.LEFT);
            }
        }
        return false;
    }
    
    /**
     * point where the ball impacts the wall area
     * @return if the ball have impacted the boarder
     */
    private boolean impactBorder(){
        Point2D p = getBall().getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }
    
    /**
     * @return number of bricks
     */
    public int getBrickCount(){
        return brickCount;
    }
    
    /**
     * 
     * @return number of balls remaining
     */
    public int getBallCount(){
        return ballCount;
    }
    
    /**
     * 
     * @return if the ball is lost
     */
    public boolean isBallLost(){
        return ballLost;
    }
    
    /**
     * resets the ball and player back to its initial position
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = randomNumber.nextInt(5) - 2;
        }while(speedX == 0);
        do{
            speedY = -randomNumber.nextInt(3);
        }while(speedY == 0);

        getBall().setSpeed(speedX,speedY);
        ballLost = false;
    }
    
    /**
     * resets the wall bricks and resets the ball count
     */
    public void wallReset(){
        for(Brick b : getBricks())
            b.repair();
        brickCount = getBricks().length;
        ballCount = 3;
    }
    
    /**
     * 
     * @return if the number of balls remaining are 0 or not
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }
    
    /**
     * 
     * @return if the number of bricks remaining are 0 or not
     */
    public boolean isDone(){
        return brickCount == 0;
    }

   /**
    * sets up the next level
    */
    public void nextLevel(){
        setBricks(levels[level++]);
        this.brickCount = getBricks().length;
        if(getLevel() == 5) {
        	player = new Player((Point) ballPos.clone(),85,10, area);
        }
    }
    
    /**
     * 
     * @return if the current level is lesser than total number of levels
     */
    public boolean hasLevel(){
        return getLevel() < levels.length;
    }
    
    /**
     * sets ball's speed at x axis
     * @param s speed to be set as
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }
    
    /**
     * sets ball's speed at y axis
     * @param s speed to be set as
     */
    public void setBallYSpeed(int s){
        getBall().setYSpeed(s);
    }
    
    /**
     * resets the number of balls
     */
    public void resetBallCount(){
        ballCount = 3;
    }

	/**
	 * @return the ball
	 */
	public Ball getBall() {
		return ball;
	}

	/**
	 * @param ball the ball to set
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}

	/**
	 * @return the bricks
	 */
	public Brick[] getBricks() {
		return bricks;
	}

	/**
	 * @param bricks the bricks to set
	 */
	public void setBricks(Brick[] bricks) {
		this.bricks = bricks;
	}

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
}
