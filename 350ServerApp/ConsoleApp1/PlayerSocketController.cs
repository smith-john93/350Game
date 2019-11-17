using System;
using System.IO;
using System.Net.Sockets;
using System.Collections.Generic;
using System.Text;

namespace ConsoleApp1
{
    public class PlayerSocketController
    {
       
        public int user;
        public NetworkStream clientInterface;
        private TcpClient _socket;


        public PlayerSocketController(TcpClient playerSocket)
        {
            _socket = playerSocket;

        }

        /// <summary>
        /// Controls unique player connecitons
        /// Called for thread starts
        /// </summary>
        public void Start()
        {
            //because yeet
            Console.WriteLine("fucking YEET");
            
            //validate the user before continuing
            bool validated = validateUser();
            
            //if the user could not be validated, respond with such and close ther connection
            if(!validated)
            {
                Console.WriteLine("Closing Connection");
                byte[] rejectionResponse = new byte[1] { 7 };
                ReadOnlySpan<byte> rejection = new ReadOnlySpan<byte>(rejectionResponse);
                clientInterface.Write(rejectionResponse);
                clientInterface.Close();
                _socket.Close();
            }
            //if they could be validated, allow them to continue and process their requested action
            else
            {
               //do stuff here
            }
        }

        /// <summary>
        /// This whole method needs to work with the data layer to validate credentials against the database
        /// </summary>
        /// <returns></returns>
        private bool validateUser()
        {
            //set variables for the method to use
            int i;
            byte[] response = new byte[1];
            int failures = 0;
            bool validCred = false;


            while (true)
            {
                //read the byte sent from the client
                //This will need to validate to check and make sure the client is trying to connect to the server
                clientInterface = _socket.GetStream();
                i = clientInterface.ReadByte();
                Console.WriteLine($"Byte Read: {i}");

                //get the username from the client
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


                //get the password from the client
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

                
                //convert the stringbuilders to string for validation
                //this will be turned into sending the info to the DAL
                string user = cUser.ToString();
                string pass = cPass.ToString();
                if (user == "username")
                    if (pass == "password")
                        validCred = true;

                //checking to see if the credentials are valid
                if (!validCred)
                {
                    Console.WriteLine("Incimenting i");
                    failures++;
                    if (failures == 4)
                    {
                        return false;
                    }

                }
                
                //set the client respaonse based on the response from validating in the DB
                if (validCred)
                    response[0] = 5;
                else
                    response[0] = 6;

                //to be deleted -- used for debugging
                Console.WriteLine($"Reponding with {(response[0] == 5 ? true : false)}");

                //create a ReadOnlySpan to respond to the client with
                ReadOnlySpan<byte> a = new ReadOnlySpan<byte>(response);

                //Respond to the client
                clientInterface.Write(a);

                //if the credentials are correct we can return to the contolling method
                if(validCred)
                    return true;
            }
        }

    }
}
