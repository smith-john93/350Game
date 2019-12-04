/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.characters.CharacterState;
import cs350project.screens.CustomizableKeyMap;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class AttackKeyMap extends CustomizableKeyMap<AttackInputListener> {
    
    private boolean punchDown = false;
    private boolean highKickDown = false;
    private boolean lowKickDown = false;
    
    public AttackKeyMap(HashMap<Integer, Integer> keyMappings) {
        super(keyMappings);
    }
    
    @Override
    protected void mappedKeyPressed(int mapCode) {
        switch(mapCode) {
            case CharacterState.PUNCH:
                if(!punchDown) {
                    for(AttackInputListener attackInputListener : inputListeners) {
                        attackInputListener.punch();
                    }
                    punchDown = true;
                }
                break;
            case CharacterState.HIGH_KICK:
                if(!highKickDown) {
                    for(AttackInputListener attackInputListener : inputListeners) {
                        attackInputListener.highKick();
                    }
                    highKickDown = true;
                }
                break;
            case CharacterState.LOW_KICK:
                if(!lowKickDown) {
                    for(AttackInputListener attackInputListener : inputListeners) {
                        attackInputListener.lowKick();
                    }
                    lowKickDown = true;
                }
                break;
        }
    }

    @Override
    protected void mappedKeyReleased(int mapCode) {
        switch(mapCode) {
            case CharacterState.PUNCH:
                punchDown = false;
                break;
            case CharacterState.HIGH_KICK:
                highKickDown = false;
                break;
            case CharacterState.LOW_KICK:
                lowKickDown = false;
                break;
        }
    }
}
