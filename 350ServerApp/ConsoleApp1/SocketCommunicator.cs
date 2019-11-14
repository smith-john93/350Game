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
        private IPEndPoint localEndPoint;
        private IPAddress ipAddr;
        private AsyncCallback onConnect;
        //private ThreadPool _playerThreadPool;
        // 0x100007F is 127.0.0.1 in big-endian.
        /*
        private const long localhost = 0x100007F;
        private const long localhost = 0x1EAC840A;
         */
        private IPHostEntry ipHost;
        private const int commandPort = 12345;


        public bool RequestShutdown;
        public GameQueue Queue;

        public SocketCommunicator(GameQueue masterQueue)
        {
            RequestShutdown = false;
            Queue = masterQueue;


            ipHost = Dns.GetHostEntry(Dns.GetHostName());
            ipAddr = ipHost.AddressList[0];
            /*
            ipAddr = new IPAddress(localhost);
            */

            localEndPoint = new IPEndPoint(ipAddr, commandPort);
            //PrimarySocket = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);
            //_playerThreadPool = new 
        }

        public void listen()
        {

            try
            {
                //PrimarySocket.Bind(localEndPoint);
                //PrimarySocket.Listen(100);
                //PrimarySocket.BeginAccept(onConnect, PrimarySocket);
                TcpListener b = new TcpListener(localEndPoint);
                b.Start();

                string socketInfo = b.LocalEndpoint.ToString();

                //used for debugging
                DisplayInfo(socketInfo);

                int i = 0;
                while(!RequestShutdown)
                {
                    try
                    {
                        PlayerSocketController a = new PlayerSocketController(b.AcceptTcpClient());
                    }
                    catch(Exception e)
                    {
                        Console.WriteLine(e);
                    }
                    Console.WriteLine("connection yeeted");

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

        private void DisplayInfo(string information)
        {
            /*
            string[] info = information.Split(":");
            Console.WriteLine(information);
            */
            string[] info = information.Split("]:");
            info[0] = info[0].ToString().Replace("[", string.Empty);
            string[] IpV6 = info[0].ToString().Split("%");
            Console.WriteLine($"Server IP: {IpV6[0]}. \nServer Socket: {info[1]}");
        }
    }
}
