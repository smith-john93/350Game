/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.Communication;
import cs350project.characters.PlayerCharacter;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.MatchKeyMap;
import cs350project.screens.keymaps.MenuKeyMapListener;
import cs350project.screens.match.RedBall;
import cs350project.screens.panels.MatchPanel;
import javax.swing.Timer;

/**
 *
 * @author Mark Masone
 */
public class MatchScreen extends Screen implements MenuKeyMapListener {

    private final MatchKeyMap matchKeyMap;
    private final MatchPanel matchPanel;
    private final RedBall redBall;
    private final Communication comm;
    
    public MatchScreen(PlayerCharacter player1, PlayerCharacter player2) {
        matchKeyMap = new MatchKeyMap();
        matchPanel = new MatchPanel(player1,player2);
        comm = new Communication();
        redBall = new RedBall(player1);
    }
    
    @Override
    public KeyMap getKeyMap() {
        return matchKeyMap;
    }

    @Override
    public void showPanel() {
        redBall.setBounds(0,0,1600,900);
        matchKeyMap.addKeyMapListener(redBall);
        matchKeyMap.addKeyMapListener(matchPanel);
        matchKeyMap.addKeyMapListener(this);
        matchPanel.addOutgoingMessageListener(comm);
        matchPanel.add(redBall);
        add(matchPanel);
        Timer tt = new Timer(17, redBall);
        tt.start();
        comm.connect();
    }
    
    @Override
    public void endGame() {
        for(ScreenListener screenListener : screenListeners) {
            screenListener.showScreen(new SelectionScreen());
        }
    }
}
