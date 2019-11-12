/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.KeyMap;
import cs350project.screens.selection.SelectionPanel;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark Masone
 */
public class GameFrame extends JFrame {
    
    private KeyMap keyMap;

    private static GameFrame gameFrame;

    private GameFrame() {

    }

    public static GameFrame getInstance() {
        if(gameFrame == null) {
            gameFrame = new GameFrame();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setTitle("CS350 Project");
            gameFrame.pack();
            Settings settings = Settings.getSettings();
            Dimension screenDimension = settings.getScreenDimension();
            gameFrame.setSize(screenDimension);
        }
        return gameFrame;
    }
    
    @Override
    public void setSize(Dimension screenDimension) {
        Insets insets = getInsets();
        int windowW = screenDimension.width + insets.left + insets.right;
        int windowH = screenDimension.height + insets.top + insets.bottom;
        super.setSize(windowW, windowH);
    }
    
    public void setKeyMap(KeyMap keyMap) {
        if(keyMap != null) {
            if (this.keyMap != null) {
                removeKeyListener(this.keyMap);
            }
            this.keyMap = keyMap;
            addKeyListener(keyMap);
        }
    }
}
