using System;
using System.IO;
using System.Net.Sockets;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1
{
    public class PlayerSocketController
    {
        int i = 0;
        private byte[] buffer;
        public int user { get; set; }
        public StreamWriter clientWriter { get; set; }
        public NetworkStream clientInterface { get; set; }

        public PlayerSocketController()
        {
            
            Console.WriteLine("fucking default YEET");
        }

        public PlayerSocketController(TcpClient playerSocket)
        {
            Console.WriteLine("fucking YEET");
            int i = 0;
            int failures = 0;
            while(true)
            {

                buffer = new byte[1];
                clientInterface = playerSocket.GetStream();
                i = clientInterface.ReadByte();
                Console.WriteLine($"Byte Read: {i}");

                StringBuilder cUser = new StringBuilder();
                do
                {
                    char u = (char)clientInterface.ReadByte();
                    if (u == 0)
                        break;
                    cUser.Append(u);
                }
                while (clientInterface.DataAvailable);
                Console.WriteLine($"UserName: {cUser}");
          
                StringBuilder cPass = new StringBuilder();
                do
                {
                    char u = (char)clientInterface.ReadByte();
                    if (u == 0)
                        break;
                    cPass.Append(u);
                }
                while (clientInterface.DataAvailable);
                Console.WriteLine($"Password: {cPass}");

                bool validCred = false;

                string user = cUser.ToString();
                string pass = cPass.ToString();
                if (user == "username")
                    if (pass == "password")
                        validCred = true;

                Console.WriteLine(validCred);

                if (!validCred)
                {
                    Console.WriteLine("Incimenting i");
                    failures++;
                    if (failures == 4)
                    {
                        Console.WriteLine("Closing Connection");
                        byte[] rejectionResponse = new byte[1] { 7 };
                        ReadOnlySpan<byte> rejection = new ReadOnlySpan<byte>(rejectionResponse);
                        clientInterface.Write(rejectionResponse);
                        clientInterface.Close();
                        playerSocket.Close();
                        break;
                    }

                }
                byte[] response = new byte[1];

                if (validCred)
                    response[0] = 5;
                else
                    response[0] = 6;

                Console.WriteLine($"Reponding with {(response[0] == 5 ? true:false)}");

                ReadOnlySpan<byte> a = new ReadOnlySpan<byte>(response);
                clientInterface.Write(a);
            }

        }
        public PlayerSocketController(int i)
        {
            user = i;
        }

        public void ReceiveCallback(IAsyncResult i)
        {
            buffer = new byte[BitConverter.ToInt32(buffer, 0)];
            // Next receive this data into the buffer with size that we did receive before
            clientInterface.Read(buffer, buffer.Length,1);
            // When we received everything its onto you to convert it into the data that you've send.
            // For example string, int etc... in this example I only use the implementation for sending and receiving a string.

            // Convert the bytes to string and output it in a message box
            string data = Encoding.Default.GetString(buffer);
            Console.WriteLine(data);
            // Now we have to start all over again with waiting for a data to come from the socket.
        }
    }
}
