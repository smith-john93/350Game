/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.Settings;
import cs350project.communication.OutgoingCommandListener;
import cs350project.communication.OutgoingMessageListener;
import cs350project.characters.PlayerCharacter;
import cs350project.chat.*;
import java.util.ArrayList;
import cs350project.screens.Panel;

/**
 *
 * @author Mark Masone
 */
public class MatchPanel extends Panel implements   
        ChatMessageFieldKeyListener, 
        ChatInputListener {
    
    private final ChatMessageQueue chatMessageQueue;
    private final ChatMessageField chatMessageField;
    private ChatMessageFieldKeyAdapter chatMessageFieldKeyAdapter;
    private final ArrayList<OutgoingMessageListener> outgoingMessageListeners;
    private final ArrayList<OutgoingCommandListener> outgoingCommandListeners;
    
    public MatchPanel() {
        outgoingCommandListeners = new ArrayList<>();
        outgoingMessageListeners = new ArrayList<>();
        chatMessageQueue = new ChatMessageQueue();
        chatMessageField = new ChatMessageField();
        chatMessageField.setVisible(false);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        setLayout(null);
        setBackground(Settings.TRANSPARENT);
        
        MatchOverlay matchOverlay = new MatchOverlay();
        //matchOverlay.setHealth(0, 40);
        //matchOverlay.setHealth(1, 75);
        add(matchOverlay);
        //setComponentZOrder(matchOverlay,0);

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
}
