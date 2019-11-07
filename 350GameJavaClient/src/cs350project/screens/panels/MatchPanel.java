/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;
import cs350project.MessageDialog;
import cs350project.communication.OutgoingCommandListener;
import cs350project.communication.OutgoingMessageListener;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.chat.*;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import cs350project.screens.listeners.match.AttackInputListener;
import cs350project.screens.listeners.match.ChatInputListener;
import cs350project.screens.listeners.match.MovementInputListener;
import cs350project.screens.match.Platform;

import javax.swing.*;
import cs350project.screens.match.MatchObject;
import cs350project.screens.match.MatchObjectManagerListener;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class MatchPanel extends Panel implements 
        MovementInputListener, 
        AttackInputListener, 
        ChatMessageFieldKeyListener, 
        ChatInputListener, 
        MatchObjectManagerListener {
    
    private final ChatMessageQueue chatMessageQueue;
    private final ChatMessageField chatMessageField;
    private ChatMessageFieldKeyAdapter chatMessageFieldKeyAdapter;
    private final ArrayList<OutgoingMessageListener> outgoingMessageListeners;
    private String background;
    //private final Platform[] platforms;
    private int stateCode = 0;
    private final PlayerCharacter playerCharacter;
    private final PlayerCharacter opponent;
    private final HashMap<Integer,Timer> attackTimers;
    private final ArrayList<OutgoingCommandListener> outgoingCommandListeners;
    
    public MatchPanel(PlayerCharacter playerCharacter, PlayerCharacter opponent) {
        outgoingCommandListeners = new ArrayList<>();
        outgoingMessageListeners = new ArrayList<>();
        chatMessageQueue = new ChatMessageQueue();
        chatMessageField = new ChatMessageField();
        chatMessageField.setVisible(false);
        this.playerCharacter = playerCharacter;
        this.opponent = opponent;

        attackTimers = new HashMap<>();
        int[] attackStates = {
                CharacterState.PUNCH,
                CharacterState.HIGH_KICK,
                CharacterState.LOW_KICK
        };
        for(int attackState : attackStates) {
            int attackFramesCount = playerCharacter.getFramesCount(attackState);
            int attackTime = PlayerCharacter.FRAME_DELAY * (attackFramesCount + 1);
            Timer attackTimer = new Timer(attackTime, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    playerCharacter.disableState(attackState);
                }
            });
            attackTimer.setRepeats(false);
            attackTimers.put(attackState, attackTimer);
        }
    }
    
    @Override
    public void addNotify() {
        super.addNotify();

        add(chatMessageQueue);
        add(chatMessageField);

        //add(playerCharacter);
        //add(opponent);
    }
    
    public void addOutgoingMessageListener(OutgoingMessageListener outgoingMessageListener) {
        outgoingMessageListeners.add(outgoingMessageListener);
    }

    public void addOutgoingCommandListener(OutgoingCommandListener outgoingCommandListener) {
        outgoingCommandListeners.add(outgoingCommandListener);
    }
    
    @Override
    public void messageStart() {
        if(!chatMessageField.isVisible()) {
            chatMessageFieldKeyAdapter = new ChatMessageFieldKeyAdapter(this);
            chatMessageField.addKeyListener(chatMessageFieldKeyAdapter);
            chatMessageField.setVisible(true);
        }
    }
    
    @Override
    public void messageCancel() {
        chatMessageField.removeKeyListener(chatMessageFieldKeyAdapter);
        chatMessageField.setVisible(false);
        chatMessageField.clearText();
        this.transferFocusUpCycle();
    }
    
    @Override
    public void messageCommit() {
        String message = chatMessageField.getText();
        if(!message.equals("")) {
            for(OutgoingMessageListener outgoingMessageListener : outgoingMessageListeners) {
                outgoingMessageListener.sendMessage(message);
            }
            chatMessageQueue.addMessage(new ChatMessage(message));
            repaint();
        }
        messageCancel();
    }

    private void sendCommand(ClientCommand clientCommand) {
        for(OutgoingCommandListener outgoingCommandListener : outgoingCommandListeners) {
            outgoingCommandListener.sendCommand(clientCommand);
        }
    }
    
    private void sendCharacterState(int stateCode) {
        try {
            Communication.getInstance().sendCharacterState(stateCode);
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Unable to send character state.", getClass());
        }
    }

    private void enableState(int stateCode) {
        this.stateCode |= stateCode;
        playerCharacter.enableState(stateCode);
        sendCharacterState(this.stateCode);
    }

    private void disableState(int stateCode) {
        this.stateCode ^= stateCode;
        playerCharacter.disableState(stateCode);
        sendCharacterState(this.stateCode);
    }

    private void attack(int attackState) {
        sendCharacterState(attackState);
        Timer attackTimer = attackTimers.get(attackState);
        if(!attackTimer.isRunning()) {
            playerCharacter.enableState(attackState);
            attackTimer.start();
        }
    }

    @Override
    public void startJump() {
        enableState(CharacterState.JUMPING);
    }

    @Override
    public void endJump() {
        disableState(CharacterState.JUMPING);
    }

    @Override
    public void startMoveLeft() {
        enableState(CharacterState.MOVING_LEFT);
    }

    @Override
    public void endMoveLeft() {
        disableState(CharacterState.MOVING_LEFT);
    }

    @Override
    public void startCrouch() {
        enableState(CharacterState.CROUCHING);
    }

    @Override
    public void endCrouch() {
        disableState(CharacterState.CROUCHING);
    }

    @Override
    public void startMoveRight() {
        enableState(CharacterState.MOVING_RIGHT);
    }

    @Override
    public void endMoveRight() {
        disableState(CharacterState.MOVING_RIGHT);
    }

    @Override
    public void startBlock() {
        enableState(CharacterState.BLOCKING);
    }

    @Override
    public void endBlock() {
        disableState(CharacterState.BLOCKING);
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
    
    public void setBackground(String background) {
        this.background = background;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            Graphics2D g2d = (Graphics2D)g;
            paintBackground(g2d,"/resources/" + background);
        }

        boolean movingRight = (stateCode & CharacterState.MOVING_RIGHT) == CharacterState.MOVING_RIGHT;
        boolean movingLeft = (stateCode & CharacterState.MOVING_LEFT) == CharacterState.MOVING_LEFT;

        if(playerCharacter != null) {
            if(movingRight) {
                playerCharacter.setDirection(1);
            } else if(movingLeft) {
                playerCharacter.setDirection(-1);
            }
        }
    }

    @Override
    public void matchObjectChanged() {
        repaint();
    }
}
