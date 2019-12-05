using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Net;


namespace GameServer
{
    public class GameServer
    {

        public GameServer() { }

        public void StartServer()
        {

            Console.WriteLine("Server initialized");

            Console.WriteLine("Generating Game Listing...");
            GameController gameController = new GameController();          
            Console.WriteLine("Game Listing Created");

            Console.WriteLine("Opening Socket Communication...");
            try
            {
                SocketCommunicator communicator = new SocketCommunicator(gameController);

                Thread SocketThread = new Thread(communicator.listen);
                SocketThread.Start();
                Console.WriteLine("Communication Started.");
                Console.WriteLine("");

                Thread.Sleep(500);

                while (true)
                {
                    Console.WriteLine("\nOptions:");
                    Console.WriteLine("1: Check the game listing size");
                    Console.WriteLine("2: Shutdown the server");

                    string a = Console.ReadLine();

                    if (a == "1")
                    {
                        Console.WriteLine("Active Games:");
                        foreach (string game in gameController.gameListing.Keys)
                            Console.WriteLine(game);
                    }
                    else
                    {
                        //matchMaker.RequestShutdown = true;
                        communicator.listener.Stop();
                        communicator.RequestShutdown = true;

                        //MatchmakerThread.Join();
                        SocketThread.Join();
                        break;

                    }
                }
            }
            catch (SocketCommunicatorException e)
            {
                Console.WriteLine(e.GetType() + ": " + e.Message);
            }
        }
    }
}
