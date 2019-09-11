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
public class KeyboardInput extends KeyAdapter {
    
    private final ArrayList<KeyboardInputListener> keyboardInputListeners;
    
    public KeyboardInput() {
        keyboardInputListeners = new ArrayList();
    }
    
    public void addKeyboardInputListener(KeyboardInputListener kil) {
        keyboardInputListeners.add(kil);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.print(ke.getKeyChar());
        for(KeyboardInputListener kil : keyboardInputListeners) {
            kil.keyTyped(ke.getKeyChar());
        }
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_M: 
                for(KeyboardInputListener kil : keyboardInputListeners) {
                    kil.messageKeyPressed();
                } 
                break;
            case KeyEvent.VK_ENTER:
                for(KeyboardInputListener kil : keyboardInputListeners) {
                    kil.messageCommit();
                } 
                break;
        }
    }
}
