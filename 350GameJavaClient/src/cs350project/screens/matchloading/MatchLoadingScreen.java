/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.matchloading;

import cs350project.communication.ClientCommand;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.Communication;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import javax.swing.JPanel;

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
    public void addNotify() {
        super.addNotify();
        comm.sendCommand(ClientCommand.CREATE_MATCH);
        System.out.println("command sent: create match");
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JPanel getJPanel() {
        return matchLoadingPanel;
    }
    
}
