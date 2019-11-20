/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.screens.KeyMap;
import cs350project.screens.match.ChatInputListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mark Masone
 */
public class ChatKeyMap extends KeyMap<ChatInputListener> {
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_C:
                for(ChatInputListener chatKeyMapListener : inputListeners) {
                    chatKeyMapListener.messageStart();
                }
                break;
        }
    }
}
