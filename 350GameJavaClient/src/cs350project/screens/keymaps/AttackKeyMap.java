/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;

import cs350project.screens.listeners.match.AttackInputListener;
import cs350project.screens.listeners.match.ChatInputListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mark Masone
 */
public class AttackKeyMap extends KeyMap<AttackInputListener> {
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_P:
                for(AttackInputListener attackInputListener : inputListeners) {
                    attackInputListener.punch();
                }
                break;
            case KeyEvent.VK_K:
                for(AttackInputListener attackInputListener : inputListeners) {
                    attackInputListener.highKick();
                }
                break;
            case KeyEvent.VK_L:
                for(AttackInputListener attackInputListener : inputListeners) {
                    attackInputListener.lowKick();
                }
                break;
        }
    }
}
