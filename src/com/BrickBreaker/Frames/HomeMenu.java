package com.BrickBreaker.Frames;

import javax.swing.*;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String EXIT_TEXT = "Exit";
    private static final String INFO_TEXT = "Info";
    private static final String HISTORY_TEXT = "History";
    
    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0); //school bus yellow
    private static final Color TEXT_COLOR = new Color(16, 52, 166);	//egyptian blue
    private static final Color BUTTON_COLOR = new Color(233, 150, 122);
    private static final Color CLICKED_BUTTON_COLOR = Color.red;
    private static final Color CLICKED_TEXT = Color.DARK_GRAY;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace; 
    private Rectangle startButton;
    private Rectangle exitButton;
    private Rectangle infoButton;
    private Rectangle historyButton;

    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean startClicked;
    private boolean exitClicked; // was menuClicked before
    private boolean infoClicked;
    private boolean historyClicked;

    
    /**
     * Instantiate the home menu object
     * @param owner GameFrame object of HomeMenu 
     * @param area dimensions of the HomeMenu window
     */
    public HomeMenu(GameFrame owner,Dimension area){

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 11);
        startButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
        historyButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Georgia",Font.BOLD,55);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);

    }

    /**
     * paints the graphics object
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * draws various containers, texts and buttons onto the the menu
     * @param g2d graphics2D object
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }
    
    /**
     * draws the container where background and boarders are set
     * @param g2d graphics2D object
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);

        Image  picture = new ImageIcon(this.getClass().getResource("/wallPic2.jpg")).getImage();
        g2d.drawImage(picture,0,0,this);

        		
        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);
        

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    /**
     * draws the text in the Home screen
     * @param g2d graphics2D object
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 8);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }

    /**
     * draws the buttons in the Home screen
     * @param g2d graphics2D object
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT,frc);
        Rectangle2D hTxtRect = buttonFont.getStringBounds(HISTORY_TEXT,frc);
        

        
        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.5);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.8);

        g2d.setFont(buttonFont);
       
        g2d.setColor(BUTTON_COLOR);
        g2d.fill(startButton);

        g2d.setColor(Color.BLACK);
        
        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.setColor(Color.BLACK);
            g2d.drawString(START_TEXT,x,y);
            
            
            //g2d.setColor(START_TEXT);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.25;

        exitButton.setLocation(x,y);
        
        g2d.setColor(BUTTON_COLOR);
        g2d.fill(exitButton);
        g2d.setColor(Color.BLACK);

        x = (int)(exitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (startButton.height * 0.8);

        if(exitClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);
  
        }
        else{
            g2d.draw(exitButton);
            g2d.setColor(Color.BLACK);
            g2d.drawString(EXIT_TEXT,x,y);
            
        }
        
        x = exitButton.x;
        y = exitButton.y;

        y *= 1.2;

        //Added
        infoButton.setLocation(x,y);
        
        g2d.setColor(BUTTON_COLOR);
        g2d.fill(infoButton);
        g2d.setColor(Color.BLACK);
    
	    x = (int)(infoButton.getWidth() - iTxtRect.getWidth()) / 2;
	    y = (int)(infoButton.getHeight() - iTxtRect.getHeight()) / 2;
	
	    x += infoButton.x;
	    y += infoButton.y + (startButton.height * 0.8);
	    
	    // if info button is clicked
	    if(infoClicked){
	        Color tmp = g2d.getColor();
	
	        g2d.setColor(CLICKED_BUTTON_COLOR);
	        g2d.draw(infoButton);
	        g2d.setColor(CLICKED_TEXT);
	        g2d.drawString(INFO_TEXT,x,y);
	        g2d.setColor(tmp);
	      
	    }
	    else{
	        g2d.draw(infoButton);
	        g2d.setColor(Color.BLACK);
	        g2d.drawString(INFO_TEXT,x,y);
	    }
	    
	    x = infoButton.x;
        y = infoButton.y;

        y *= 1.18;
        
        historyButton.setLocation(x,y);
        
        g2d.setColor(BUTTON_COLOR);
        g2d.fill(historyButton);
        g2d.setColor(Color.BLACK);
        
        
	    x = (int)(historyButton.getWidth() - hTxtRect.getWidth()) / 2;
	    y = (int)(historyButton.getHeight() - hTxtRect.getHeight()) / 2;
	
	    x += historyButton.x;
	    y += historyButton.y + (startButton.height * 0.8);
	    
	    // if info button is clicked
	    if(historyClicked){
	        Color tmp = g2d.getColor();
	
	        g2d.setColor(CLICKED_BUTTON_COLOR);
	        g2d.draw(historyButton);
	        g2d.setColor(CLICKED_TEXT);
	        g2d.drawString(HISTORY_TEXT,x,y);
	        g2d.setColor(tmp);
	    }
	    else{
	        g2d.draw(historyButton);
	        g2d.drawString(HISTORY_TEXT,x,y);
	    }

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();

        }
        else if(exitButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(infoButton.contains(p)){
            owner.dispose();
            InfoScreen infoBoard = new InfoScreen();
            infoBoard.initialize();
 
        }
        else if(historyButton.contains(p)){
            owner.dispose();
            HistoryScreenView historyBoard = new HistoryScreenView();
            historyBoard.initialize();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(exitButton.contains(p)){
            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
        else if (infoButton.contains(p)) {
        	infoClicked = true;
        	repaint(infoButton.x,infoButton.y,infoButton.width+1,startButton.height+1);
        }
        else if (historyButton.contains(p)) {
        	historyClicked = true;
        	repaint(historyButton.x,historyButton.y,historyButton.width+1,historyButton.height+1);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(exitClicked){
            exitClicked = false;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);
        }
        else if(infoClicked){
        	infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
        else if(historyClicked){
        	historyClicked = false;
            repaint(historyButton.x,historyButton.y,historyButton.width+1,historyButton.height+1);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || exitButton.contains(p)||infoButton.contains(p)||historyButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
