/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import javax.swing.JFrame;

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
        gameFrame.setSize(1600, 900);
        GamePanel gamePanel = new GamePanel();
        KeyboardInput ki = new KeyboardInput();
        ki.addKeyboardInputListener(gamePanel);
        gameFrame.addKeyListener(ki);
        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
    }
    
}
