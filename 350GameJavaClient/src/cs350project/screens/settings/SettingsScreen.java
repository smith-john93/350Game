/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.CS350Project;
import cs350project.Settings;
import cs350project.menu.BackButtonPanel;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.mainmenu.MainMenuScreen;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mark Masone
 */
public class SettingsScreen extends Screen implements SettingsInputListener {

    private final SettingsPanel settingsPanel;
    
    public SettingsScreen() {
        settingsPanel = new SettingsPanel();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        settingsPanel.addInputListener(this);
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.setBackground(Settings.TRANSPARENT);
        gbc.gridy = 0;
        jPanel.add(settingsPanel,gbc);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        jPanel.add(new BackButtonPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        }),gbc);
        return jPanel;
    }

    @Override
    public void back() {
        CS350Project.showScreen(new MainMenuScreen());
    }
    
}
