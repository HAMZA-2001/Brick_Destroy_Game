package com.BrickBreaker.Entities;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import com.BrickBreaker.Bricks.Brick;
import com.BrickBreaker.Bricks.CementBrick;
import com.BrickBreaker.Bricks.ClayBrick;
import com.BrickBreaker.Bricks.MarbleBrick;
import com.BrickBreaker.Bricks.SteelBrick;

public class LevelDesign {


	    private static final int LEVELS_COUNT = 5;

	    private static final int CLAY = 1;
	    private static final int STEEL = 2;
	    private static final int CEMENT = 3;
	    private static final int MARBLE = 4;
	    
	    /**
	     * makes the designated type of brick 
	     * @param point point to place the brick
	     * @param size size of the brick
	     * @param type the type of brick
	     * @return Brick object specifying the type of brick
	     */
	    	private Brick makeBrick(Point point, Dimension size, int type){
	        Brick out;
	        switch(type){
	            case CLAY:
	                out = new ClayBrick(point,size);
	                break;
	            case STEEL:
	                out = new SteelBrick(point,size);
	                break;
	            case CEMENT:
	                out = new CementBrick(point, size);
	                break;
	            case MARBLE:
	            	out = new MarbleBrick(point, size);
	            	break;
	            default:
	                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
	        }
	        return  out;
	    }
// Changed brickCnt to brickCount
// Changed lineCnt to lineCount
	    
	    	/**
	    	 * Levels of brick containing only a single type of brick
	    	 * @param drawArea area of the bricks to be drawn at
	    	 * @param brickCount number of bricks
	    	 * @param lineCount number of lines 
	    	 * @param brickSizeRatio ratio of the brick in comparison to the game frame
	    	 * @param type type of brick
	    	 * @return list of Brick objects required to set the level
	    	 */

		  	private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCount, int lineCount, double brickSizeRatio, int type){
		        /*
		          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
		          multiple of lineCount smaller then brickCount
		         */
		        brickCount -= brickCount % lineCount;

		        int brickOnLine = brickCount / lineCount;

		        double brickLen = drawArea.getWidth() / brickOnLine;
		        double brickHgt = brickLen / brickSizeRatio;

		        brickCount += lineCount / 2;

		        Brick[] tmp  = new Brick[brickCount];

		        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
		        Point p = new Point();

		        int i;
		        for(i = 0; i < tmp.length; i++){
		            int line = i / brickOnLine;
		            if(line == lineCount)
		                break;
		            double x = (i % brickOnLine) * brickLen;
		            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
		            double y = (line) * brickHgt;
		            p.setLocation(x,y);
		            tmp[i] = makeBrick(p,brickSize,type);
		        }

		        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
		            double x = (brickOnLine * brickLen) - (brickLen / 2);
		            p.setLocation(x,y);
		            tmp[i] = new ClayBrick(p,brickSize);
		        }
		        return tmp;

		    }

		    /**
		     * 2 types of bricks made in a design of a chess board
		     * @param drawArea area of the bricks to be drawn at
		     * @param brickCnt number of bricks
		     * @param lineCnt number of lines
		     * @param brickSizeRatio ratio of the brick in comparison to the game frame
		     * @param brickA first type of a brick
		     * @param brickB second type of brick
		     * @return list of Brick objects required to set the level
		     */
		   private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int brickA, int brickB){
		        /*
		          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
		          multiple of lineCount smaller then brickCount
		         */
		        brickCnt -= brickCnt % lineCnt;

		        int brickOnLine = brickCnt / lineCnt;

		        int centerLeft = brickOnLine / 2 - 1;
		        int centerRight = brickOnLine / 2 + 1;

		        double brickLen = drawArea.getWidth() / brickOnLine;
		        double brickHgt = brickLen / brickSizeRatio;

		        brickCnt += lineCnt / 2;

		        Brick[] tmp  = new Brick[brickCnt];

		        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
		        Point p = new Point();

		        int i;
		        for(i = 0; i < tmp.length; i++){
		            int line = i / brickOnLine;
		            if(line == lineCnt)
		                break;
		            int posX = i % brickOnLine;
		            double x = posX * brickLen;
		            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
		            double y = (line) * brickHgt;
		            p.setLocation(x,y);

		            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
		            tmp[i] = b ?  makeBrick(p,brickSize,brickA) : makeBrick(p,brickSize,brickB);
		        }

		        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
		            double x = (brickOnLine * brickLen) - (brickLen / 2);
		            p.setLocation(x,y);
		            tmp[i] = makeBrick(p,brickSize,brickA);
		        }
		        return tmp;
		    }
		   
		   /**
		    * sets up the the bricks for different levels
		    * @param drawArea area of the bricks to be drawn at
		    * @param brickCount number of bricks
		    * @param lineCount number  of lines
		    * @param brickDimensionRatio ratio of the brick in comparison to the game frame
		    * @return
		    */
		   public Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
		        Brick[][] tmp = new Brick[LEVELS_COUNT][];
		        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
		        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
		        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
		        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
		        tmp[4] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CEMENT,MARBLE);
		        return tmp;
		    }

		    
		   	
	

}
