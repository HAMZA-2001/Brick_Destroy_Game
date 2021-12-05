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
