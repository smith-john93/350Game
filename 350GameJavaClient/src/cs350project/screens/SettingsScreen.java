/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.listeners.SettingsInputListener;
import cs350project.screens.panels.SettingsPanel;

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
    public void showPanel() {
        settingsPanel.addInputListener(this);
        addPanel(settingsPanel);
    }
    
}
