/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;

import java.awt.event.KeyEvent;

/**
 *
 * @author Mark Masone
 */
public class MovementKeyMap extends KeyMap<MovementKeyMapListener> {
    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_A:
                for(MovementKeyMapListener movementKeyListener : keyMapListeners) {
                    movementKeyListener.startMoveLeft();
                }
                break;
            case KeyEvent.VK_D:
                for(MovementKeyMapListener movementKeyListener : keyMapListeners) {
                    movementKeyListener.startMoveRight();
                }
                break;
            case KeyEvent.VK_SPACE:
                for(MovementKeyMapListener movementKeyListener : keyMapListeners) {
                    movementKeyListener.startJump();
                }
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_A:
                for(MovementKeyMapListener movementKeyListener : keyMapListeners) {
                    movementKeyListener.endMoveLeft();
                }
                break;
            case KeyEvent.VK_D:
                for(MovementKeyMapListener movementKeyListener : keyMapListeners) {
                    movementKeyListener.endMoveRight();
                }
                break;
            case KeyEvent.VK_SPACE:
                for(MovementKeyMapListener movementKeyListener : keyMapListeners) {
                    movementKeyListener.endJump();
                }
                break;
        }
    }
}
