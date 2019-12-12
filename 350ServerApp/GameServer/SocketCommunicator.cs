using System;
using System.Threading;
using System.Net;
using System.Net.Sockets;


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
        //private Multicast.Multicast multi;
        public bool RequestShutdown;

        ///<exception cref="SocketCommunicatorException">
        ///Thrown when the local host has no IPv4 address entries.
        ///</exception>
        public SocketCommunicator(GameController gameController, Database.Database dbService) 
        {
            gController = gameController;
            //databaseService = dbService;
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
        }

        async public void Listen()
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

        /// <summary>
        /// Display the IP information
        /// </summary>
        /// <param name="information"></param>
        private void DisplayInfo(string information)
        {
            // This works - Mark M.
            Console.WriteLine(information);
        }
    }
}
