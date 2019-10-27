/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;

import cs350project.screens.listeners.InputListener;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 * @param <E>
 */
public class Panel<E extends InputListener> extends JPanel {
    
    protected final ArrayList<E> inputListeners;
    
    public Panel() {
        inputListeners = new ArrayList<>();
    }
    
    public void paintBackground(Graphics2D g2d, String backgroundFile) {
        URL url = SelectionPanel.class.getResource(backgroundFile);
        try {
            BufferedImage background = ImageIO.read(url);
            g2d.drawImage(background, 0, 0, this);
        } catch(IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    public void addInputListener(E inputListener) {
        inputListeners.add(inputListener);
    }
}
