/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import java.util.NoSuchElementException;

/**
 *
 * @author Mark Masone
 */
public enum MatchObjectType {
    PLATFORM(0x0),
    PLAYER_CHARACTER(0x1),
    ATTACK(0x2),
    PROJECTILE(0x3);

    private final int value;

    MatchObjectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MatchObjectType parse(byte value) {
        for(MatchObjectType matchObjectType : MatchObjectType.values()) {
            if(matchObjectType.getValue() == value) {
                return matchObjectType;
            }
        }
        throw new NoSuchElementException("Match object type not found.");
    }
}
