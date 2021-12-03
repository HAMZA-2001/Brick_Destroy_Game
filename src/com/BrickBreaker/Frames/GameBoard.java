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
package com.BrickBreaker.Frames;

import javax.swing.*;

import com.BrickBreaker.Balls.Ball;
import com.BrickBreaker.Bricks.Brick;
import com.BrickBreaker.Debug.DebugConsole;
import com.BrickBreaker.Entities.Player;
import com.BrickBreaker.Entities.Wall;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;



public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);


    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;

    private Wall wall;

    private String message;
    private String scoreText;
    private String highScore = "";
    
    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    private int currentLevel;
    String score;

    //Timer variables
    Timer timer;	
	String timertxt;
	int second, minute;
	String ddSecond, ddMinute;	
	DecimalFormat dFormat = new DecimalFormat("00");
	
	
    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
        
        HistoryScreenModel scoreList = new HistoryScreenModel();
        
        this.initialize();
        message = "";
        scoreText = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));

        debugConsole = new DebugConsole(owner,wall,this);
        initiateTimer();
        
        //initialize the first level
        wall.nextLevel();
        
        
        
        gameTimer = new Timer(10,e ->{
            wall.move();
            wall.findImpacts();
            
            if(highScore.equals("")) {
            	highScore = GetHighScore();
            }
            
            if(wall.getLevel() == 5) {
            	timer.start();
            }
            
            
            message = String.format("Bricks %d Balls %d Score %d",wall.getBrickCount(),wall.getBallCount(),wall.getScore());
            scoreText = String.format("HighScore: %s", highScore);
            
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.wallReset();
                    CheckScore(wall.getScore());
                    scoreList.writeScore(wall.getScore());
                    wall.setScore(0);
                    message = "Game over";
                    scoreText = "";
                    timertxt = "";
                    timer.stop();
                    initiateTimer();
                    
                }
                wall.ballReset();
                timer.stop();
                gameTimer.stop();
            }
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    scoreText = "";
                    timertxt = "";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    CheckScore(wall.getScore());
                    scoreText = "";
                    timertxt = "";
                    gameTimer.stop();
                    timer.stop();
                }
            }
 
            repaint();
        });

    }
    
    public void initiateTimer() {
    	timertxt = "05:00";
        second =0;
		minute = 5;
		countdownTimer();
    }
    
	public void countdownTimer() {
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);	
				timertxt = ddMinute + ":" + ddSecond;
				System.out.println(timertxt);
				
				if(second==-1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);	
					timertxt = ddMinute + ":" + ddSecond;
				}
				if(minute==0 && second==0) {
					message = "The time is over...";
					timertxt = "";
                    CheckScore(wall.getScore());
                    score = "";
					timer.stop();
					initiateTimer();
					gameTimer.stop();
					wall.setScore(0);
		            wall.ballReset();
		            wall.wallReset();
					repaint();
					
				}
			}
		});		
	}		



    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    

    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        if(wall.getLevel() >= 5) {
        	g2d.setColor(Color.DARK_GRAY);
        	g2d.drawString(timertxt,295,210);
        }
        
        drawBall(wall.getBall(),g2d);
        
        g2d.setColor(Color.BLUE);
        g2d.drawString(message,250,225);
        
        g2d.setColor(Color.black);
		g2d.drawString(scoreText,250,240);
		
        drawBall(wall.getBall(),g2d);

        for(Brick b : wall.getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.getPlayer(),g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerBody();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }
    //Added
    public String GetHighScore() {
    	FileReader readFile = null;
    	BufferedReader reader = null;
    	try {
    		readFile = new FileReader("highscore.dat");

    		
    		reader = new BufferedReader(readFile);
    		return reader.readLine();
    	}
    	catch(Exception e) {
    		return "Nobody:0";
    	}
    	finally {
    		try {
    			if(reader != null)
    			reader.close();
    		}catch(IOException e){
    			e.printStackTrace();
    		}
    	}
    	
    }
    
    public void CheckScore(int gamescore) {
    	
    	if (gamescore > Integer.parseInt((highScore.split(":")[1]))) {
    	
    		//user sets a new record
    		String name =  JOptionPane.showInputDialog("You have set a new highscore. What is your name?");
    		//String name =  JOptionPane.showInputDialog("You have set a new highscore. What is your name?");
    		this.highScore = name + ":" + gamescore;
    		
    		File scoreFile = new File("highscore.dat");
    		//System.out.println(scoreFile.getAbsolutePath());
    		if(!scoreFile.exists()) {
    			try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		FileWriter writeFile = null;
    		BufferedWriter writer = null;
    		try 
    		{
    			writeFile = new FileWriter(scoreFile);
    			writer = new BufferedWriter(writeFile); // Changes file into writable file
    			writer.write(highScore); // writes highest score into  the file
    		}
    		catch(Exception e) {
    			//errors
    		}
    		finally {
    			try {
    				if(writer != null)
        				writer.close();
    			}
    			catch (Exception e) {}
    			
    		}
    		
    	}
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.getPlayer().moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                timer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning()) {
                    	gameTimer.stop();
                    	timer.stop();
                    }	
                    else
                    	if(wall.getLevel() <= 4) {
                    		gameTimer.start();
                    	}else {
                    		gameTimer.start();
                    		timer.start();
                    	}
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.getPlayer().stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.getPlayer().stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            scoreText = "";
            wall.setScore(0);
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            
//            if(wall.getLevel()<=5) {
//            	currentLevel = wall.getLevel();
//            }
//            
            if(wall.getLevel() >= 5) 
            {
            	timertxt = "";
            	second =0;
        		minute =5;
        		countdownTimer();
            	
            }
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    public void onLostFocus(){
        gameTimer.stop();
        timer.stop();
        message = "Focus Lost";
        scoreText = "";
        timertxt = "";
        repaint();
    }

}
