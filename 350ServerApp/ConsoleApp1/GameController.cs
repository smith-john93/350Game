using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;

namespace GameServer
{
    public class GameController
    {
        public Dictionary<string, GameSimulation> gameListing;
        public GameController()
        {
            gameListing = new Dictionary<string, GameSimulation>();
        }

        public bool CreateGame(string gameName, PlayerController player)
        {

            if (gameListing.ContainsKey(gameName))
                return false;
            
            //create the game
            GameSimulation game = new GameSimulation(player);
            //add the game to a dicitonary
            gameListing.Add(gameName, game);

            game.Run();
            return true;
        }

        public void EndGame(string game, PlayerController player)
        {
            throw new NotImplementedException();
        }

        public void AddPlayer(string game, PlayerController player)
        {
            if (!gameListing.ContainsKey(game))
                Console.WriteLine("Game Not Preset");
            else
                gameListing.GetValueOrDefault(game).Player2Join(player);
            
        }

    }
}
