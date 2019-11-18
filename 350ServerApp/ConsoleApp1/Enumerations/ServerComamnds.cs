using System;
using System.Collections.Generic;
using System.Text;

namespace GameSevrer.Enumerations
{
    public enum ServerCommands
    {
        CREATE_MATCH_OBJECT = 0,
        SELECT_CHARACTER = 1,
        UPDATE_MATCH = 2,
        UPDATE_LOBBY = 3,
        START_MATCH = 4,
        USER_AUTH_PASS = 5,
        USER_AUTH_FAIL = 6,
        USER_AUTH_BLOCKED = 7,
        VALID_MATCH_NAME = 8,
        INVALID_MATCH_NAME = 9

    }
}
