using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ServerPhysics.DataTransferObject
{
    public class PlayerStats
    {
        public PlayerStats(int xCord, int yCord, byte control, byte healthVal, byte id)
        {
            x = xCord;
            y = yCord;
            controlByte = control;
            health = healthVal;
            playerId = id;
        }

        public int x { get; set; }
        public int y { get; set; }
        public byte controlByte { get; set; }
        public byte health { get; set; }
        public byte playerId { get; set; }
    }
}
