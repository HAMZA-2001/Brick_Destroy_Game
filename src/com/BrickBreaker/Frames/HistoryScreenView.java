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

public class HistoryScreenView extends JFrame {

	private JPanel contentPane;
	public String scoreString;

	public HistoryScreenView() {
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
		backButton.setBounds(276, 377, 99, 48);
		contentPane.add(backButton);
		
		JLabel title = new JLabel("History");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Georgia", Font.BOLD, 30));
		title.setBounds(267, 51, 168, 35);
		contentPane.add(title);
		
		JLabel scoreText = new JLabel("\r\n");
		HistoryScreenModel historymodel = new HistoryScreenModel();
		scoreText.setText("<html>"+"<h1><u>Scores of Last 10 games </u></h1><<br/>" + historymodel.readScore(20) + "</html>");
		scoreText.setBackground(new Color(255, 255, 255));
		scoreText.setForeground(new Color(255, 235, 205));
		scoreText.setHorizontalAlignment(SwingConstants.CENTER);
		scoreText.setFont(new Font("Candara", Font.BOLD, 17));
		scoreText.setBounds(86, 104, 471, 262);
		contentPane.add(scoreText);
		
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setToolTipText("");
		Image img = new ImageIcon(this.getClass().getResource("/info-bg-pic3.png")).getImage();
		backgroundImage.setIcon(new ImageIcon(img));
		backgroundImage.setBounds(0, 0,650, 500);
		contentPane.add(backgroundImage);
	}
	
	public void initialize() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.autoLocate();
        this.setVisible(true);
      
	}
	
	
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }
    
    
    public void enableHomeBoard(){
        this.dispose();

    }
    


}
