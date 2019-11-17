/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.CS350Project;
import cs350project.MessageDialog;
import cs350project.communication.Communication;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.keymaps.MatchKeyMap;
import cs350project.screens.listeners.match.AttackInputListener;
import cs350project.screens.panels.MatchPanel;
import cs350project.screens.listeners.match.MatchMenuInputListener;
import cs350project.screens.listeners.match.MovementInputListener;
import cs350project.screens.match.Combat;
import cs350project.screens.match.MatchObject;
import cs350project.screens.match.MatchObjectManager;
import java.io.IOException;
import cs350project.screens.match.MatchObjectManagerListener;

/**
 *
 * @author Mark Masone
 */
public class MatchScreen extends Screen implements 
        MatchMenuInputListener, 
        MovementInputListener,
        AttackInputListener {

    private final MatchKeyMap matchKeyMap;
    private final MatchPanel matchPanel;
    private final Combat combat;
    private final Communication comm;
    private final MatchObjectManager matchObjectManager;
    private final PlayerCharacter player;
    
    /* It is important that this class keeps its own stateCode.
    The stateCode in PlayerCharacter will not update quickly enough
    to keep up with multiple inputs in rapid succession, thus causing
    buggy character movements.
    */
    private int stateCode;
    
    public MatchScreen(PlayerCharacter player, PlayerCharacter opponent) {
        this.player = player;
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
    }
    
    @Override
    public void endGame() {
        try {
            MatchObjectManager.getInstance().clear();
            CS350Project.showScreen(new SelectionScreen());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void updateMatch() {
        try {
            comm.updateMatch(this.stateCode);
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Unable to update match.", getClass());
        }
    }
    
    private void enableState(int stateCode) {
        this.stateCode |= stateCode;
    }

    private void disableState(int stateCode) {
        this.stateCode ^= stateCode;
    }
    
    private void startMove(int stateCode) {
        enableState(stateCode);
        updateMatch();
    }
    
    private void endMove(int stateCode) {
        disableState(stateCode);
        updateMatch();
    }
    
    @Override
    public void startJump() {
        startMove(CharacterState.JUMPING);
    }

    @Override
    public void endJump() {
        endMove(CharacterState.JUMPING);
    }

    @Override
    public void startMoveLeft() {
        startMove(CharacterState.MOVING_LEFT);
    }

    @Override
    public void endMoveLeft() {
        endMove(CharacterState.MOVING_LEFT);
    }

    @Override
    public void startCrouch() {
        startMove(CharacterState.CROUCHING);
    }

    @Override
    public void endCrouch() {
        endMove(CharacterState.CROUCHING);
    }

    @Override
    public void startMoveRight() {
        startMove(CharacterState.MOVING_RIGHT);
    }

    @Override
    public void endMoveRight() {
        endMove(CharacterState.MOVING_RIGHT);
    }

    @Override
    public void startBlock() {
        startMove(CharacterState.BLOCKING);
    }

    @Override
    public void endBlock() {
        endMove(CharacterState.BLOCKING);
    }
    
    private void attack(int stateCode) {
        enableState(stateCode);
        updateMatch();
        disableState(stateCode);
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
}
