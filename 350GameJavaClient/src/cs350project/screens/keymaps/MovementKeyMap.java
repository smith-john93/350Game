/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;

import cs350project.screens.listeners.match.MovementInputListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Mark Masone
 */
public class MovementKeyMap extends KeyMap<MovementInputListener> {
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_A:
                for(MovementInputListener movementInputListener : inputListeners) {
                    movementInputListener.startMoveLeft();
                }
                break;
            case KeyEvent.VK_D:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.startMoveRight();
                }
                break;
            case KeyEvent.VK_SPACE:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.startJump();
                }
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_A:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endMoveLeft();
                }
                break;
            case KeyEvent.VK_D:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endMoveRight();
                }
                break;
            case KeyEvent.VK_SPACE:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endJump();
                }
                break;
        }
    }
}
