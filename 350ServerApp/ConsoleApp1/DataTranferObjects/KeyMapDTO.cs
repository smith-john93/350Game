using System;
using System.Collections.Generic;
using System.Text;

namespace GameServer.DataTranferObjects
{
    public class KeyMapDTO
    {
        public KeyMapDTO()
        {

        }

        public byte Command { get; set; }

        public string KeyString { get; set; }
    }
}
