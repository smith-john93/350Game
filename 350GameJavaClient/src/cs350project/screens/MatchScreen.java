/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.characters.PlayerCharacter;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.MatchKeyMap;
import cs350project.screens.panels.MatchPanel;
import cs350project.screens.panels.Panel;

/**
 *
 * @author Mark Masone
 */
public class MatchScreen extends Screen {

    private final MatchKeyMap matchKeyMap;
    private final MatchPanel matchPanel;
    
    public MatchScreen(PlayerCharacter player1, PlayerCharacter player2) {
        matchKeyMap = new MatchKeyMap();
        matchPanel = new MatchPanel(player1,player2);
        matchKeyMap.addKeyMapListener(matchPanel);
        add(matchPanel);
    }
    
    @Override
    public KeyMap getKeyMap() {
        return matchKeyMap;
    }

    @Override
    public Panel getPanel() {
        return matchPanel;
    }

    @Override
    public void panelClose() {
        for(ScreenListener screenListener : screenListeners) {
            screenListener.showScreen(new SelectionScreen());
        }
    }
    
}
