using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using GameServer.Enumerations;

namespace GameServer
{
    public class GameController
    {
        public Dictionary<string, GameSimulation> gameListing;
        public List<PlayerController> playerList;
        public GameController()
        {
            gameListing = new Dictionary<string, GameSimulation>();
            playerList = new List<PlayerController>();
        }

        public void StartGame(string game)
        {
            gameListing[game].Run();
        }

        public bool CreateGame(string gameName, PlayerController player)
        {

            if (gameListing.ContainsKey(gameName))
                return false;

            Console.WriteLine($"Creating Match {gameName}");
            byte[] responseByte = new byte[1] { (byte)ServerCommands.VALID_MATCH_NAME };
            ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
            player.SendMessage(response);
            player.inGame = true;

            //create the game
            GameSimulation game = new GameSimulation();
            game.AddPlayer1(player);

            //add the game to a dicitonary
            gameListing.Add(gameName, game);
            Console.WriteLine($"{gameListing.Count} games in wait");
            StartGame(gameName);
            NotifyLobbyUpdate(gameName, true);
            return true;
        }

        public void EndGame(string game, PlayerController player)
        {
            throw new NotImplementedException();
        }

        public bool AddPlayer(string game, PlayerController player)
        {
            if (!gameListing.ContainsKey(game))
            { 
                Console.WriteLine("Game Not Preset");
                return false;
            }
            else
            {
                if(gameListing.GetValueOrDefault(game).PlayerOneJoined())
                {
                    Console.WriteLine("Adding player 2");
                    player.inGame = true;
                    NotifyLobbyUpdate(game, false);
                    gameListing.GetValueOrDefault(game).AddPlayer2(player);
                    return true;
                }
                else
                {
                    Console.WriteLine("Adding Player 1");
                    gameListing.GetValueOrDefault(game).AddPlayer1(player);
                    return true;

                    
                }
            }            
        }

        async private void NotifyLobbyUpdate(string match, bool add)
        {
        //    string a = "addition";
        //    string b = "removal";
        //    Console.WriteLine($"Pulsing {(add ? a : b)} of {match}");
            foreach(PlayerController player in playerList)
            {
                await Task.Run(() => player.PulseLobby(match, add));
            }
        }

        public void SendAllGames(PlayerController player)
        {
            foreach(string game in gameListing.Keys)
            {
                NotifyLobbyUpdate(game, true);
            }
        }
    }
}
