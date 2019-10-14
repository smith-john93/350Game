/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class Panel extends JPanel {
    public void paintBackground(Graphics2D g2d, String backgroundFile) {
        URL url = SelectionPanel.class.getResource(backgroundFile);
        try {
            BufferedImage background = ImageIO.read(url);
            g2d.drawImage(background, 0, 0, this);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
