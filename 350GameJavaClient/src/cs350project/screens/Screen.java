/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.Settings;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public abstract class Screen extends JComponent {
    
    @Override
    public void addNotify() {
        super.addNotify();
        setLayout(null);
        Settings settings = Settings.getSettings();
        Rectangle bounds = settings.getBounds();
        
        BackgroundImage backgroundImage = getBackgroundImage();
        backgroundImage.setBounds(bounds);
        
        JPanel jPanel = getJPanel();
        jPanel.setBounds(bounds);
        
        add(jPanel);
        add(backgroundImage);
    }
    
    public abstract BackgroundImage getBackgroundImage();
    public abstract JPanel getJPanel();
    public abstract KeyMap getKeyMap();
}
