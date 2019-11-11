/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.Settings;
import cs350project.screens.selection.SelectionPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
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
    
    private final BufferedImage background;
    
    public BackgroundImage(String fileName) {
        background = getBackgroundImage(fileName);
    }
    
    private BufferedImage getBackgroundImage(String backgroundFile) {
        URL url = SelectionPanel.class.getResource(backgroundFile);
        try {
            int width = Settings.getSettings().getScreenWidth();
            int height = Settings.getSettings().getScreenHeight();
            BufferedImage bufferedImage = ImageIO.read(url);
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2D = resizedImage.createGraphics();

            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2D.drawImage(bufferedImage, 0, 0, width, height, null);
            g2D.dispose();
            
            return resizedImage;
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Unable to load background image.",getClass());
        }
        return null;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(background, 0, 0, this);
        }
    }
}
