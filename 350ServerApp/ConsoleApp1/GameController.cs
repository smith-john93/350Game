using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

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
            
            //create the game
            GameSimulation game = new GameSimulation(player);
            //add the game to a dicitonary
            gameListing.Add(gameName, game);

            NotifyLobbyUpdate(gameName, true);
            game.Run();
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
                NotifyLobbyUpdate(game, false);
                gameListing.GetValueOrDefault(game).Player2Join(player);
                return true;
            }            
        }
        async private void NotifyLobbyUpdate(string match, bool add)
        {
            string a = "addition";
            string b = "removal";
            Console.WriteLine($"Pulsing {(add ? a:b)} of {match}");
            foreach(PlayerController player in playerList)
            {
                player.PulseLobby(match, add);
            }
        }
    }
}
