using System;

namespace GameServer
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Initializing Server...");
            GameServer server = new GameServer();
            server.StartServer();
            Console.WriteLine("Press any key to continue...");
            Console.ReadLine();
        }
    }
}
