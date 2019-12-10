/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.Settings;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mark Masone
 */
class IncrementalSetting extends JPanel {
    
    private final SettingsPanel settingsPanel;
    private final JTextField settingText;
    
    IncrementalSetting(SettingsPanel settingsPanel) {
        this.settingsPanel = settingsPanel;
        settingText = new JTextField();
        settingText.setFont(Settings.FONT_SETTING);
        settingText.setPreferredSize(new Dimension(200,0));
    }
    
    public void setText(String text) {
        //System.out.println(text);
        settingText.setText(text);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;
        
        setLayout(new GridBagLayout());
        setBackground(Settings.TRANSPARENT);
        
        JButton settingDown = new JButton("<");
        JButton settingUp = new JButton(">");
        int fontHeight = getFontMetrics(Settings.BUTTON_FONT_MEDIUM).getHeight();
        Dimension buttonSize = new Dimension(fontHeight,fontHeight);
        settingDown.setFont(Settings.BUTTON_FONT_MEDIUM);
        settingUp.setFont(Settings.BUTTON_FONT_MEDIUM);
        settingDown.setMaximumSize(buttonSize);
        settingUp.setMaximumSize(buttonSize);
        
        IncrementalSetting incrementalSetting = this;
        
        settingDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsPanel.settingDecrement(incrementalSetting);
            }
        });
        settingUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsPanel.settingIncrement(incrementalSetting);
            }
        });
        
        gbc.gridx = 0;
        add(settingDown,gbc);
        gbc.gridx = 1;
        //gbc.weightx = 1;
        add(settingText,gbc);
        gbc.gridx = 2;
        //gbc.weightx = 0;
        add(settingUp,gbc);
    }
}
