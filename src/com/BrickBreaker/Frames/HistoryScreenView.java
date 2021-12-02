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
		

		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setForeground(SystemColor.text);
		btnNewButton.setBackground(new Color(128, 0, 0));
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableHomeBoard();
				GameFrame owner = new GameFrame();
				owner.initialize();	
			}
		});
		btnNewButton.setBounds(276, 377, 99, 48);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("History");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel.setBounds(267, 51, 168, 35);
		contentPane.add(lblNewLabel);
		
		JLabel infoText = new JLabel("\r\n");
		HistoryScreenModel historymodel = new HistoryScreenModel();
		infoText.setText("<html>"+"<h1><u>Scores of Last 10 games </u></h1><<br/>" + historymodel.readScore(20) + "</html>");
		infoText.setBackground(new Color(255, 255, 255));
		infoText.setForeground(new Color(255, 235, 205));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		infoText.setFont(new Font("Candara", Font.BOLD, 17));
		infoText.setBounds(86, 104, 471, 262);
		contentPane.add(infoText);
		
		
		JLabel bg = new JLabel("");
		bg.setToolTipText("");
		Image img = new ImageIcon(this.getClass().getResource("/info-bg-pic3.png")).getImage();
		bg.setIcon(new ImageIcon(img));
		bg.setBounds(0, 0,650, 500);
		contentPane.add(bg);
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
