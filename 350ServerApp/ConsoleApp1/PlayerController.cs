using System;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1
{
    public class PlayerController
    {
        private PlayerSocketController _socketController;
        public PlayerController(PlayerSocketController psc)
        {
            _socketController = psc;
        }

        
    }
}
