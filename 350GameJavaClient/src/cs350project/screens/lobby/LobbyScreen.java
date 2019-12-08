/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.lobby;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.screens.MessageDialog;
import cs350project.communication.Communication;
import cs350project.communication.CommunicationException;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.KeyMap;
import cs350project.screens.mainmenu.MainMenuScreen;
import cs350project.screens.Screen;
import cs350project.screens.selection.SelectionScreen;
import cs350project.screens.settings.SettingsScreen;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class LobbyScreen extends Screen implements LobbyInputListener, IncomingCommandListener {
    
    private final LobbyPanel lobbyPanel;
    private final Communication comm;
    
    public LobbyScreen() {
        lobbyPanel = new LobbyPanel();
        comm = Communication.getInstance();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }
    
    @Override
    public JPanel getJPanel() {
        lobbyPanel.addInputListener(this);
        comm.addIncomingCommandListener(this);
        return lobbyPanel;
    }

    @Override
    public void createMatch(String matchName) {
        try {
            comm.createMatch(matchName);
        } catch (CommunicationException ex) {
            MessageDialog.showErrorMessage(ex.getMessage(), getClass());
        }
    }
    
    @Override
    public void joinMatch(String matchName) {
        
        try {
            //comm.addIncomingCommandListener(matchObjectManager);
            comm.joinMatch(matchName);
        } catch (CommunicationException ex) {
            Logger.getLogger(LobbyScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void settings() {
        GameFrame.getInstance().showScreen(new SettingsScreen());
    }
    
    @Override
    public void back() {
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        //System.out.println("command received: " + serverCommand);
        switch(serverCommand) {
            case UPDATE_LOBBY:
                try {
                    int action = dataInputStream.readByte();
                    //System.out.println("action byte received: " + action);
                    byte[] matchNameBytes = new byte[1000];
                    int bytesRead = dataInputStream.read(matchNameBytes);
                    String matchName = new String(matchNameBytes);
                    System.out.println("bytes read " + bytesRead);
                    matchName = matchName.substring(0, bytesRead);
                    //System.out.println("match name received: " + matchName);
                    switch (action) {
                        case 0:
                            lobbyPanel.removeMatch(matchName);
                            break;
                        case 1:
                            lobbyPanel.addMatch(matchName);
                            break;
                        default:
                            System.err.println("update lobby action not recognized");
                            break;
                    }
                } catch(IOException e) {
                    System.err.println(e.getMessage());
                }
                break;
            case SELECT_CHARACTER:
                try {
                    comm.removeIncomingCommandListener(this);
                    GameFrame.getInstance().showScreen(new SelectionScreen());
                } catch(IOException e) {
                    MessageDialog.showErrorMessage("Unable to open selection screen.", getClass());
                }
                break;
            case VALID_MATCH_NAME:
                //System.out.println("valid match name");
                break;
            case INVALID_MATCH_NAME:
                //System.out.println("invalid match name");
                break;
        }
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }

    
    
}
