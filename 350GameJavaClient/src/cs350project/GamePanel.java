/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.Shape;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Mark Masone
 */
public class GamePanel extends JPanel implements KeyboardInputListener {
    
    private final ChatMessageQueue chatMessageQueue;
    private boolean captureMessage;
    private String message;
    
    public GamePanel() {
        captureMessage = false;
        message = "";
        chatMessageQueue = new ChatMessageQueue();
        chatMessageQueue.addMessage(new ChatMessage("WOW"));
        chatMessageQueue.addMessage(new ChatMessage("YOU'RE WINNER !"));
        chatMessageQueue.addMessage(new ChatMessage("You are on the way to destruction"));
        chatMessageQueue.addMessage(new ChatMessage("All your base are belong to us"));
        chatMessageQueue.addMessage(new ChatMessage("I'm the great annihilator"));
        chatMessageQueue.addMessage(new ChatMessage("I slay"));
    }
    
    @Override
    public void keyTyped(char value) {
        if(captureMessage) {
            message += value;
        }
    }
    
    @Override
    public void messageKeyPressed() {
        System.out.println("start new message");
        captureMessage = true;
    }
    
    @Override
    public void messageCommit() {
        System.out.println("commit new message");
        if(captureMessage) {
            chatMessageQueue.addMessage(new ChatMessage(message));
            captureMessage = false;
            message = "";
            repaint();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        chatMessageQueue.draw(g2d);
    }
}
