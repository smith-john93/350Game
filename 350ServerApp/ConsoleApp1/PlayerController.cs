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

            Console.WriteLine($"Sendigng All Games to {playername}");
            gController.SendAllGames(this);

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
            Console.WriteLine($"Received match name {game}");

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
            Console.WriteLine($"Player: {playername} inGame: {inGame}");
            if (!inGame)
            {
                Console.WriteLine($"Player: {playername} inGame: {inGame} SENDING UPDATE");
                await Task.Run(() => SendMatchList(match, addMatch));
            }
        }

        async public void SendMatchList(string match, bool addMatch)
        {
            Console.WriteLine($"Sending {playername} update lobby");
            byte[] updateCommand = new byte[1] { (byte)ServerCommands.UPDATE_LOBBY };
            await clientInterface.WriteAsync(updateCommand);

            //Console.WriteLine($"sent udpate command");

            byte[] add = new byte[1] { addMatch ? (byte)1 : (byte)0 };
            await clientInterface.WriteAsync(add);

            //Console.WriteLine($"sent action bit {add[0]}");
            
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
            clientInterface.WriteByte((byte)command);
        }
        
        public void SendMessage(ReadOnlySpan<byte> byteSpan)
        {
            clientInterface.Write(byteSpan);
        }

        public void SendOpponentPlayer(CharacterEnum Selection)
        {
            //send CREATE_MATCH_OBJECT = 0,         ---- 1 byte
            //MatchObjectType PlayerCharacter = 1,  ---- 1 byte
            // ID of 1                              ---- 2 bytes
            //player seleciton                      ---- 1 byte
            // State send 0                         ---- 1 byte
            // x                                    ---- 2 bytes
            // y                                    ---- 2 bytes


            //create match object
            clientInterface.WriteByte((byte)ServerCommands.CREATE_MATCH_OBJECT);
            
            //notify a player character is being sent
            clientInterface.WriteByte((byte)MatchObjectType.PlayerCharacter);

            //send short 1
            clientInterface.WriteByte(0);
            clientInterface.WriteByte(1);

            //send the player seleciton
            clientInterface.WriteByte((byte)Selection);

            //send a state of 0
            clientInterface.WriteByte(0);

            //send 256
            clientInterface.WriteByte(1);
            clientInterface.WriteByte(0);

            //send 256
            clientInterface.WriteByte(1);
            clientInterface.WriteByte(0);          
        }

        public void SendPlatform(MatchObjectType command, byte MatchObjectId, int x, int y, int w, int h)
        {
            //create match object
            clientInterface.WriteByte((byte)ServerCommands.CREATE_MATCH_OBJECT);
            clientInterface.WriteByte((byte)command);
            

            int[] response = new int[5] { MatchObjectId, x, y, w, h };
            

            foreach (int i in response)
            {
                //Console.WriteLine($"Sending {i}");
                //byte[] buf = BitConverter.GetBytes(i);
                //clientInterface.Write(buf, 0, buf.Length);

                clientInterface.WriteByte(0);
                clientInterface.WriteByte((byte)i);
            }
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
