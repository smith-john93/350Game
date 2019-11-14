using System;
using System.IO;
using System.Net.Sockets;

namespace ConsoleApp1
{
    public class PlayerSocketController
    {
        public PlayerSocketController()
        {
            
        }

        public PlayerSocketController(Socket playerSocket,short playerID)
        {
            this.playerID = playerID;
            NetworkStream networkStream = new NetworkStream(playerSocket);
            clientReader = new StreamReader(networkStream);
            ClientWriter = new StreamWriter(networkStream);
            Console.WriteLine("Client connected.");
        }
        public PlayerSocketController(int i)
        {
            User = i;
        }

        public void listen()
        {
            while (true)
            {
                byte commandByte = (byte)clientReader.Read();
                short id = (short)((byte)clientReader.Read() << 8);
                id += (byte)clientReader.Read();
                Console.Write("Player " + playerID + ",");
                Console.Write("ID: " + id + ",");
                Console.WriteLine("command received: " + CharacterStateParser.Parse(commandByte));
            }
        }

        private short playerID;
        public int User { get; set; }
        public StreamWriter ClientWriter { get; set; }
        private StreamReader clientReader;
    }
}
