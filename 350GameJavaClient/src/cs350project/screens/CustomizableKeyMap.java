/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 * @param <E>
 */
public abstract class CustomizableKeyMap<E extends InputListener> extends KeyMap<E> {
    
    private final HashMap<Integer, Integer> keyMappings;
    
    public CustomizableKeyMap(HashMap<Integer, Integer> keyMappings) {
        super();
        this.keyMappings = keyMappings;
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        Integer mapCode = keyMappings.get(keyCode);
        if(mapCode != null) {
            mappedKeyPressed(mapCode);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        int mapCode = keyMappings.get(ke.getKeyCode());
        mappedKeyReleased(mapCode);
    }
    
    protected abstract void mappedKeyPressed(int mapCode);
    protected abstract void mappedKeyReleased(int mapCode);
}
