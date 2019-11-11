/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.Settings;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mark Masone
 */
public class SettingPanel extends JPanel {
    @Override
    public void addNotify() {
        super.addNotify();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        setLayout(new GridBagLayout());
        setBackground(Color.red);
        setMaximumSize(new Dimension(440,80));
        
        JButton settingDown = new JButton("<");
        JButton settingUp = new JButton(">");
        settingDown.setFont(Settings.BUTTON_FONT);
        settingUp.setFont(Settings.BUTTON_FONT);
        
        JTextField settingText = new JTextField();
        settingText.setPreferredSize(new Dimension(300,0));
        
        gbc.gridx = 0;
        add(settingDown,gbc);
        gbc.gridx = 1;
        add(settingText,gbc);
        gbc.gridx = 2;
        add(settingUp,gbc);
    }
}
