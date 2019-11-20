using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    enum ServerCommand
    {
        CREATE_MATCH_OBJECT = 0x0,
        SELECT_CHARACTER = 0x1,
        UPDATE_MATCH = 0x2,
        UPDATE_LOBBY = 0x3,
        START_MATCH = 0x4,
        USER_AUTH_PASS = 0x5,
        USER_AUTH_FAIL = 0x6,
        USER_AUTH_BLOCKED = 0x7
    }

    static class ServerCommandParser
    {
        public static ServerCommand Parse(int command)
        {
            return (ServerCommand)Enum.Parse(typeof(ServerCommand), Enum.GetName(typeof(ServerCommand), command));
        }
    }
}
