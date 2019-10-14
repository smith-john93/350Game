using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    public class GameSimulation
    {
        public bool RequestShutdown;
        private TaskFactory tFactory;
        private GameQueue queue;
        private Action<PlayerSocketController, PlayerSocketController> execute;
        public GameSimulation(GameQueue _queue)
        {
            RequestShutdown = false;
            queue = _queue;
            execute = new Action<PlayerSocketController, PlayerSocketController>(Simulation);
            tFactory = new TaskFactory();
        }

        public void Run()
        {
            while (true && !RequestShutdown)
            {
                if (queue.queue.Count < 2)
                {

                    continue;
                }
                else
                    execute(queue.queue.Dequeue(), queue.queue.Dequeue());
            }
            if(RequestShutdown)
            {
                Console.WriteLine("Shutdown Request Received... Ending match making");
            }
        }


        public void Simulation(PlayerSocketController player1, PlayerSocketController player2)
        {
            //Console.WriteLine($"Game spawned for {player1.user} and {player2.user}");
            Thread player1Thread = new Thread(player1.listen);
            player1Thread.Start();
            Thread player2Thread = new Thread(player2.listen);
            player2Thread.Start();
        }

    }

    public class PlayerMatchup
    {
        public PlayerMatchup()
        {

        }

        public PlayerMatchup(PlayerSocketController player1, PlayerSocketController player2)
        {
            p1 = player1;
            p2 = player2;
        }
        public PlayerSocketController p1;
        public PlayerSocketController p2;
    }
}
