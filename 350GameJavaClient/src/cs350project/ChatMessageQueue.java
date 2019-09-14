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
    private final int maxMessagesOnScreen;
    private final double maxOpacity;
    private final int startingYPosition;
    private final int spacing;
    
    public ChatMessageQueue() {
        chatMessages = new LinkedList<>();
        maxMessagesOnScreen = 6;
        maxOpacity = 1;
        startingYPosition = 800;
        spacing = 10;
    }
    
    public void addMessage(ChatMessage chatMessage) {
        chatMessages.addFirst(chatMessage);
        if(chatMessages.size() > maxMessagesOnScreen) {
            chatMessages.removeLast();
        }
    }
    
    public void draw(Graphics2D g2d) {
        int y = startingYPosition;
        double opacity = maxOpacity;
        double opacityDecrement = opacity / maxMessagesOnScreen;
        for(ChatMessage chatMessage : chatMessages) {
            chatMessage.setY(y);
            chatMessage.setOpacity(opacity);
            chatMessage.draw(g2d);
            y -= chatMessage.getHeight() + spacing;
            opacity -= opacityDecrement;
        }
    }
}
