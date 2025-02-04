/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.communication;

/**
 *
 * @author Mark Masone
 */
public enum ClientCommand {
    LOGIN(0x0),
    CREATE_ACCOUNT(0x1),
    CREATE_MATCH(0x2),
    JOIN_MATCH(0x3),
    UPDATE_MATCH(0x4),
    CHARACTER_SELECTED(0x5),
    SAVE_KEY_MAPPINGS(0x6),
    SAVE_ALL_MAPPINGS(0x7);
    
    private final int value;
    
    ClientCommand(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
