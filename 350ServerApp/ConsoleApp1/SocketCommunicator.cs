using System;
using System.Threading;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Net.WebSockets;
using Multicast;
using Database;

namespace GameServer
{
    public class SocketCommunicatorException : Exception
    {
        public SocketCommunicatorException(string message) : base(message) { }
    }

    public class SocketCommunicator
    {
        
        private IPEndPoint localEndPoint;
        private IPAddress ipAddr;
        private Database.Database databaseService;
        private IPHostEntry ipHost;
        private const int commandPort = 12345;
        public TcpListener listener;
        private GameController gController;
        private Multicast.Multicast multi;
        public bool RequestShutdown;
        private const string MULTICAST_STRING = "o";

        ///<exception cref="SocketCommunicatorException">
        ///Thrown when the local host has no IPv4 address entries.
        ///</exception>
        public SocketCommunicator(GameController gameController, Database.Database dbService) 
        {
            gController = gameController;
            databaseService = dbService;
            RequestShutdown = false;

            ipHost = Dns.GetHostEntry(Dns.GetHostName());
            foreach(IPAddress ipAddress in ipHost.AddressList)
            {
                if(ipAddress.AddressFamily == AddressFamily.InterNetwork)
                {
                    ipAddr = ipAddress;
                    break;
                }
            }

            if(ipAddr == null)
            {
                throw new SocketCommunicatorException("Host has no IPv4 addresses.");
            }

            localEndPoint = new IPEndPoint(ipAddr, commandPort);

            multi = new Multicast.Multicast();
        }


        async public void listen()
        {
            try
            {
                Console.WriteLine("Starting Multicast Broadcast...");
                Task.Run(() => SendMulticast(MULTICAST_STRING));
                Console.WriteLine("Broadcast Started");                

                listener = new TcpListener(localEndPoint);
                listener.Start();               

                string socketInfo = listener.LocalEndpoint.ToString();

                //used for debugging
                DisplayInfo(socketInfo);

                while(!RequestShutdown)
                {
                    try
                    {
                        //Get a new TCPClient and hand it off to a PlayerSocketCOntroller, then spawn a new thread
                        PlayerSocketController p = new PlayerSocketController(listener.AcceptTcpClient(), gController, databaseService);
                        Thread playerThread = new Thread(p.Start);
                        playerThread.Start();                  
                    }
                    catch(Exception e)
                    {
                        Console.WriteLine(e);
                    }

                }
                if(RequestShutdown)
                {
                    Console.WriteLine("Shutdown Request Received. Shutting down server...");
                }

            }
            catch(SocketException e)
            {
                Console.WriteLine(e);
            }
        }

        async public void SendMulticast(string message)
        {
            while(!RequestShutdown)
            {
                multi.send(message);
                Thread.Sleep(new TimeSpan(0, 0, 10));
            }
        }

        private void DisplayInfo(string information)
        {
            /*
            string[] info = information.Split(":");
            Console.WriteLine(information);
            */

            /* This broke when I switched to IPv4 - Mark M.
            string[] info = information.Split("]:");
            info[0] = info[0].ToString().Replace("[", string.Empty);
            string[] IpV6 = info[0].ToString().Split("%");
            Console.WriteLine($"Server Information: \nServer IP: {IpV6[0]}. \nServer Socket: {info[1]}");
            */

            // This works - Mark M.
            Console.WriteLine(information);
        }
    }
}
