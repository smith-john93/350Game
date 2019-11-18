using System;
using System.Threading;
using System.Threading.Tasks;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Net.WebSockets;

namespace GameSevrer
{
    public class SocketCommunicator
    {
        
        private IPEndPoint localEndPoint;
        private IPAddress ipAddr;
        //private ThreadPool _playerThreadPool;
        // 0x100007F is 127.0.0.1 in big-endian.
        /*
        private const long localhost = 0x100007F;
        private const long localhost = 0x1EAC840A;
         */
        private IPHostEntry ipHost;
        private const int commandPort = 12345;
        public TcpListener listener;
        private GameController gController;

        public bool RequestShutdown;
        public SocketCommunicator(GameController gameController)
        {
            gController = gameController;

            RequestShutdown = false;

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
                        PlayerSocketController p = new PlayerSocketController(listener.AcceptTcpClient(), gController);
                        Thread playerThread = new Thread(p.Start);
                        playerThread.Start();                  
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
            Console.WriteLine($"Server Information: \nServer IP: {IpV6[0]}. \nServer Socket: {info[1]}");
        }
    }
}
