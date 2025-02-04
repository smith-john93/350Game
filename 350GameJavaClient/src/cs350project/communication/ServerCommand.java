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
    START_MATCH(0x4),
    USER_AUTH_PASS(0x5),
    USER_AUTH_FAIL(0x6),
    USER_AUTH_BLOCKED(0x7),
    VALID_MATCH_NAME(0x8),
    INVALID_MATCH_NAME(0x9),
    DELETE_MATCH_OBJECT(0xa),
    END_GAME_INSTANCE(0xb),
    SAVED_KEY_MAPPINGS(0xc);
    
    private final int value;
    
    ServerCommand(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
