/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;
import cs350project.Screen;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.SelectionKeyMap;
import cs350project.screens.panels.SelectionPanel;
import cs350project.Music;
import cs350project.ScreenListener;
import cs350project.screens.panels.Panel;
import cs350project.screens.panels.PanelListener;

/**
 *
 * @author Mark Masone
 */
public class SelectionScreen extends Screen implements PanelListener {
    
    private final SelectionKeyMap selectionKeyMap;
    private final SelectionPanel selectionPanel;
    private final Music music;
    
    public SelectionScreen() {
        selectionPanel = new SelectionPanel();
        selectionKeyMap = new SelectionKeyMap();
        selectionKeyMap.addKeyMapListener(selectionPanel);
        music = new Music();
        music.playSelectionPanelMusic();
        add(selectionPanel);
    }
    
    @Override
    public KeyMap getKeyMap() {
        return selectionKeyMap;
    }
    
    @Override
    public Panel getPanel() {
        return selectionPanel;
    }

    @Override
    public void panelClose() {
        for(ScreenListener screenListener : screenListeners) {
            MatchScreen matchScreen = new MatchScreen(
                    selectionPanel.getPlayer1Selection(),
                    selectionPanel.getPlayer2Selection()
            );
            screenListener.showScreen(matchScreen);
            music.stop();
        }
    }
}
