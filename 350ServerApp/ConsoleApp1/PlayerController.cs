using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Net.Sockets;
using GameServer.Enumerations;

namespace GameServer
{
    public class PlayerController
    {
        private NetworkStream clientInterface;
        private GameController gController;
        public string playername;
        public bool inGame;
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

            gController.playerList.Add(this);

            PlayerControl();
        }

        private void PlayerControl()
        {
            while(true)
            {

                Console.WriteLine("Waiting for Player Match Option");
                int i = clientInterface.ReadByte();
                Console.WriteLine($"Received option {i}");

                switch (i)
                {
                    case (int)ClientCommands.CREATE_MATCH:
                        Console.WriteLine("Creating Match");
                        CreateGame();
                        
                        Console.WriteLine("Exiting Creating Match");
                        break;

                    case (int)ClientCommands.JOIN_MATCH:
                        Console.WriteLine("Joining Match");
                        JoinGame();                       
                        Console.WriteLine("Exiting Joining Match");
                        break;

                    default:
                        Console.WriteLine($"Invalid Action {i}");
                        break;
                }
            }
        }

        #region Player Options
        /// <summary>
        /// Called when a client joins a game, rather than creating a game
        /// </summary>
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
            inGame = gController.AddPlayer(game, this);
            while(inGame)
            {
                continue;
            }
            //write a method to handle cleanup after the game ends
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
                //Console.WriteLine($"Match to make: {matchMake}");
                
                //Turn the name into a string
                string gameName = matchMake.ToString();

                if (gController.CreateGame(gameName, this))
                {
                    inGame = true;
                    while(inGame)
                    {
                        continue;
                    }
                    //this needs to handle cleanup after the game ends
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

        async public void PulseLobby(string match, bool addMatch)
        {
            if (!inGame)
                await Task.Run(() => SendMatchList(match, addMatch));
        }

        async public void SendMatchList(string match, bool addMatch)
        {
            byte[] updateCommand = new byte[1] { (byte)ServerCommands.UPDATE_LOBBY };
            await clientInterface.WriteAsync(updateCommand);

            Console.WriteLine($"sent udpate command");

            byte[] add = new byte[1] { addMatch ? (byte)1 : (byte)0 };
            await clientInterface.WriteAsync(add);

            Console.WriteLine($"sent action bit {add[0]}");
            
            byte[] matchName = new byte[10];
            int loc = 0;
            foreach (char a in match.ToCharArray())
            {
                matchName[loc++] = (byte)a;
            }
            await clientInterface.WriteAsync(matchName);
            Console.WriteLine($"sent match name {match}");
        }

        async public void GetCharacter()
        {
            Console.WriteLine($"{playername} In Character Selection");
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
        
        public void SendMessage(ReadOnlySpan<byte> byteSpan)
        {
            clientInterface.Write(byteSpan);
        }

        public void SendPlatform(MatchObjectType command, byte MatchObjectId, byte x, byte y, byte w, byte h)
        {

            byte[] response = new byte[5] { MatchObjectId, x, y, w, h };
            clientInterface.WriteByte((byte)command);
            foreach (byte i in response)
            {                
                clientInterface.WriteByte(0);
                clientInterface.WriteByte(i);
            }

            //ReadOnlySpan<byte> responseMessage = new ReadOnlySpan<byte>(response[0]);
            //clientInterface.Write(responseMessage);
            //responseMessage = new ReadOnlySpan<byte>(response[1]);
            //clientInterface.Write(responseMessage);
            //responseMessage = new ReadOnlySpan<byte>(response[2]);
            //clientInterface.Write(responseMessage);
            //responseMessage = new ReadOnlySpan<byte>(response[3]);
            //clientInterface.Write(responseMessage);
            //responseMessage = new ReadOnlySpan<byte>(response[4]);
            //clientInterface.Write(responseMessage);
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
                //if (user == "username")
                //    if (pass == "password")
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
                {
                    playername = user;
                    return true;
                }
            }
            return false;
        }

        public void LeaveGame()
        {
            inGame = false;
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
