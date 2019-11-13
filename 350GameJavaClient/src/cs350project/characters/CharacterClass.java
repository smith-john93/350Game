/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

/**
 *
 * @author Mark Masone
 */
public enum CharacterClass {
    NORMAL(0x1),
    NINJA(0x2),
    MAGE(0x3);
    
    private final int value;
    
    private CharacterClass(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
