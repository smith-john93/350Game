/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;

/**
 *
 * @author Mark Masone
 */
public class MatchOverlay extends JComponent {
    
    private final Rectangle.Double healthBar1;
    private final Rectangle.Double healthBar2;
    private final Rectangle bounds;
    private final double healthBarWidth;
    private final double healthBarHeight;
    private final double paddingSide;
    
    public MatchOverlay() {
        Settings settings = Settings.getSettings();
        bounds = settings.getScreenBounds();
        double paddingTop = bounds.height * 0.02;
        paddingSide = bounds.width * 0.01;
        healthBarWidth = bounds.width * 0.4;
        healthBarHeight = bounds.height * 0.08;
        healthBar1 = new Rectangle.Double(
                paddingSide, 
                paddingTop,
                healthBarWidth,
                healthBarHeight
        );
        healthBar2 = new Rectangle.Double(
                bounds.width - paddingSide - healthBarWidth, 
                paddingTop,
                healthBarWidth,
                healthBarHeight
        );
    }
            
    @Override
    public void addNotify() {
        setBounds(bounds);
    }
    
    public void setHealth(int id, int health) {
        double width = healthBarWidth * health * 0.01;
        Rectangle.Double healthBar = healthBar1;
        if(id == 1) {
            healthBar = healthBar2;
            healthBar.x = bounds.width - paddingSide - width;
        }
        healthBar.width = width;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.setColor(Color.green);
        g2D.fill(healthBar1);
        g2D.fill(healthBar2);
    }
}
