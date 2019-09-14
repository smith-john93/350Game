/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 */
public class KeyboardAdapter extends KeyAdapter {
    
    private final ArrayList<KeyboardListener> keyboardListeners;
    
    public KeyboardAdapter() {
        keyboardListeners = new ArrayList();
    }
    
    public void addKeyboardListener(KeyboardListener kl) {
        keyboardListeners.add(kl);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_C: 
                for(KeyboardListener kl : keyboardListeners) {
                    kl.messageStart();
                }
                break;
        }
    }
}
