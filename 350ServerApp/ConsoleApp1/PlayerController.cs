﻿using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Net.Sockets;
using GameServer.Enumerations;
using System.IO;
using Database;
using GameServer.DataTranferObjects;


namespace GameServer
{
    public class PlayerController
    {
        public NetworkStream clientInterface;
        private GameController gController;
        public string playername;
        private bool inGame;
        public CharacterEnum selectedCharacter;
        private bool CharacterPicked;
        private Database.Database databseService;
        private const string connecitonString = "Data Source=database.sqlite3";
        private bool forceLeaveGame;

        public PlayerController(PlayerSocketController stream, GameController gameController)
        {
            forceLeaveGame = false;
            clientInterface = stream.clientInterface;
            gController = gameController;
            CharacterPicked = false;
            databseService = new Database.Database(connecitonString);
        }

        public void ManagePlayer()
        {
            bool authenticatedUser = false;

            while(!authenticatedUser)
                authenticatedUser = AuthenticateUser();

            SendKeyMappings();

            gController.playerList.Add(this);
            gController.SendAllGames(this);

            PlayerControl();
        }

        /// <summary>
        /// Controls the player thread and hands exection to the appropriate areas
        /// </summary>
        private void PlayerControl()
        {
            while(true)
            {
                if (clientInterface == null)
                {
                    Console.WriteLine("No connection");
                    DisposePlayer();
                    return;
                }

                int i;
                try
                {
                    i = clientInterface.ReadByte();

                    Console.WriteLine($"received {i}");

                    switch (i)
                    {
                        case (int)ClientCommands.CREATE_MATCH:
                            Console.WriteLine("Creating Game");
                            CreateGame();
                            inGame = false;
                            forceLeaveGame = false;
                            Console.WriteLine("back in switch from create");
                            continue;

                        case (int)ClientCommands.JOIN_MATCH:
                            Console.WriteLine("Joining game");
                            JoinGame();
                            inGame = false;
                            forceLeaveGame = false;
                            Console.WriteLine("back in switch from join");
                            continue;

                        case (int)ClientCommands.SAVE_KEY_MAPPINGS:
                            SaveMapping();
                            continue;

                        default:
                            continue;
                    }
                }
                catch(IOException)
                {
                    DisposePlayer();
                    return;
                }
            }
        }

        #region Player Options
        /// <summary>
        /// Called when a client joins a game, rather than creating a game
        /// </summary>
        private void JoinGame()
        {
            int size = clientInterface.ReadByte();
            //int size = clientInterface.ReadByte();
            //get the matchName from the client
            StringBuilder matchJoin = new StringBuilder();
            do
            {
                matchJoin.Append(ReadChar());
                    size--;
            }
            while (size > 0);
            string game = matchJoin.ToString();

            //Add the player to the match
            bool inGame = gController.AddPlayer(game, this);

            if (!inGame)
                return;

            //stay in the game until the player is no longer in the game
            while(!forceLeaveGame)
            {
                Thread.Sleep(new TimeSpan(0,0,0,0,100));
                //Console.WriteLine($"{playername} still in game");
                continue;
            }
            return;
        }

        /// <summary>
        /// Manages interaction for thr player when creating a game
        /// </summary>
        private void CreateGame()
        {
            try
            {

                int size = clientInterface.ReadByte();
                //stay in the loop until the game is created
                while(true)
                {
                    //get the matchName from the client
                    StringBuilder matchMake = new StringBuilder();
                    do
                    {
                        matchMake.Append(ReadChar());
                        size--;
                    }
                    while (size> 0);
                
                    //Turn the name into a string
                    string gameName = matchMake.ToString();

                    //check to see if a game with that name already exists
                    //otherwise the game runs until completion
                    if (!gController.CreateGame(gameName, this))
                    {
                        //inform the player the entered name is not valid, send the invalid match name byte
                        byte[] responseByte = new byte[1] { (byte)ServerCommands.INVALID_MATCH_NAME };
                        ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
                        clientInterface.Write(response);
                    }
                    else
                    {
                        return;
                    }
                }
            }
            catch(IOException)
            {
                gController.RemovePlayer(this);
            }
        }
        
        private void SaveMapping()
        {
            List<KeyMapDTO> mappings = new List<KeyMapDTO>();

            //receive char state
            //receive int until none are left
            int i = 0;
            while(true)
            {
                if (i == 8)
                    break;
                i++;

                byte charState = (byte)clientInterface.ReadByte();

                int size = clientInterface.ReadByte();
                //get the matchName from the client
                StringBuilder MappingString = new StringBuilder();
                do
                {
                    MappingString.Append(ReadChar());
                    size--;
                }
                while (size > 0);

                KeyMapDTO map = new KeyMapDTO();
                map.Command = charState;
                map.KeyString = MappingString.ToString();
                mappings.Add(map);
            }

            foreach(KeyMapDTO map in mappings)
            {
                //Console.WriteLine($"{playername}, {CommandUtilities.ParseCommand(map.Command).ToString()}, {map.KeyString}");
                databseService.SetKeyBinding(playername, CommandUtilities.ParseCommand(map.Command).ToString(), map.KeyString, connecitonString);
            }
            //Console.WriteLine("Sending key mapping");
            SendMessage(ServerCommands.SAVED_KEY_MAPPINGS);
            return;
        }
        #endregion

