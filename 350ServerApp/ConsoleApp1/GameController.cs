using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using GameServer.Enumerations;
using System.Linq;

namespace GameServer
{
    public class GameController
    {        
        //A dictionary to maintain games and references to the games
        public Dictionary<string, GameSimulation> gameListing;

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
            Console.WriteLine($"Creating match {gameName}");
            //verify the games does not exist in the dictionary yet
            if (gameListing.ContainsKey(gameName))
                return false;

            //respond to the client to inform them the name of the game is valid
            byte[] responseByte = new byte[1] { (byte)ServerCommands.VALID_MATCH_NAME };
            ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
            player.SendMessage(response);


            player.SetInGameState();

            //create the game
            GameSimulation game = new GameSimulation();
            game.AddPlayer1(player);

            //add the game to a dicitonary
            gameListing.Add(gameName, game);
            Console.WriteLine($"{gameListing.Count} games in wait");
            NotifyLobbyUpdate(gameName, true);
            StartGame(gameName);
            return true;
        }

        public void EndGame(string game)
        {
            gameListing.Remove(game);
        }

        public bool AddPlayer(string game, PlayerController player)
        {
            Console.WriteLine("Adding PLayer");
            if (!gameListing.ContainsKey(game))
            { 
                return false;
            }
            else
            {
                if(gameListing.GetValueOrDefault(game).PlayerOneJoined())
                {
                    player.SetInGameState();
                    NotifyLobbyUpdate(game, false);
                    gameListing.GetValueOrDefault(game).AddPlayer2(player);
                    return true;
                }
                else
                {
                    gameListing.GetValueOrDefault(game).AddPlayer1(player);
                    return true;                  
                }
            }            
        }

        public void RemovePlayer(PlayerController player)
        {
            playerList.Remove(player);
        }

        async private void NotifyLobbyUpdate(string match, bool add)
        {
            foreach(PlayerController player in playerList)
            {
                await Task.Run(() => player.PulseLobby(match, add));
            }
        }

        public void SendAllGames(PlayerController player)
        {
            foreach(KeyValuePair<string, GameSimulation> game in gameListing.Where(a => a.Value.PlayerTwoJoined() == false))
            {
                NotifyLobbyUpdate(game.Key, true);
            }
        }
    }
}
