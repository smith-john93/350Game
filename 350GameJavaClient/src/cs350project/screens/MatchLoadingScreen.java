/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.communication.ClientCommand;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.Communication;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.MatchLoadingPanel;

/**
 *
 * @author Mark Masone
 */
public class MatchLoadingScreen extends Screen {

    private final MatchLoadingPanel matchLoadingPanel;
    private final Communication comm;
    
    public MatchLoadingScreen(PlayerCharacter player1, PlayerCharacter player2) {
        matchLoadingPanel = new MatchLoadingPanel();
        comm = Communication.getInstance();
        //comm.connect();
        //comm.listen();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void showPanel() {
        addPanel(matchLoadingPanel);
        comm.sendCommand(ClientCommand.CREATE_MATCH);
        System.out.println("command sent: create match");
    }
    
}
