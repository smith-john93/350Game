/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 * @param <KeyMapListener>
 */
public class KeyMap<KeyMapListener> extends KeyAdapter {
    
    protected final ArrayList<KeyMapListener> keyMapListeners;
    private final ArrayList<KeyMap> keyMaps;
    
    public KeyMap() {
        keyMapListeners = new ArrayList<>();
        keyMaps = new ArrayList<>();
    }
    
    public void addKeyMapListener(KeyMapListener keyMapListener) {
        keyMapListeners.add(keyMapListener);
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
