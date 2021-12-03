package com.BrickBreaker.Bricks;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;



    
    
    public class Crack{
    	

//    public static final int MIN_CRACK = 1;
//    public static final int DEF_CRACK_DEPTH = 1;
//    public static final int DEF_STEPS = 35;
//
//
//    public static final int UP_IMPACT = 100;
//    public static final int DOWN_IMPACT = 200;
//    public static final int LEFT_IMPACT = 300;
//    public static final int RIGHT_IMPACT = 400;

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;

        /**
         * Instantiate a crack object 
         * @param crackDepth depth of crack
         * @param steps number of steps
         */
        Brick brick;
        public Crack(int crackDepth, int steps, Brick brick){

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;
            this.brick = brick;

        }


        /**
         * provides full implementations of a geometric path
         * @return crack a generalPath object
         */
        public GeneralPath draw(){

            return crack;
        }
        
        /**
         * resets the crack
         */
        public void reset(){
            crack.reset();
        }
        
        /**
         * the brick is cracked when the bounds of a brick and the coordinates of the 
         * ball intersects with each other 
         * @param point store a 2D coordinate space
         * @param direction direction of the crack
         */
        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = brick.brickBody.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;

            }
        }
        
        /**
         * TODDO
         * @param start starting coordinate 
         * @param end ending coordinate
         */
        protected void makeCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);
 
                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return Brick.rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        private int jumps(int bound,double probability){

            if(Brick.rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }
        
        // Changed the parameter name from and to to start and end

        private Point makeRandomPoint(Point start,Point end, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = Brick.rnd.nextInt(end.x - start.x) + start.x;
                    out.setLocation(pos,end.y);
                    break;
                case VERTICAL:
                    pos = Brick.rnd.nextInt(end.y - start.y) + start.y;
                    out.setLocation(end.x,pos);
                    break;
            }
            return out;
        }

    }