/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.util.NoSuchElementException;

/**
 *
 * @author Mark Masone
 */
public enum CharacterType {
    GANCHEV(0x0),
    COFFMAN(0x1),
    TRUMP(0x2),
    LEGOMAN(0x3);
    
    private final int value;
    
    private CharacterType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public static CharacterType parse(byte value) {
        for(CharacterType characterType : CharacterType.values()) {
            if(characterType.getValue() == value) {
                return characterType;
            }
        }
        throw new NoSuchElementException("Character type not found.");
    }
}
