/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.characters.CharacterState;
import cs350project.screens.CustomizableKeyMap;
import cs350project.screens.KeyMap;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class MovementKeyMap extends CustomizableKeyMap<MovementInputListener> {
    
    public MovementKeyMap(HashMap<Integer, Integer> keyMappings) {
        super(keyMappings);
    }

    @Override
    protected void mappedKeyPressed(int mapCode) {
        switch(mapCode) {
            case CharacterState.JUMPING:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.startJump();
                }
                break;
            case CharacterState.MOVING_LEFT:
                for(MovementInputListener movementInputListener : inputListeners) {
                    movementInputListener.startMoveLeft();
                }
                break;
            case CharacterState.CROUCHING:
                for(MovementInputListener movementInputListener : inputListeners) {
                    movementInputListener.startCrouch();
                }
                break;
            case CharacterState.MOVING_RIGHT:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.startMoveRight();
                }
                break;
            case CharacterState.BLOCKING:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.startBlock();
                }
                break;
        }
    }

    @Override
    protected void mappedKeyReleased(int mapCode) {
        switch(mapCode) {
            case CharacterState.JUMPING:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endJump();
                }
                break;
            case CharacterState.MOVING_LEFT:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endMoveLeft();
                }
                break;
            case CharacterState.CROUCHING:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endCrouch();
                }
                break;
            case CharacterState.MOVING_RIGHT:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endMoveRight();
                }
                break;
            case CharacterState.BLOCKING:
                for(MovementInputListener movementKeyListener : inputListeners) {
                    movementKeyListener.endBlock();
                }
                break;
        }
    }
}
