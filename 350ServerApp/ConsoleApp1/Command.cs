using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    public enum Command
    {
        START_MOVE_LEFT = 0x1,
        START_MOVE_RIGHT = 0x2,
        START_JUMP = 0x3,
        END_MOVE_LEFT = 0x4,
        END_MOVE_RIGHT = 0x5,
        END_JUMP = 0x6
    }

    public static class CommandUtilities
    {
        public static Command ParseCommand(byte commandByte)
        {
            return (Command)Enum.Parse(typeof(Command),Enum.GetName(typeof(Command),commandByte));
        }
    }
}
