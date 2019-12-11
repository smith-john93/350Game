using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Threading;
using System.Linq;

namespace GameServer
{
    public class GameController
    {        
        //A dictionary to maintain games and references to the games
        public Dictionary<string, GameSimulation> gameListing;

        //Set to true while notifications are being sent out
        //otherwise we can run into issues when accessing and modifying the list
        bool LockDownGameList = false;

        //A list to maintain each player
        public List<PlayerController> playerList;

        /// <summary>
        /// Initializes the game controller
        /// </summary>
        public GameController()
        {
            gameListing = new Dictionary<string, GameSimulation>();
            playerList = new List<PlayerController>();
        }

        /// <summary>
        /// Starts the specified game in the dictionary
        /// </summary>
        /// <param name="game"></param>
        public void StartGame(string game)
        {
            gameListing[game].Run();
        }

        /// <summary>
        /// Creates a game and adds it to the dictionary
        /// </summary>
        /// <param name="gameName"></param>
        /// <param name="player"></param>
        /// <returns></returns>
        public bool CreateGame(string gameName, PlayerController player)
        {
            //verify the games does not exist in the dictionary yet
            if (gameListing.ContainsKey(gameName))
                return false;

            //respond to the client to inform them the name of the game is valid
            byte[] responseByte = new byte[1] { (byte)ServerCommands.VALID_MATCH_NAME };
            ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
            player.SendMessage(response);

            //set this player to in game
            player.SetInGameState();

            //create the game
            GameSimulation game = new GameSimulation();
            game.AddPlayer1(player);

            while(LockDownGameList)
            {
                Thread.Sleep(new TimeSpan(0, 0, 0, 0, 10));
            }
            LockDownGameList = true;
            //add the game to a dicitonary
            gameListing.Add(gameName, game);

            LockDownGameList = false;

            //notify the lobby that this game is available
            NotifyLobbyUpdate(gameName, true);

            StartGame(gameName);
        
            gameListing.Remove(gameName);
            return true;
        }

        /// <summary>
        /// Adds a player to a game
        /// </summary>
        /// <param name="game"></param>
        /// <param name="player"></param>
        /// <returns></returns>
        public bool AddPlayer(string game, PlayerController player)
        {
            //ensure the game exists
            if (!gameListing.ContainsKey(game))
            { 
                return false;
            }
            else
            {
                //make sure player1 is in the game
                if(gameListing.GetValueOrDefault(game).PlayerOneJoined())
                {
                    player.SetInGameState();
                    NotifyLobbyUpdate(game, false);
                    gameListing.GetValueOrDefault(game).AddPlayer2(player);
                    return true;
                }
                //add player 2 if player 1 is in the game
                else
                {
                    gameListing.GetValueOrDefault(game).AddPlayer1(player);
                    return true;                  
                }
            }            
        }

        /// <summary>
        /// removes a player from the Player List
        /// </summary>
        /// <param name="player"></param>
        public void RemovePlayer(PlayerController player)
        {
            playerList.Remove(player);
        }

        /// <summary>
        /// notify every client that a match is avalable
        /// </summary>
        /// <param name="match"></param>
        /// <param name="add"></param>
        async private void NotifyLobbyUpdate(string match, bool add)
        {
            while (LockDownGameList)
                Thread.Sleep(new TimeSpan(0, 0, 0, 0, 10));

            LockDownGameList = true;
            
            foreach (PlayerController player in playerList)
            {
                await Task.Run(() => player.PulseLobby(match, add));
            }
            LockDownGameList = false;
        }

        /// <summary>
        /// Sendonly player the entire active lobby
        /// </summary>
        /// <param name="player"></param>
        public void SendAllGames(PlayerController player)
        {
            while (LockDownGameList)
                Thread.Sleep(new TimeSpan(0, 0, 0, 0, 10));

            LockDownGameList = true;
            foreach(KeyValuePair<string, GameSimulation> game in gameListing.Where(a => a.Value.PlayerTwoJoined() == false))
            {
                Task.Run(() => player.PulseLobby(game.Key, true));
            }
            LockDownGameList = false;
        }
    }
}
