package com.BrickBreaker.Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class InfoScreen extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Instantiate a InforScreen object
	 */
	public InfoScreen() {
		super();
	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 545);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224,139,62));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JButton backButton = new JButton("Back");
		backButton.setForeground(SystemColor.text);
		backButton.setBackground(new Color(128, 0, 0));
		backButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableHomeBoard();
				GameFrame owner = new GameFrame();
				owner.initialize();	
			}
		});
		backButton.setBounds(286, 353, 99, 48);
		contentPane.add(backButton);
		
		JLabel Title = new JLabel("How To Play?");
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setFont(new Font("Georgia", Font.BOLD, 30));
		Title.setBounds(211, 51, 224, 57);
		contentPane.add(Title);
		
		JLabel infoText = new JLabel("<html><body style='text-align: center' >In this game, the player moves a PADDLE from side-to-side to hit a BALL.The game's objective is to eliminate all of the BRICKS at the top of the screen by hitting them with the BALL. But, if the ball hits the bottom ENCLOSURE,the player loses and the game ends! To win the game, all the \t\tBRICKS must be eliminated.</body></html>\r\n");
		infoText.setBackground(new Color(255, 255, 255));
		infoText.setForeground(new Color(255, 235, 205));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		infoText.setFont(new Font("Candara", Font.BOLD, 17));
		infoText.setBounds(86, 104, 471, 238);
		contentPane.add(infoText);
		
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setToolTipText("");
		Image img = new ImageIcon(this.getClass().getResource("/info-bg-pic3.png")).getImage();
		backgroundImage.setIcon(new ImageIcon(img));
		backgroundImage.setBounds(0, 0,650, 500);
		contentPane.add(backgroundImage);
	}
	
	/**
	 * initializes the frame to set
	 */
	public void initialize() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.autoLocate();
        this.setVisible(true);
      
	}
	
	/**
	 * Relocates the screen
	 */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }
    
   /**
    * Disposes the current frame to initialize HomeBoard
    */
    public void enableHomeBoard(){
        this.dispose();
    }


}
