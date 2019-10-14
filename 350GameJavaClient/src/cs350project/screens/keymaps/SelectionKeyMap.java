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
 */

/*
Key bindings for the selection screen.
*/
public class SelectionKeyMap extends KeyMap<SelectionKeyMapListener> {
    
    private final ArrayList<SelectionKeyMapListener> selectionKeyMapListeners;
    
    public SelectionKeyMap() {
        selectionKeyMapListeners = new ArrayList<>();
    }
    
    public void addSelectionKeyMapListener(SelectionKeyMapListener selectionKeyMapListener) {
        selectionKeyMapListeners.add(selectionKeyMapListener);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_SPACE: 
                for(SelectionKeyMapListener selectionKeyMapListener : selectionKeyMapListeners) {
                    selectionKeyMapListener.characterSelected();
                }
                break;
            case KeyEvent.VK_D:
                for(SelectionKeyMapListener selectionKeyMapListener : selectionKeyMapListeners) {
                    selectionKeyMapListener.selectNextRight();
                }
                break;
            case KeyEvent.VK_A:
                for(SelectionKeyMapListener selectionKeyMapListener : selectionKeyMapListeners) {
                    selectionKeyMapListener.selectNextLeft();
                }
                break;
        }
    }
}
