using System;
using System.Collections.Generic;
using System.Text;

namespace GameSevrer
{
    public class GameQueue
    {
        public GameQueue()
        {
            queue = new Queue<PlayerSocketController>();
        }

        public Queue<PlayerSocketController> queue {get; private set;}
    }
}
