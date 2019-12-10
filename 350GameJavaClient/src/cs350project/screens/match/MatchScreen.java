/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.screens.lobby.LobbyScreen;
import cs350project.screens.MessageDialog;
import cs350project.communication.Communication;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.CommunicationException;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class MatchScreen extends Screen implements 
        MatchMenuInputListener, 
        MovementInputListener,
        AttackInputListener,
        MatchObjectManagerListener {

    private final MatchKeyMap matchKeyMap;
    private final MatchPanel matchPanel;
    private final Communication comm;
    private final MatchObjectManager matchObjectManager;
    private final BackgroundImage backgroundImage;
    private final MatchOverlay matchOverlay;
    
    /* It is important that this class keeps its own stateCode.
    The stateCode in PlayerCharacter will not update quickly enough
    to keep up with multiple inputs in rapid succession, thus causing
    buggy character movements.
    */
    private int stateCode;
    
    public MatchScreen(PlayerCharacter player) {
        matchKeyMap = new MatchKeyMap(Settings.getSettings().getKeyMappings());
        matchPanel = new MatchPanel();
        comm = Communication.getInstance();
        matchObjectManager = MatchObjectManager.getInstance();
        matchObjectManager.setPlayer(player);
        backgroundImage = new BackgroundImage("maps/WhiteHouse.png");
        matchOverlay = new MatchOverlay();
        Thread repaintThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    matchOverlay.setHealth(player.getPlayerID(),player.getHealth());
                    PlayerCharacter opponent = matchObjectManager.getOpponent();
                    matchOverlay.setHealth(opponent.getPlayerID(),opponent.getHealth());
                    repaint();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MatchScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        repaintThread.start();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return matchKeyMap;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        matchKeyMap.addInputListener(matchPanel);
        matchKeyMap.addInputListener(this);
        matchPanel.addOutgoingMessageListener(comm);
        matchPanel.addOutgoingCommandListener(comm);
        matchPanel.add(matchOverlay);
        matchObjectManager.addMatchObjectManagerListener(this);
        for(MatchObject matchObject : matchObjectManager.getMatchObjects()) {
            if(matchObject != null) {
                matchPanel.add(matchObject);
                //System.out.println("match object added to match panel: " + matchObject.getClass().getSimpleName());
            }
        }
    }
    
    @Override
    public void endGame() {
        MatchObjectManager.getInstance().unsetAll();
        GameFrame.getInstance().showScreen(new LobbyScreen());
    }
    
    private void updateMatch() {
        try {
            comm.updateMatch(stateCode);
        } catch(CommunicationException e) {
            MessageDialog.showErrorMessage(e.getMessage(), getClass());
        }
    }
    
    private void enableState(int stateCode) {
        this.stateCode |= stateCode;
    }

    private void disableState(int stateCode) {
        this.stateCode ^= stateCode;
    }
    
    @Override
    public void startMovement(int stateCode) {
        switch(stateCode) {
            case CharacterState.JUMPING:
            case CharacterState.MOVING_LEFT:
            case CharacterState.CROUCHING:
            case CharacterState.MOVING_RIGHT:
            case CharacterState.BLOCKING:
                enableState(stateCode);
                updateMatch();
                break;
        }
    }
    
    @Override
    public void endMovement(int stateCode) {
        switch(stateCode) {
            case CharacterState.JUMPING:
            case CharacterState.MOVING_LEFT:
            case CharacterState.CROUCHING:
            case CharacterState.MOVING_RIGHT:
            case CharacterState.BLOCKING:
                disableState(stateCode);
                updateMatch();
                break;
        }
    }
    
    private void attack(int stateCode) {
        enableState(stateCode);
        updateMatch();
        disableState(stateCode);
        //updateMatch();
    }
    
    @Override
    public void punch() {
        attack(CharacterState.PUNCH);
    }

    @Override
    public void highKick() {
        attack(CharacterState.HIGH_KICK);
    }

    @Override
    public void lowKick() {
        attack(CharacterState.LOW_KICK);
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public JPanel getJPanel() {
        return matchPanel;
    }

    @Override
    public void matchObjectChanged() {
        repaint();
    }
}
