using System;
using System.Collections.Generic;
using System.Text;

namespace GameServer.Enumerations
{
    public enum ClientCommands
    {
        LOGIN = 0,
        CREATE_ACCOUNT = 1,
        CREATE_MATCH = 2,
        JOIN_MATCH = 3,
        UDPATE_MATCH = 4,
        CHARACTER_SELECTED = 5
    }
}
