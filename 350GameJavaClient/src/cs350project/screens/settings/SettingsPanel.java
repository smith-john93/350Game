/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.screens.Panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mark Masone
 */
public class SettingsPanel extends Panel<SettingsInputListener> {
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        setBorder(new LineBorder(Color.white,5));
        
        SettingPanel resolutionSetting = new SettingPanel();
        
        resolutionSetting.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(resolutionSetting);
        //add(Box.createVerticalGlue());
        add(new SettingPanel());
        add(new SettingPanel());
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        paintBackground(g2d,"/resources/background.jpg");
    }
}
