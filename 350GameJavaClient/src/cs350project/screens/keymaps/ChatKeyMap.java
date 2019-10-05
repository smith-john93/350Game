/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;

import java.awt.event.KeyEvent;

/**
 *
 * @author Mark Masone
 */
public class ChatKeyMap extends KeyMap<ChatKeyMapListener> {
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_C:
                for(ChatKeyMapListener chatKeyMapListener : keyMapListeners) {
                    chatKeyMapListener.messageStart();
                }
                break;
        }
    }
}