        #region Communication

        /// <summary>
        /// 
        /// </summary>
        /// <param name="match"></param>
        /// <param name="addMatch"></param>
        async public void PulseLobby(string match, bool addMatch)
        {
            //send update if the player is not in a game
            if (!inGame)
            {
                //call the method to send the match update to the client
                await Task.Run(() => SendMatchList(match, addMatch));
            }
        }

        /// <summary>
        /// Sends the client a match listing update 
        /// </summary>
        /// <param name="match"></param>
        /// <param name="addMatch"></param>
        async public void SendMatchList(string match, bool addMatch)
        {
            Console.WriteLine("Pulsing");
            //Send the client the update lobby byte
            byte[] updateCommand = new byte[1] { (byte)ServerCommands.UPDATE_LOBBY };
            await clientInterface.WriteAsync(updateCommand);

            //send the client a byte to indicate udpate or removal
            byte[] action = new byte[1] { addMatch ? (byte)1 : (byte)0 };
            await clientInterface.WriteAsync(action);

            //send the length of the string to the client
            byte[] length = new byte[] { (byte)match.Length };
            await clientInterface.WriteAsync(length);

            //send the character array to the client
            WriteChars(match.ToCharArray());
        }

        async private void WriteChars(char[] chars)
        {
            byte[] bytes = new byte[chars.Length * 2];
            int i = 0;
            foreach(char c in chars)
            {
                bytes[i++] = (byte)(c >> 8);
                bytes[i++] = (byte)c;
            }
            await clientInterface.WriteAsync(bytes);
        }

