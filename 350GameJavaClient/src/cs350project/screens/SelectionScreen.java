/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;
import cs350project.CS350Project;
import cs350project.MessageDialog;
import cs350project.screens.keymaps.SelectionKeyMap;
import cs350project.screens.panels.SelectionPanel;
import cs350project.Music;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.listeners.SelectionInputListener;
import cs350project.screens.overlays.SelectionOverlay;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class SelectionScreen extends Screen implements SelectionInputListener {
    
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
    public void showPanel() {
        selectionKeyMap.addSelectionInputListener(this);
        SelectionOverlay selectionOverlay = selectionPanel.getSelectionOverlay();
        selectionOverlay.addSelectionInputListener(this);
        addPanel(selectionPanel);
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
        comm.connect();
        //comm.addIncomingCommandListener(this);
        music.stop();
        try {
            comm.joinMatch(player1.getCharacterClass());
            CS350Project.showScreen(new MatchScreen(player1,player2));
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Unable to join match.", getClass());
        }
        
        //CS350Project.showScreen(new MatchLoadingScreen(player1,player2));
    }

    @Override
    public void highlightNextRight() {
        selectionPanel.getSelectionOverlay().highlightNextRight();
    }

    @Override
    public void highlightNextLeft() {
        selectionPanel.getSelectionOverlay().highlightNextLeft();
    }
}
