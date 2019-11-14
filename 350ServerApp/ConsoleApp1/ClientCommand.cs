using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    enum ClientCommand
    {
        LOGIN = 0x0,
        CREATE_ACCOUNT = 0x1,
        CREATE_MATCH = 0x2,
        JOIN_MATCH = 0x3,
        UPDATE_MATCH = 0x4
    }

    static class ClientCommandParser
    {
        public static ClientCommand Parse(int command)
        {
            return (ClientCommand)Enum.Parse(typeof(ClientCommand), Enum.GetName(typeof(ClientCommand), command));
        }
    }
}
