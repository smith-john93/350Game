using System;
using System.Net;
using System.Net.Sockets;

namespace ConsoleApp1
{
    public class SocketCommunicator
    {
        private Socket PrimarySocket;
        private SocketInformation SocketInfo;
        private IPHostEntry ipHost;
        private IPEndPoint localEndPoint;
        private IPAddress ipAddr;
        // 0x100007F is 127.0.0.1 in big-endian.
        private const long localhost = 0x100007F;
        private const int commandPort = 12345;

        public bool RequestShutdown;
        public GameQueue Queue;
        public SocketCommunicator(GameQueue masterQueue)
        {
            RequestShutdown = false;
            Queue = masterQueue;
            SocketInfo = new SocketInformation();
            
            ipAddr = new IPAddress(localhost);
            localEndPoint = new IPEndPoint(ipAddr, commandPort);

            PrimarySocket = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        }

        public void listen()
        {

            try
            {
                PrimarySocket.Bind(localEndPoint);
                PrimarySocket.Listen(100);

                Console.WriteLine($"Server IP: {ipAddr}. \nServer Socket: {commandPort}");

                while(true && !RequestShutdown)
                {
                    short playerID = (short)(Queue.queue.Count + 1);
                    Queue.queue.Enqueue(new PlayerSocketController(PrimarySocket.Accept(),playerID));
                }
                if(RequestShutdown)
                {
                    Console.WriteLine("Shutdown Request Received. Shutting down server...");
                }
                PrimarySocket.Close();

            }
            catch(SocketException e)
            {
                Console.WriteLine(e);
            }
        }
    }
}
