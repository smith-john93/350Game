using System;
using System.Net;
using System.Net.Sockets;

namespace Multicast
{
    class Multicast
    {
        private readonly string groupIP = "224.3.5.0";
        private readonly int groupPort = 65000;
        
        public Multicast() { }

        public Multicast(string groupIP, int groupPort) {
            this.groupIP = groupIP;
            this.groupPort = groupPort;
        }
        
        public void send(string multicastMessage)
        {
            IPAddress group = IPAddress.Parse(groupIP);
            IPEndPoint ipEndPoint = new IPEndPoint(group, groupPort);
            UdpClient udpClient = new UdpClient(AddressFamily.InterNetwork);

            udpClient.JoinMulticastGroup(group);

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
            }
            catch(SocketException e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
