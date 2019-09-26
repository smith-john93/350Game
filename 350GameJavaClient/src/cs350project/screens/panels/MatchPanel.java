/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;
import cs350project.Communication;
import cs350project.characters.PlayerCharacter;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cs350project.chat.*;
import cs350project.screens.keymaps.MatchKeyMapListener;
import cs350project.screens.match.RedBall;
import javax.swing.Timer;

/**
 *
 * @author Mark Masone
 */
public class MatchPanel extends Panel implements MatchKeyMapListener, ChatMessageFieldKeyListener {
    
    private final ChatMessageQueue chatMessageQueue;
    private final ChatMessageField chatMessageField;
    private ChatMessageFieldKeyAdapter chatMessageFieldKeyAdapter;
    private final Communication comm;
    private final RedBall redBall;
    
    public MatchPanel(PlayerCharacter player1, PlayerCharacter player2) {
        setBounds(0,0,1600,900);
        comm = new Communication();
        comm.connect();
        chatMessageQueue = new ChatMessageQueue();
        setLayout(null);
        chatMessageField = new ChatMessageField();
        chatMessageField.setVisible(false);
        redBall = new RedBall(player1);
        redBall.setBounds(0,0,1600,900);
        add(chatMessageQueue);
        add(chatMessageField);
        add(redBall);
        Timer tt = new Timer(17, redBall);
        tt.start();
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
            comm.sendMessage(message);
            chatMessageQueue.addMessage(new ChatMessage(message));
            repaint();
        }
        messageCancel();
    }

    @Override
    public void startMoveLeft() {
        redBall.startMoveLeft();
    }

    @Override
    public void startMoveRight() {
        redBall.startMoveRight();
    }

    @Override
    public void startJump() {
        redBall.startJump();
    }

    @Override
    public void endMoveLeft() {
        redBall.endMoveLeft();
    }

    @Override
    public void endMoveRight() {
        redBall.endMoveRight();
    }

    @Override
    public void endJump() {
        redBall.endJump();
    }

    @Override
    public void endGame() {
        for(PanelListener panelListener : panelListeners) {
            panelListener.panelClose();
        }
    }
}
