using System;
using System.Net;
using System.Net.Sockets;

namespace Multicast
{
    class Multicast
    {
        private readonly string groupIP = "ff01::6";
        private readonly int groupPort = 12340;
        
        public Multicast() { }

        public Multicast(string groupIP, int groupPort) {
            this.groupIP = groupIP;
            this.groupPort = groupPort;
        }
        
        public void send(string multicastMessage)
        {
            IPAddress group = IPAddress.Parse(groupIP);
            IPEndPoint ipEndPoint = new IPEndPoint(group, groupPort);
            UdpClient udpClient = new UdpClient(AddressFamily.InterNetworkV6);

            udpClient.JoinMulticastGroup(group);

            //Console.WriteLine("multicast group: " + groupIP + " port: " + groupPort);

            try
            {
                char[] message = multicastMessage.ToCharArray();
                byte[] data = new byte[message.Length];
                int i = 0;
                foreach(char c in message)
                {
                    data[i++] = (byte)c;
                }
                udpClient.SendAsync(data,data.Length,ipEndPoint);
               // Console.WriteLine("sent multicast message: " + multicastMessage);
            } catch(SocketException e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
