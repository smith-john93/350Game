/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;
import cs350project.screens.listeners.ScreenListener;
import cs350project.screens.keymaps.SelectionKeyMap;
import cs350project.screens.panels.SelectionPanel;
import cs350project.Music;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.listeners.SelectionInputListener;
import cs350project.screens.overlays.SelectionOverlay;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class SelectionScreen extends Screen implements SelectionInputListener {
    
    private final SelectionKeyMap selectionKeyMap;
    private final SelectionPanel selectionPanel;
    private final Music music;
    
    public SelectionScreen() throws IOException {
        selectionPanel = new SelectionPanel((short)1);
        selectionKeyMap = new SelectionKeyMap();
        music = new Music();
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
        MatchScreen matchScreen = new MatchScreen(
                selectionPanel.getPlayer1Selection(),
                selectionPanel.getPlayer2Selection()
        );
        showScreen(matchScreen);
        music.stop();
        //showScreen(new MatchLoadingScreen());
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
