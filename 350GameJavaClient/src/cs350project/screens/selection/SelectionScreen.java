/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.selection;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.Communication;
import cs350project.communication.CommunicationException;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.LoadingDialog;
import cs350project.screens.match.MatchObjectManager;
import cs350project.screens.match.MatchScreen;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class SelectionScreen extends Screen implements SelectionInputListener, IncomingCommandListener {
    
    private final SelectionKeyMap selectionKeyMap;
    private final SelectionPanel selectionPanel;
    private final Communication comm;
    private PlayerCharacter player1;
    private LoadingDialog loadingDialog;
    
    public SelectionScreen() throws IOException {
        selectionPanel = new SelectionPanel();
        selectionKeyMap = new SelectionKeyMap();
        comm = Communication.getInstance();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return selectionKeyMap;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        selectionKeyMap.addSelectionInputListener(this);
        SelectionOverlay selectionOverlay = selectionPanel.getSelectionOverlay();
        selectionOverlay.addSelectionInputListener(this);
    }

    @Override
    public void characterSelected() {
        loadingDialog = createLoadingDialog(new Loader() {
            @Override
            public void load() throws CommunicationException {
                player1 = selectionPanel.getPlayer1Selection();
                MatchObjectManager.getInstance().setPlayer(player1);
                comm.addIncomingCommandListener(MatchObjectManager.getInstance());
                comm.characterSelected(player1.getCharacterType());
            }
        },"Waiting for other player...",false,null);
    }

    @Override
    public void highlightNextRight() {
        selectionPanel.getSelectionOverlay().highlightNextRight();
        repaint();
    }

    @Override
    public void highlightNextLeft() {
        selectionPanel.getSelectionOverlay().highlightNextLeft();
        repaint();
    }
    
    @Override
    public void highlightCharacter() {
        repaint();
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }

    @Override
    public JPanel getJPanel() {
        comm.addIncomingCommandListener(this);
        return selectionPanel;
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        //System.out.println("selection screen received command: " + serverCommand);
        if(serverCommand == ServerCommand.START_MATCH) {
            loadingDialog.close();
            comm.removeIncomingCommandListener(this);
            GameFrame.getInstance().showScreen(new MatchScreen(player1));
        }
    }
}
