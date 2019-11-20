using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    enum CharacterState
    {
        MOVING_LEFT = 0x100,
        MOVING_RIGHT = 0x80,
        CROUCHING = 0x40,
        JUMPING = 0x20,
        BLOCKING = 0x10,
        PUNCH = 0x8,
        HIGH_KICK = 0x4,
        LOW_KICK = 0x2,
        FALLING = 0x1,
        IDLE = 0x0
    }

    static class CharacterStateParser
    {
        public static CharacterState Parse(int stateCode)
        {
            return (CharacterState)Enum.Parse(typeof(CharacterState),Enum.GetName(typeof(CharacterState),stateCode));
        }
    }
}
