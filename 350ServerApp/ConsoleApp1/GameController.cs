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

        public bool CreateGame(string gameName, PlayerController player)
        {

            if (gameListing.ContainsKey(gameName))
                return false;

            byte[] responseByte = new byte[1] { (byte)ServerCommands.VALID_MATCH_NAME };
            ReadOnlySpan<byte> response = new ReadOnlySpan<byte>(responseByte);
            player.SendMessage(response);
            player.inGame = true;

            //create the game
            GameSimulation game = new GameSimulation(player);
            //add the game to a dicitonary
            gameListing.Add(gameName, game);

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
                    gameListing.GetValueOrDefault(game).AddPlayer1(player);
                    return true;
                }
                else
                {
                NotifyLobbyUpdate(game, false);
                gameListing.GetValueOrDefault(game).AddPlayer2(player);
                return true;
                }
            }            
        }

        async private void NotifyLobbyUpdate(string match, bool add)
        {
            string a = "addition";
            string b = "removal";
            Console.WriteLine($"Pulsing {(add ? a : b)} of {match}");
            foreach(PlayerController player in playerList)
            {
                await Task.Run(() => player.PulseLobby(match, add));
            }
        }
    }
}
