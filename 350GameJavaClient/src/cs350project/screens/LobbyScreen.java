/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.listeners.LobbyInputListener;
import cs350project.screens.panels.LobbyPanel;

/**
 *
 * @author Mark Masone
 */
public class LobbyScreen extends Screen implements LobbyInputListener {
    
    private final LobbyPanel lobbyPanel;
    
    public LobbyScreen() {
        lobbyPanel = new LobbyPanel();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void showPanel() {
        lobbyPanel.addInputListener(this);
        addPanel(lobbyPanel);
    }

    @Override
    public void createMatch() {
        showScreen(new SelectionScreen());
    }

    @Override
    public void showSettings() {
        showScreen(new SettingsScreen());
    }
    
}
