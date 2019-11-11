/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.screens.KeyMap;
import java.awt.event.KeyEvent;
import cs350project.screens.match.MatchMenuInputListener;

/**
 *
 * @author Mark Masone
 */
public class MatchMenuKeyMap extends KeyMap<MatchMenuInputListener> {
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                for(MatchMenuInputListener menuKeyListener : inputListeners) {
                    menuKeyListener.endGame();
                }
                break;
        }
    }
}
