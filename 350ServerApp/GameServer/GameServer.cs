using System;
using System.Threading;

namespace GameServer
{
    public class GameServer
    {

        private const string connecitonString = "Data Source=database.sqlite3";
        public GameServer() { }

        public void StartServer()
        {

            Console.WriteLine("Server initialized");

            Console.Write("Generating Game Listing...");
            GameController gameController = new GameController();          
            Console.WriteLine("Game Listing Created");

            Console.Write("Launching Database...");
            Database.Database databaseService = new Database.Database(connecitonString);
            Console.WriteLine("Data base launched");

            Console.WriteLine("Opening Socket Communication...");
            try
            {
                SocketCommunicator communicator = new SocketCommunicator(gameController, databaseService);

                Thread SocketThread = new Thread(communicator.Listen);
                SocketThread.Start();
                Console.WriteLine("Communication Started.");
                Console.WriteLine("");

                Thread.Sleep(500);

                while (true)
                {
                    Console.WriteLine("\nOptions:");
                    Console.WriteLine("1: Check the game listing size");

                    string a = Console.ReadLine();

                    if (a == "1")
                    {
                        Console.WriteLine("Active Games:");
                        foreach (string game in gameController.gameListing.Keys)
                            Console.WriteLine(game);
                    }
                    else
                    {
                        continue;
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
