using System;

namespace ConsoleApp1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Initializing Server...");
            ServerConfiguration _serverConfig = ConfigureServer(); ;
            GameServer server = new GameServer(_serverConfig);
            server.StartServer();
            Console.WriteLine("Press any key to continue...");
            Console.ReadLine();
        }

        private static ServerConfiguration ConfigureServer()
        {
            return new ServerConfiguration();
        }
    }
}
