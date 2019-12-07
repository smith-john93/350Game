using System;
using System.Collections.Generic;
using System.Text;

namespace GameServer.Enumerations
{
    public enum Command
    {
        IDLE = 0,
        MOVING_LEFT = 1,
        LOW_KICK = 2,
        HIGH_KICK = 4,
        PUNCH = 8,
        BLOCKING = 16,
        JUMPING = 32,
        CROUCHING = 64,
        MOVING_RIGHT = 128
    }

    public static class CommandUtilities
    {
        public static Command ParseCommand(byte commadByte)
        {
            return (Command)Enum.Parse(typeof(Command), Enum.GetName(typeof(Command), commadByte));
        }
    }
}
