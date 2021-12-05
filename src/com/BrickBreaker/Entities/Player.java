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
import com.BrickBreaker.Balls.Ball;


public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerBody; // was playerFace before
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;

    /**
     * Instantiate the Player object
     * @param ballPoint point of the ball
     * @param width player's width
     * @param height player's height
     * @param container Rectangle object for player's shape
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerBody = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }
    
    /**
     * makes a rectangle of the given coordinates at a point
     * @param width width of rectangle
     * @param height height of rectangle
     * @return a Rectangle object
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }
    
    /**
     * 
     * @param b Ball object
     * @return if ball have made impact with player
     */
    public boolean impact(Ball b){
        return playerBody.contains(b.getPosition()) && playerBody.contains(b.getDown()) ;
    }
    
    /**
     * movement on x axis of the player and updates its location
     * 
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerBody.setLocation(ballPoint.x - (int)playerBody.getWidth()/2,ballPoint.y);
    }

    /**
     * move player to the left
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }
    
    /**
     * move player to the right
     */
    public void moveRight(){  // was movRight() before
        moveAmount = DEF_MOVE_AMOUNT;
    }
    
    /**
     * stops the player
     */
    public void stop(){
        moveAmount = 0;
    }
    
    /**
     * @return the Shape of player
     */
    public Shape getPlayerBody(){ // was getPlayerFace() before
        return  playerBody;
    }

    /**
     * moves the ball and the players position
     * @param p Point object
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerBody.setLocation(ballPoint.x - (int)playerBody.getWidth()/2,ballPoint.y);
    }
}
