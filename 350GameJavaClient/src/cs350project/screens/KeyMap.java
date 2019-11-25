/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 * @param <E>
 */
public class KeyMap<E extends InputListener> extends KeyAdapter {
    
    protected final ArrayList<E> inputListeners;
    private final ArrayList<KeyMap> keyMaps;
    
    public KeyMap() {
        inputListeners = new ArrayList<>();
        keyMaps = new ArrayList<>();
    }
    
    public void addInputListener(E inputListener) {
        inputListeners.add(inputListener);
    }
    
    public void addKeyMap(KeyMap keyMap) {
        keyMaps.add(keyMap);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        for(KeyMap keyMap : keyMaps) {
            keyMap.keyPressed(ke);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        for(KeyMap keyMap : keyMaps) {
            keyMap.keyReleased(ke);
        }
    }
}
