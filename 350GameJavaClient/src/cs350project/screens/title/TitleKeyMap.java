/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.title;

import cs350project.screens.KeyMap;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mark Masone
 */
public class TitleKeyMap extends KeyMap<TitleInputListener> {
    
    private final int continueKeyCode = KeyEvent.VK_SPACE;
    
    public String getContinueKeyText() {
        return KeyEvent.getKeyText(continueKeyCode);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == continueKeyCode) {
            for(TitleInputListener titleInputListener : inputListeners) {
                titleInputListener.start();
            }
        }
    }
}
