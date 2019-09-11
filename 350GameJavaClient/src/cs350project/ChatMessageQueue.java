/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import java.util.LinkedList;
import java.awt.Graphics2D;

/**
 *
 * @author Mark Masone
 */
public class ChatMessageQueue {
    
    private final LinkedList<ChatMessage> chatMessages;
    
    public ChatMessageQueue() {
        chatMessages = new LinkedList<>();
    }
    
    public void addMessage(ChatMessage chatMessage) {
        chatMessages.addFirst(chatMessage);
        if(chatMessages.size() > 6) {
            chatMessages.removeLast();
        }
    }
    
    public void draw(Graphics2D g2d) {
        int y = 854;
        double opacity = 1.15;
        for(ChatMessage chatMessage : chatMessages) {
            chatMessage.setY(y -= 54);
            chatMessage.setOpacity(opacity -= 0.15);
            chatMessage.draw(g2d);
        }
    }
}
