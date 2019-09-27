/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;
import cs350project.screens.keymaps.SelectionKeyMapListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 * @param <KeyMapListener>
 */
public class KeyMap<KeyMapListener> extends KeyAdapter {
    
    protected final ArrayList<KeyMapListener> keyMapListeners;
    
    public KeyMap() {
        keyMapListeners = new ArrayList();
    }
    
    public void addKeyMapListener(KeyMapListener kml) {
        keyMapListeners.add(kml);
    }
}
