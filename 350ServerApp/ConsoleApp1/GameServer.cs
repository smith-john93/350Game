using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Net;


namespace ConsoleApp1
{
    public class GameServer
    {
        private ServerConfiguration _serverConfig;
        public GameServer(ServerConfiguration _serverConfiguration)
        {
            _serverConfig = _serverConfiguration;
        }

        public void StartServer()
        {

            Console.WriteLine("Server initialized");



            Console.WriteLine("Opening Matchmaking Queue...");
            GameQueue queue = new GameQueue();
            GameSimulation matchMaker = new GameSimulation(queue);
            Thread MatchmakerThread = new Thread(matchMaker.Run);
            MatchmakerThread.Start();
            Console.WriteLine("Queue Opened.");


            Console.WriteLine("Opening Socket Communication...");
            SocketCommunicator communicator = new SocketCommunicator(queue);
            Thread SocketThread = new Thread(communicator.listen);
            SocketThread.Start();
            Console.WriteLine("Communication Started.");

            Thread.Sleep(100);

            while(true)
            {
                Console.WriteLine("\nOptions:");
                Console.WriteLine("1: Check the queue size");
                Console.WriteLine("2: Shutdown the server");
                
                string a = Console.ReadLine();

                if (a == "1")
                    Console.WriteLine($"Queue Size is {queue.queue.Count}");
                else
                {
                    matchMaker.RequestShutdown = true;
                    communicator.listener.Stop();
                    communicator.RequestShutdown = true;

                    MatchmakerThread.Join();
                    SocketThread.Join();
                    break;

                }
            }
        }


        /*
        needs:
            (for each new game: Thread) Class for simulating game
            (ongoing thread) Class to detect new connections
            Controller to pair up new players
            Layer to interact with the DB
            

        Logical order:
            Accept new connection from user
            validate the user (authentication - check username for match, salt then hash password & check for match)
            wait for user input:
                1- Enter Lobby
                2- Check stats
                3- Edit information
            Process user request
                1- place into matchmaking queue
                2- reply with user status
                3- allow information editing


        Player Controller:
            Thread for each 

        MATCHMAKING QUEUE:
            Queue that matches two users
            creates a new instance of GameSimulation(SocketStream1, SocketStream2)
            queue will make sure two players who just played will not play together in new match

        */
    }
}
