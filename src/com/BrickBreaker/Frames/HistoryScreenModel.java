package com.BrickBreaker.Frames;

import java.io.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class HistoryScreenModel {

    private String txt1; //txt to be written
    
    /**
     * gets the user score upon finishing the game and writes the score set by the user into a text file 
     * @param score game score
     */
    public void writeScore(int score){

        JFrame frame = new JFrame("User Name Storing");
        frame.setSize(560, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JButton button = new JButton("Enter User Name");
        
        File historyFile = new File("history.txt");

        final JLabel label = new JLabel();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = (String)JOptionPane.showInputDialog(
                        frame,
                        "Type Down Your Player Name",
                        "Store User Name",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "Default Name"
                );
                if(userName != null && userName.length() > 0){
                    label.setText("You selected:" + userName);
                }else {
                    label.setText("None selected");
                }
                try{
                    txt1 = "\n" + score + " by " + userName;
                    FileWriter fw = new FileWriter(historyFile, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(txt1);
                    bw.close();
                }catch(Exception xyz){System.out.println(xyz);}
                System.out.println("Success...");

            }
        });

        panel.add(button);
        panel.add(label);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

    }
    
    /**
     * reads the score from the history.txt file and sets it in displayable format for the jLabels to render it in 
     * @param lines number of lines to be read from the history.txt file
     * @return the required text read from history.txt in string 
     */
    public String readScore(int lines){
        int readLines = 0;
        File historyFile = new File("history.txt");
        StringBuilder builder = new StringBuilder();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(historyFile, "r");
            long fileLength = historyFile.length() - 1;
            
            // Set the pointer at the last of the file
            randomAccessFile.seek(fileLength);
            for(long pointer = fileLength; pointer >= 0; pointer--){
                randomAccessFile.seek(pointer);
                char c;
                
                // read from the last one char at the time
                c = (char)randomAccessFile.read();
                
                // break when end of the line
                if(c == '\n'){
                    builder.append(">/rb<"); //writing reverse of </br> because file is being read back words and then revered
                    readLines++;
                    if(readLines == lines)
                        break;
                }
                builder.append(c);
            }
            
            // Since line is read from the last so it
            // is in reverse so use reverse method to make it right
            builder.reverse();
            return  builder.toString() ;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(randomAccessFile != null){
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return "Error loading file";
    }
}

