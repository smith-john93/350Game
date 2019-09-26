using System;
using System.Threading;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Net.WebSockets;

namespace ConsoleApp1
{
    public class SocketCommunicator
    {
        private Socket PrimarySocket;
        private SocketInformation SocketInfo;
        private IPHostEntry ipHost;
        private IPEndPoint localEndPoint;
        private IPAddress ipAddr;

        public bool RequestShutdown;
        public GameQueue Queue;
        public SocketCommunicator(GameQueue masterQueue)
        {
            RequestShutdown = false;
            Queue = masterQueue;
            SocketInfo = new SocketInformation();

            ipHost = Dns.GetHostEntry(Dns.GetHostName());
            ipAddr = ipHost.AddressList[0];
            localEndPoint = new IPEndPoint(ipAddr, 0);

            PrimarySocket = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
        }

        public void listen()
        {

            try
            {
                PrimarySocket.Bind(localEndPoint);
                PrimarySocket.Listen(100);
                string socketInfo = PrimarySocket.LocalEndPoint.ToString();
                string[] info = socketInfo.Split("]:");
                info[0] = info[0].Replace("[", "");

                Console.WriteLine($"Server IP: {info[0]}. \nServer Socket: {info[1]}");
                int i = 0;
                while(true && !RequestShutdown)
                {
                    if(PrimarySocket.Connected)
                    {
                        Queue.queue.Enqueue(new PlayerSocketController(PrimarySocket.Accept()));

                    }
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
