/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;

import cs350project.communication.OutgoingCommandListener;
import cs350project.communication.OutgoingMessageListener;
import cs350project.characters.CharacterState;
import cs350project.characters.PlayerCharacter;
import cs350project.chat.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import cs350project.screens.listeners.match.AttackInputListener;
import cs350project.screens.listeners.match.ChatInputListener;
import javax.swing.*;
import cs350project.screens.match.MatchObjectManagerListener;

/**
 *
 * @author Mark Masone
 */
public class MatchPanel extends Panel implements   
        ChatMessageFieldKeyListener, 
        ChatInputListener, 
        MatchObjectManagerListener {
    
    private final ChatMessageQueue chatMessageQueue;
    private final ChatMessageField chatMessageField;
    private ChatMessageFieldKeyAdapter chatMessageFieldKeyAdapter;
    private final ArrayList<OutgoingMessageListener> outgoingMessageListeners;
    private String background;
    private final PlayerCharacter playerCharacter;
    private final PlayerCharacter opponent;
    private final ArrayList<OutgoingCommandListener> outgoingCommandListeners;
    
    public MatchPanel(PlayerCharacter playerCharacter, PlayerCharacter opponent) {
        outgoingCommandListeners = new ArrayList<>();
        outgoingMessageListeners = new ArrayList<>();
        chatMessageQueue = new ChatMessageQueue();
        chatMessageField = new ChatMessageField();
        chatMessageField.setVisible(false);
        this.playerCharacter = playerCharacter;
        this.opponent = opponent;
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
    
    public void setBackground(String background) {
        this.background = background;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            Graphics2D g2d = (Graphics2D)g;
            //paintBackground(g2d,"/resources/" + background);
        }
    }

    @Override
    public void matchObjectChanged() {
        repaint();
    }
}
