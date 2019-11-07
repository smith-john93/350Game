/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.CS350Project;
import cs350project.MessageDialog;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.listeners.LobbyInputListener;
import cs350project.screens.match.MatchObjectManager;
import cs350project.screens.panels.LobbyPanel;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class LobbyScreen extends Screen implements LobbyInputListener, IncomingCommandListener {
    
    private final LobbyPanel lobbyPanel;
    private final Communication comm;
    private final MatchObjectManager matchObjectManager;
    
    public LobbyScreen() {
        lobbyPanel = new LobbyPanel();
        comm = Communication.getInstance();
        matchObjectManager = MatchObjectManager.getInstance();
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
        comm.connect();
        //comm.listen();
        comm.addIncomingCommandListener(this);
        comm.addIncomingCommandListener(matchObjectManager);
        comm.sendCommand(ClientCommand.CREATE_MATCH);
    }

    @Override
    public void showSettings() {
        CS350Project.showScreen(new SettingsScreen());
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        if(serverCommand == ServerCommand.SELECT_CHARACTER) {
            try {
                comm.removeIncomingCommandListener(this);
                CS350Project.showScreen(new SelectionScreen());
            } catch(IOException e) {
                MessageDialog.showErrorMessage("Unable to open selection screen.", getClass());
            }
        }
    }
    
}
