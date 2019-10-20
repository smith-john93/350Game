/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;
import cs350project.screens.listeners.SelectionInputListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 */

/*
Key bindings for the selection screen.
*/
public class SelectionKeyMap extends KeyMap<SelectionInputListener> {
    
    private final ArrayList<SelectionInputListener> selectionInputListeners;
    
    public SelectionKeyMap() {
        selectionInputListeners = new ArrayList<>();
    }
    
    public void addSelectionInputListener(SelectionInputListener selectionInputListener) {
        selectionInputListeners.add(selectionInputListener);
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_SPACE: 
                for(SelectionInputListener selectionInputListener : selectionInputListeners) {
                    selectionInputListener.characterSelected();
                }
                break;
            case KeyEvent.VK_D:
                for(SelectionInputListener selectionInputListener : selectionInputListeners) {
                    selectionInputListener.highlightNextRight();
                }
                break;
            case KeyEvent.VK_A:
                for(SelectionInputListener selectionInputListener : selectionInputListeners) {
                    selectionInputListener.highlightNextLeft();
                }
                break;
        }
    }
}
