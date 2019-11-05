/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.ClientCommand;
import cs350project.screens.listeners.ScreenListener;
import cs350project.Communication;
import cs350project.Settings;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.MatchKeyMap;
import cs350project.screens.panels.MatchPanel;
import javax.swing.Timer;
import cs350project.screens.listeners.match.MatchMenuInputListener;
import cs350project.screens.match.Combat;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class MatchScreen extends Screen implements MatchMenuInputListener {

    private final MatchKeyMap matchKeyMap;
    private final MatchPanel matchPanel;
    private final Combat combat;
    private final Communication comm;
    
    public MatchScreen(PlayerCharacter player1, PlayerCharacter player2) {
        player1.setState(CharacterState.IDLE);
        matchKeyMap = new MatchKeyMap();
        matchPanel = new MatchPanel(player1,player2);
        comm = new Communication();
        combat = new Combat(player1);
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
        matchKeyMap.addInputListener(matchPanel);
        matchKeyMap.addInputListener(this);
        matchKeyMap.addInputListener(combat);
        matchPanel.addOutgoingMessageListener(comm);
        matchPanel.addOutgoingCommandListener(comm);
        matchPanel.setBackground("maps/whitehouse.png");
        comm.addIncomingCommandListener(matchPanel);
        addPanel(matchPanel);
        comm.connect();
    }
    
    @Override
    public void endGame() {
        try {
            showScreen(new SelectionScreen());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
