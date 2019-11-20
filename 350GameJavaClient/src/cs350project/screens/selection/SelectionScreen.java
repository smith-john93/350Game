/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.selection;

import cs350project.GameFrame;
import cs350project.Music;
import cs350project.Settings;
import cs350project.characters.CharacterType;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.Communication;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.MessageDialog;
import cs350project.screens.lobby.LobbyScreen;
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
    private final Music music;
    private final Communication comm;
    private PlayerCharacter player1;
    private PlayerCharacter player2;
    
    public SelectionScreen() throws IOException {
        selectionPanel = new SelectionPanel((short)1);
        selectionKeyMap = new SelectionKeyMap();
        music = new Music();
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
        music.playSelectionPanelMusic();
    }

    @Override
    public void characterSelected() {
        player1 = selectionPanel.getPlayer1Selection();
        player2 = selectionPanel.getPlayer2Selection();
        /*MatchScreen matchScreen = new MatchScreen(
                selectionPanel.getPlayer1Selection(),
                selectionPanel.getPlayer2Selection()
        );
        showScreen(matchScreen);*/
        //comm.connect();
        //comm.addIncomingCommandListener(this);
        //music.stop();
        comm.addIncomingCommandListener(MatchObjectManager.getInstance());
        comm.addIncomingCommandListener(this);
        comm.characterSelected(player1.getCharacterType());
        System.out.println("waiting for other player to select");
    }

    @Override
    public void highlightNextRight() {
        selectionPanel.getSelectionOverlay().highlightNextRight();
    }

    @Override
    public void highlightNextLeft() {
        selectionPanel.getSelectionOverlay().highlightNextLeft();
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
        System.out.println("selection screen received command: " + serverCommand);
        if(serverCommand == ServerCommand.START_MATCH) {
            comm.removeIncomingCommandListener(this);
            comm.removeIncomingCommandListener(MatchObjectManager.getInstance());
            GameFrame.getInstance().showScreen(new MatchScreen(player1,player2));
        }
    }
}
