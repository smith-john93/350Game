/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.screens.listeners.ScreenListener;
import cs350project.Communication;
import cs350project.Settings;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.MatchKeyMap;
import cs350project.screens.match.RedBall;
import cs350project.screens.panels.MatchPanel;
import javax.swing.Timer;
import cs350project.screens.listeners.match.MatchMenuInputListener;
import cs350project.screens.match.Combat;

/**
 *
 * @author Mark Masone
 */
public class MatchScreen extends Screen implements MatchMenuInputListener {

    private final MatchKeyMap matchKeyMap;
    private final MatchPanel matchPanel;
    private final RedBall redBall;
    private final Combat combat;
    private final Communication comm;
    
    public MatchScreen(PlayerCharacter player1, PlayerCharacter player2) {
        player1.setState(CharacterState.IDLE);
        matchKeyMap = new MatchKeyMap();
        matchPanel = new MatchPanel(player1,player2);
        comm = new Communication();
        redBall = new RedBall(player1);
        combat = new Combat();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return matchKeyMap;
    }

    @Override
    public void showPanel() {
        Settings settings = Settings.getSettings();
        int screenW = settings.getScreenWidth();
        int screenH = settings.getScreenHeight();
        redBall.setBounds(0,0,screenW,screenH);
        matchKeyMap.addInputListener(redBall);
        matchKeyMap.addInputListener(matchPanel);
        matchKeyMap.addInputListener(this);
        matchKeyMap.addInputListener(combat);
        matchPanel.addOutgoingMessageListener(comm);
        redBall.addOutgoingCommandListener(comm);
        matchPanel.add(redBall);
        matchPanel.setBackground("maps/whitehouse.png");
        addPanel(matchPanel);
        Timer tt = new Timer(17, redBall);
        tt.start();
        comm.connect();
    }
    
    @Override
    public void endGame() {
        showScreen(new SelectionScreen());
    }
}
