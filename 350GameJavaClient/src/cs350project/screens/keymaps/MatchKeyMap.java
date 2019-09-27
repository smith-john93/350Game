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
Key bindings for the fight screen.
*/
public class MatchKeyMap extends KeyMap<MatchKeyMapListener> {
    
    @Override
    public void keyPressed(KeyEvent ke) {
        for(MatchKeyMapListener matchKeyMapListener : keyMapListeners) {
            switch(ke.getKeyCode()) {
                case KeyEvent.VK_C:
                    matchKeyMapListener.messageStart();
                    break;
                case KeyEvent.VK_A:
                    matchKeyMapListener.startMoveLeft();
                    break;
                case KeyEvent.VK_D:
                    matchKeyMapListener.startMoveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    matchKeyMapListener.startJump();
                    break;
                case KeyEvent.VK_ESCAPE:
                    matchKeyMapListener.endGame();
                    break;
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        for(MatchKeyMapListener matchKeyMapListener : keyMapListeners) {
            switch(ke.getKeyCode()) {
                case KeyEvent.VK_A:
                    matchKeyMapListener.endMoveLeft();
                    break;
                case KeyEvent.VK_D:
                    matchKeyMapListener.endMoveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    matchKeyMapListener.endJump();
                    break;
            }
        }
    }
}
