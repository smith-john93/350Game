/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Dimension;

/**
 *
 * @author Mark Masone
 */
public class CS350Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setTitle("CS350 Project");
        
        GamePanel gamePanel = new GamePanel();
        KeyboardAdapter ki = new KeyboardAdapter();
        ki.addKeyboardListener(gamePanel);
        gameFrame.addKeyListener(ki);
        gameFrame.add(gamePanel);
        gameFrame.setSize(1600, 900);
        gameFrame.setVisible(true);
    }
    
}
