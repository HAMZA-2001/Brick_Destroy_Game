package com.BrickBreaker.Bricks;

import java.awt.*;
import java.awt.Point;

/**
 * Created by filippo on 04/09/16.
 *
 */
public class ClayBrick extends Brick {

    private static final String BRICK_NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;





    /**
     * Instantiate a ClayBrick object
     * @param point Point object
     * @param size dimension of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(BRICK_NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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
        return super.brickBody;
    }


}
