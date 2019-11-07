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
public enum ServerCommand {
    CREATE_MATCH_OBJECT(0x0),
    SELECT_CHARACTER(0x1),
    UPDATE_MATCH(0x2),
    UPDATE_LOBBY(0x3),
    START_MATCH(0x4);
    
    private final int value;
    
    private ServerCommand(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
