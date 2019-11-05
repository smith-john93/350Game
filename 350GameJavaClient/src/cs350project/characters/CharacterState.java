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
public class CharacterState {
    
    public static final int MOVING_LEFT = 0x100;
    public static final int MOVING_RIGHT = 0x80;
    public static final int CROUCHING = 0x40;
    public static final int JUMPING = 0x20;
    public static final int BLOCKING = 0x10;
    public static final int PUNCH = 0x8;
    public static final int HIGH_KICK = 0x4;
    public static final int LOW_KICK = 0x2;
    public static final int FALLING = 0x1;
    public static final int IDLE = 0x0;
    
    // Client only
    public static final int THUMBNAIL = 0x10000;
    
}
