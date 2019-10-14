package cs350project;

public enum Command {
    START_MOVE_LEFT(0x1),
    START_MOVE_RIGHT(0x2),
    START_JUMP(0x3),
    END_MOVE_LEFT(0x4),
    END_MOVE_RIGHT(0x5),
    END_JUMP(0x6);

    private byte code;

    private Command(int code) {
        this.code = (byte)code;
    }

    public byte getCode() {
        return code;
    }
}
