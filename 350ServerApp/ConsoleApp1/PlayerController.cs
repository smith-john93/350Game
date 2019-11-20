using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Net.Sockets;
using GameServer.Enumerations;

namespace GameServer
{
    public class PlayerController
    {
        private NetworkStream clientInterface;
        private GameController gController;

        public CharacterEnum selectedCharacter;
        public bool CharacterPicked;
        public PlayerController(PlayerSocketController stream, GameController gameController)
        {
            Console.WriteLine("Player Instance Created");
            clientInterface = stream.clientInterface;
            gController = gameController;
            CharacterPicked = false;
        }

        public void ManagePlayer()
        {
            bool authenticatedUser = AuthenticateUser();

            if(!authenticatedUser)
            {
                CloseConneciton();
                return;
            }

            PlayerControl();
        }

        private void PlayerControl()
        {
            while (true)
            {
                int i = clientInterface.ReadByte();

                switch (i)
                {
                    case (int)ClientCommands.CREATE_MATCH:
                        Console.WriteLine("Creating Match");
                        CreateGame();                       
                        break;

                    case (int)ClientCommands.JOIN_MATCH:
                        Console.WriteLine("Joining Match");
                        JoinGame();                       
                        break;

                    default:
                        Console.WriteLine($"Invalid Action {i}");
                        break;
                }
            }
        }

        #region Player Options
        private void JoinGame()
        {
            Console.WriteLine("Join Match");
            //get the matchName from the client
            StringBuilder matchJoin = new StringBuilder();
            do
            {
                char u = (char)clientInterface.ReadByte();
                if (u == 0)
                    break;
                matchJoin.Append(u);
            }
            while (clientInterface.DataAvailable);
            string game = matchJoin.ToString();

            //Add the player to the match
            gController.AddPlayer(game, this);
        }
        private void JoinGame(string gameName)
        {
            //Add the player to the match
            gController.AddPlayer(gameName, this);
        }

        private void CreateGame()
        {
            while(true)
            {

                //get the matchName from the client
                StringBuilder matchMake = new StringBuilder();
                do
                {
                    char u = (char)clientInterface.ReadByte();
                    if (u == 0)
                        break;
                    matchMake.Append(u);
                }
                while (clientInterface.DataAvailable);
                Console.WriteLine($"Match to make: {matchMake}");
                string gameName = matchMake.ToString();

                if (gController.CreateGame(gameName, this))
                {
                    byte[] responseByte = new byte[1] { (byte)ServerCommands.VALID_MATCH_NAME};
                    ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
                    clientInterface.Write(response);
                    JoinGame(gameName);
                }
                else
                {
                    byte[] responseByte = new byte[1] { (byte)ServerCommands.INVALID_MATCH_NAME };
                    ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
                    clientInterface.Write(response);
                }
            }
        }
        #endregion

        #region Communication

        async public void GetCharacter()
        {
            Console.WriteLine("In Character Selection");
            byte[] selectedBuffer = new byte[1];
            await clientInterface.ReadAsync(selectedBuffer);
            if (selectedBuffer[0] == (byte)ClientCommands.CHARACTER_SELECTED)
                CharacterPicked = true;

            Console.WriteLine(selectedBuffer[0]);            
            byte[] buffer = new byte[1];
            await clientInterface.ReadAsync(buffer);
            Console.WriteLine(buffer[0]);
            
            switch(buffer[0])
            {
                case (byte) CharacterEnum.Ganchev:
                    selectedCharacter = CharacterEnum.Ganchev;
                    break;
                case (byte)CharacterEnum.Coffman:
                    selectedCharacter = CharacterEnum.Coffman;
                    break;
                case (byte)CharacterEnum.Trump:
                    selectedCharacter = CharacterEnum.Trump;
                    break;
                default:
                    selectedCharacter = CharacterEnum.Lego;
                    break;
            }
        }

        public void SendMessage(ServerCommands command)
        {
            byte[] response = new byte[1] { (byte)command };
            ReadOnlySpan<byte> responseMessage = new ReadOnlySpan<byte>(response);
            clientInterface.Write(responseMessage);
        }
        #endregion

        #region Validation and Authenticaiton
        private bool AuthenticateUser()
        {
            //set variables for the method to use
            int i;
            byte[] response = new byte[1];

            while (true)
            {
                //read the byte sent from the client
                i = clientInterface.ReadByte();
                
                if (i == (int)ClientCommands.CREATE_ACCOUNT)
                {
                    bool accountCreated = CreateUserAccount();

                    if (accountCreated)
                        return true;
                    
                    CloseConneciton();
                    return false;
                }
                else if (i == (int)ClientCommands.LOGIN)
                {
                    bool validUser = ValidateUser();

                    if (validUser)
                        return true;

                    CloseConneciton();
                    return false;
                }
                else
                    CloseConneciton();
            }
        }

        private bool CreateUserAccount()
        {
            //listen for username and password
            //ensure username is allowed int he DB
            //create user in the database



            //if the username is good send back USER_AUTH_PASS
            //If not USER_AUTH_FAIL

            // too many attempts
            //USER_AUTH_BLOCKED
            return true;
        }

        private bool ValidateUser()
        {

            bool validCred = false;
            int failures = 0;

            while(failures < 4)
            {

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
                    failures++;
                    if (failures == 4)
                    {
                        byte[] rejectionResponse = new byte[1] { (byte)ServerCommands.USER_AUTH_BLOCKED };
                        ReadOnlySpan<byte> rejection = new ReadOnlySpan<byte>(rejectionResponse);
                        clientInterface.Write(rejectionResponse);
                        return false;
                    }
                }

                //set the client respaonse based on the response from validating in the DB

                byte[] response = new byte[1];
                if (validCred)
                    response[0] = (byte)ServerCommands.USER_AUTH_PASS;
                else
                    response[0] = (byte)ServerCommands.USER_AUTH_FAIL;

                //to be deleted -- used for debugging
                Console.WriteLine($"Reponding with {(response[0] == 5 ? true : false)}");

                //create a ReadOnlySpan to respond to the client with
                ReadOnlySpan<byte> a = new ReadOnlySpan<byte>(response);

                //Respond to the client
                clientInterface.Write(a);

                //if the credentials are correct we can return to the contolling method
                if (validCred)
                    return true;
            }
            return false;
        }

        #endregion

        #region Cleanup

        private void CloseConneciton()
        {
            Console.WriteLine("Closing Connection");
            clientInterface.Close();
        }

        #endregion

    }
}
