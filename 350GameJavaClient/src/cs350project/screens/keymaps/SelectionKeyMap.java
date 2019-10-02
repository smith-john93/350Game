/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import cs350project.screens.keymaps.MatchKeyMapListener;

/**
 *
 * @author Mark Masone
 */

/*
Key bindings for the selection screen.
*/
public class SelectionKeyMap extends KeyMap<SelectionKeyMapListener> {
    
    @Override
    public void keyPressed(KeyEvent ke) {
        for(SelectionKeyMapListener selectionKeyMapListener : keyMapListeners) {
            switch(ke.getKeyCode()) {
                case KeyEvent.VK_SPACE: 
                    selectionKeyMapListener.characterSelected();
                    break;
                case KeyEvent.VK_D:
                    selectionKeyMapListener.selectNextRight();
                    break;
                case KeyEvent.VK_A:
                    selectionKeyMapListener.selectNextLeft();
                    break;
            }
        }
    }
}
