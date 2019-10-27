/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;

import cs350project.screens.listeners.SettingsInputListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Mark Masone
 */
public class SettingsPanel extends Panel<SettingsInputListener> {
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        paintBackground(g2d,"/resources/background.jpg");
    }
}
