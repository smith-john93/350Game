package cs350project;

public enum Command {
    MOVING_LEFT(0x40),
    MOVING_RIGHT(0x20),
    CROUCHING(0x10),
    JUMPING(0x8),
    BLOCKING(0x4),
    PUNCH(0x2),
    HIGH_KICK(0x1),
    LOW_KICK(0x5);
        
    private final int code;
        
    private Command(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