        /// <summary>
        /// Gets the player to be played in a game from the client
        /// </summary>
        async public void GetCharacter()
        {

            //listen for the character selected byte
            byte[] selectedBuffer = new byte[1];
            await clientInterface.ReadAsync(selectedBuffer);
            if (selectedBuffer[0] == (byte)ClientCommands.CHARACTER_SELECTED)
                CharacterPicked = true;

            //listen for which character has been picked
            byte[] buffer = new byte[1];
            await clientInterface.ReadAsync(buffer);


            //set the correct character
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

        public bool HasSelectdCharacter()
        {
            return CharacterPicked;
        }

        /// <summary>
        /// Informs the PlayerController this instance is in a game
        /// </summary>
        /// <param name="inGame"></param>
        public void SetInGameState()
        {
            inGame = true;
        }

        /// <summary>
        /// provides the game state of the player
        /// </summary>
        /// <returns></returns>
        public bool GetGameState()
        {
            return inGame;
        }

        /// <summary>
        /// Sets a ServerCommand enumeration to the player
        /// </summary>
        /// <param name="command"></param>
        public void SendMessage(ServerCommands command)
        {
            clientInterface.WriteByte((byte)command);
        }
        
        /// <summary>
        /// Sends a ReadOnlySpan of bytes to the player
        /// </summary>
        /// <param name="byteSpan"></param>
        public void SendMessage(ReadOnlySpan<byte> byteSpan)
        {
            clientInterface.Write(byteSpan);
        }

        /// <summary>
        /// sends the player the opposing player's information
        /// </summary>
        /// <param name="Selection"></param>
        public void SendOpponentPlayer(CharacterEnum Selection, byte playerId)
        {
            //send CREATE_MATCH_OBJECT = 0,         ---- 1 byte
            //MatchObjectType PlayerCharacter = 1,  ---- 1 byte
            // ID of 1                              ---- 2 bytes
            //player seleciton                      ---- 1 byte
            // State send 0                         ---- 1 byte
            // x                                    ---- 2 bytes
            // y                                    ---- 2 bytes
            //health                                ---- 1 byte

            //create match object
            clientInterface.WriteByte((byte)ServerCommands.CREATE_MATCH_OBJECT);
            
            //notify a player character is being sent
            clientInterface.WriteByte((byte)MatchObjectType.PlayerCharacter);

            //send short 1
            clientInterface.WriteByte(0);
            clientInterface.WriteByte(playerId);

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

            clientInterface.WriteByte(100);
        }

        /// <summary>
        /// Sends coorrdinates of a platform to the player
        /// </summary>
        /// <param name="command"></param>
        /// <param name="MatchObjectId"></param>
        /// <param name="x"></param>
        /// <param name="y"></param>
        /// <param name="w"></param>
        /// <param name="h"></param>
        public void SendPlatform(MatchObjectType command, int MatchObjectId, int x, int y, int w, int h)
        {
            //Send the byte for creating the match object
            clientInterface.WriteByte((byte)ServerCommands.CREATE_MATCH_OBJECT);
            clientInterface.WriteByte((byte)command);
            
            //create an int array to send the remaining information
            int[] response = new int[5] { MatchObjectId, x, y, w, h };
            
            //loop through the array to send each item in the array
            foreach (int i in response)
            {

                //send the client the first byte of the int
                clientInterface.WriteByte((byte)(i >>8));

                //send the second byte of the int
                clientInterface.WriteByte((byte)i);
            }
        }

        private void SendKeyMappings()
        {
            List<KeyMapDTO> keyMapList = new List<KeyMapDTO>();
            foreach(Command c in (Command[])Enum.GetValues((typeof(Command))))
            {
                if ((byte)c == 0)
                    continue;
                KeyMapDTO map = new KeyMapDTO();
                map.Command = (byte)c;
                map.KeyString = databseService.GetKeyBinding(playername, c.ToString(), connecitonString);
                keyMapList.Add(map);
            }

            foreach (KeyMapDTO mappingObject in keyMapList)
            {
                //Console.WriteLine($"Sending {mappingObject.Command} {mappingObject.KeyString}");
                byte[] array = new byte[mappingObject.KeyString.Length+1];
                int i = 0;
                foreach (char a in mappingObject.KeyString.ToCharArray())
                {
                    array[i] = (byte)a;
                    i++;
                }
                array[mappingObject.KeyString.Length] = 0;
               
                ReadOnlySpan<byte> byteSpan = new ReadOnlySpan<byte>(array);
                clientInterface.WriteByte(mappingObject.Command);
                clientInterface.Write(byteSpan);
            }
        }
        #endregion

        #region Validation and Authenticaiton

        /// <summary>
        /// Handles the login and create account process flow
        /// </summary>
        /// <returns></returns>
        private bool AuthenticateUser()
        {
            //set variables for the method to use
            int i;
            byte[] response = new byte[1];
            
            while (true)
            {
                //read the byte sent from the client
                i = clientInterface.ReadByte();

                //if the client is trying to create an account                
                if (i == (int)ClientCommands.CREATE_ACCOUNT)
                {
                    //call the method to create an account
                    bool accountCreated = false;

                    accountCreated = CreateUserAccount();
                    if (!accountCreated)
                        SendMessage(ServerCommands.USER_AUTH_FAIL);

                    //if the account hs been created, respond with true
                    if (accountCreated) {
                        SendMessage(ServerCommands.USER_AUTH_PASS);
                        return true;
                    }

                    return false;
                }
                //if the client is trying to login
                else if (i == (int)ClientCommands.LOGIN)
                {

                    //if the user is valid, respond with true
                    if (ValidateUser())
                    {
                        SendMessage(ServerCommands.USER_AUTH_PASS);
                        return true;
                    }

                    SendMessage(ServerCommands.USER_AUTH_FAIL);
                    return false;
                }
                else
                {
                    //A bad byte was sent by the client
                    return false;
                }
            }
        }

        /// <summary>
        /// Handles creation of a user account
        /// </summary>
        /// <returns></returns>
        private bool CreateUserAccount()
        {
            //get the username from the client
            int size = clientInterface.ReadByte();
            StringBuilder cUser = new StringBuilder();
            do
            {
                cUser.Append(ReadChar());
                size--;
            }
            while (size > 0);

            //get the password from the client
            size = clientInterface.ReadByte();
            StringBuilder cPass = new StringBuilder();
            do
            {
                cPass.Append(ReadChar());
                size--;
            }
            while (size > 0);

            bool result = databseService.AddNewUser(cUser.ToString(), cPass.ToString(), connecitonString);

            if(result)
            {
                return true;
            }

            return false;
        }

        private char ReadChar()
        {
            int i = clientInterface.ReadByte() << 8;
            i += clientInterface.ReadByte();
            return (char)i;
        }

        /// <summary>
        /// Verifys if the user credentials are valid
        /// </summary>
        /// <returns></returns>
        private bool ValidateUser()
        {
            //get the username from the client
            int size = clientInterface.ReadByte();
            StringBuilder cUser = new StringBuilder();
            do
            {
                cUser.Append(ReadChar());
                size--;
            }
            while (size > 0);

            //get the password from the client
            size = clientInterface.ReadByte();
            StringBuilder cPass = new StringBuilder();
            do
            {
                cPass.Append(ReadChar());
                size--;
            }
            while (size > 0);

            //convert the stringbuilders to string for validation
            //this will be turned into sending the info to the DAL
            string user = cUser.ToString();
            string pass = cPass.ToString();

            bool result = databseService.VerifyPassword(user, pass, connecitonString);
            if (result)
                playername = user;
            return result;
        }

        public void LeaveGame()
        {
            inGame = false;
            forceLeaveGame = true;
            Console.WriteLine($"{playername} leaving game. Value: {inGame}");
        }

        #endregion

        #region Cleanup


        private void DisposePlayer()
        {
            gController.RemovePlayer(this);
        }
        /// <summary>
        /// Closes the network stream for the instance
        /// </summary>
        private void CloseConneciton()
        {
            clientInterface.Close();
        }

        #endregion

    }
}
