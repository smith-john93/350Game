/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.CS350Project;
import cs350project.communication.Communication;
import cs350project.Settings;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.MatchKeyMap;
import cs350project.screens.panels.MatchPanel;
import cs350project.screens.listeners.match.MatchMenuInputListener;
import cs350project.screens.match.Combat;
import cs350project.screens.match.MatchObject;
import cs350project.screens.match.MatchObjectManager;
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
    private final MatchObjectManager matchObjectManager;
    
    public MatchScreen(PlayerCharacter player, PlayerCharacter opponent) {
        player.setState(CharacterState.IDLE);
        matchKeyMap = new MatchKeyMap();
        matchPanel = new MatchPanel(player,opponent);
        comm = Communication.getInstance();
        combat = new Combat(player);
        matchObjectManager = MatchObjectManager.getInstance();
        matchObjectManager.setPlayerCharacters(player, opponent);
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
        matchObjectManager.addMatchObjectManagerListener(matchPanel);
        for(MatchObject matchObject : matchObjectManager.getMatchObjects()) {
            if(matchObject != null) {
                matchPanel.add(matchObject);
            }
        }
        addPanel(matchPanel);
        //comm.connect();
        //comm.listen();
    }
    
    @Override
    public void endGame() {
        try {
            CS350Project.showScreen(new SelectionScreen());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
