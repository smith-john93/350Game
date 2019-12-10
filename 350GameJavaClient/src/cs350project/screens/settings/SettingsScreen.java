/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.communication.Communication;
import cs350project.communication.CommunicationException;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.lobby.LobbyScreen;
import java.io.DataInputStream;
import javax.swing.JPanel;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark Masone
 */
public class SettingsScreen extends Screen implements SettingsInputListener, IncomingCommandListener {

    private final SettingsPanel settingsPanel;
    private final HashMap<Integer, Integer> keyMappings;
    private final Communication comm;

    public SettingsScreen() {
        settingsPanel = new SettingsPanel();
        keyMappings = new HashMap<>();
        keyMappings.putAll(Settings.getSettings().getKeyMappings());
        comm = Communication.getInstance();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        comm.addIncomingCommandListener(this);
    }

    @Override
    public JPanel getJPanel() {
        settingsPanel.addInputListener(this);
        settingsPanel.setKeyMappings(keyMappings);
        return settingsPanel;
    }

    @Override
    public void back() {
        GameFrame.getInstance().showScreen(new LobbyScreen());
    }

    @Override
    public void save() {
        Settings settings = Settings.getSettings();
        settings.setKeyMappings(keyMappings);
        try {
            comm.sendActionMappings(settings.getActionMappings());
        } catch (CommunicationException ex) {
            Logger.getLogger(SettingsScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        if(serverCommand == ServerCommand.SAVED_KEY_MAPPINGS) {
            System.out.println("KEY MAPPINGS SAVED");
        }
    }
}
