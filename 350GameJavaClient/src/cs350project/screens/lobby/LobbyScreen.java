/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.lobby;

import cs350project.CS350Project;
import cs350project.screens.MessageDialog;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.KeyMap;
import cs350project.screens.match.MatchObjectManager;
import cs350project.screens.mainmenu.MainMenuScreen;
import cs350project.screens.Screen;
import cs350project.screens.selection.SelectionScreen;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JPanel;

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
    public void addNotify() {
        super.addNotify();
        lobbyPanel.addInputListener(this);
    }

    @Override
    public void createMatch() {
        
    }
    
    @Override
    public void joinMatch() {
        comm.connect();
        //comm.listen();
        comm.addIncomingCommandListener(this);
        comm.addIncomingCommandListener(matchObjectManager);
        comm.sendCommand(ClientCommand.CREATE_MATCH);
    }
    
    @Override
    public void back() {
        CS350Project.showScreen(new MainMenuScreen());
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

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        return lobbyPanel;
    }
    
}
