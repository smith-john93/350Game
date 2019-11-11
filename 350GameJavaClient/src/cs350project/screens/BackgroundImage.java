/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.screens.selection.SelectionPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark Masone
 */
public class BackgroundImage extends JComponent {
    
    private final String fileName;
    
    public BackgroundImage(String fileName) {
        this.fileName = fileName;
    }
    
    private void paintBackground(Graphics2D g2d, String backgroundFile) {
        URL url = SelectionPanel.class.getResource(backgroundFile);
        try {
            BufferedImage background = ImageIO.read(url);
            g2d.drawImage(background, 0, 0, this);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        paintBackground(g2d,fileName);
    }
}
