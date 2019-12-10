/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.screens.CustomizableKeyMap;
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
        for(MovementInputListener movementKeyListener : inputListeners) {
            movementKeyListener.startMovement(mapCode);
        }
    }

    @Override
    protected void mappedKeyReleased(int mapCode) {
        for(MovementInputListener movementKeyListener : inputListeners) {
            movementKeyListener.endMovement(mapCode);
        }
    }
}
