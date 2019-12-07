/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.MessageDialog;
import cs350project.screens.mainmenu.MainMenuScreen;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.HashMap;
import java.util.Iterator;

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
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }

    @Override
    public void save() {
        Settings settings = Settings.getSettings();
        settings.setKeyMappings(keyMappings);
        HashMap<Integer,ArrayList<Integer>> actionMappings = settings.getActionMappings();
        try {
            comm.sendClientCommand(ClientCommand.SAVE_KEY_MAPPINGS);
            for(int stateCode : actionMappings.keySet()) {
                comm.sendCharacterState(stateCode);
                StringBuilder keyCodeCSV = new StringBuilder();
                ArrayList<Integer> keyCodes = actionMappings.get(stateCode);
                for(int i = 0; i < keyCodes.size(); i++) {
                    if(i != 0) {
                        keyCodeCSV.append(",");
                    }
                    keyCodeCSV.append(keyCodes.get(i));
                }
                keyCodeCSV.append('\0');
                String output = keyCodeCSV.toString();
                comm.sendString(output);
                System.out.println(output);
                System.out.println("output length " + output.length());
            }
            comm.sendClientCommand(ClientCommand.SAVE_ALL_MAPPINGS);
        } catch(IOException e) {
            MessageDialog.showErrorMessage(
                    "Unable to send key mappings to server. "
                    + "Key mappings will not persist after the game is restarted.", 
                    getClass()
            );
        }
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        if(serverCommand == ServerCommand.SAVED_KEY_MAPPINGS) {
            System.out.println("KEY MAPPINGS SAVED");
        }
    }
}
