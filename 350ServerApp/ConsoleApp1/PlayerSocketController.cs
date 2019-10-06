using System.IO;
using System.Net.Sockets;

namespace ConsoleApp1
{
    public class PlayerSocketController
    {
        public PlayerSocketController()
        {
            
        }

        public PlayerSocketController(Socket playerSocket)
        {

        }
        public PlayerSocketController(int i)
        {
            user = i;
        }
        public int user { get; set; }
        public StreamWriter clientWriter { get; set; }
        public StreamReader clientReader { get; set; }
    }
}
