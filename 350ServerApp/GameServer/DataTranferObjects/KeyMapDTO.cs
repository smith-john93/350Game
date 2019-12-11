using System;
using System.Collections.Generic;
using System.Text;

namespace GameServer.DataTranferObjects
{
    /// <summary>
    /// This is a Data Transfer Object for a 1 byte command and a string
    /// </summary>
    public class KeyMapDTO
    {
        public KeyMapDTO()
        {

        }

        public byte Command { get; set; }

        public string KeyString { get; set; }
    }
}
