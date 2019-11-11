/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import javax.swing.JPanel;

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
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        settingsPanel.addInputListener(this);
        return settingsPanel;
    }
    
}
