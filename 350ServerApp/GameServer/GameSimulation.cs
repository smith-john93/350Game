using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using GameServer.Enumerations;
using ServerPhysics;

namespace GameServer
{
    public class GameSimulation
    {
        public bool RequestShutdown;
        PlayerController player1;
        PlayerController player2;
        private bool player1Joined;
        
        //Create the object for a game
        public GameSimulation() 
        {
            player1Joined = false;
        }

        /// <summary>
        /// Getter for if player one is in the game
        /// </summary>
        /// <returns></returns>
        public bool PlayerOneJoined()
        {
            return player1Joined;
        }

        /// <summary>
        /// Getter for if player 2 is in the game
        /// </summary>
        /// <returns></returns>
        public bool PlayerTwoJoined()
        {
            return player2 != null ? true : false;
        }

        /// <summary>
        /// Adds player 1 into this game and sets their in game state
        /// </summary>
        /// <param name="player"></param>
        public void AddPlayer1(PlayerController player)
        {
            player1 = player;
            player1Joined = true;

        }

        /// <summary>
        /// Adds player 2 into this game
        /// </summary>
        /// <param name="player"></param>
        public void AddPlayer2(PlayerController player)
        {
            player2 = player;
        }

        /// <summary>
        /// Begins a loop to check for a second player
        /// begins the match once player 2 joins
        /// </summary>
        public void Run()
        {

            while (player2 == null)
            {
                if (player2 == null)
                {
                    continue;
                }
            }           
            Simulation(player1, player2);
        }

        /// <summary>
        /// Spawn and begn execution of a game
        /// </summary>
        /// <param name="player1"></param>
        /// <param name="player2"></param>
        public void Simulation(PlayerController player1, PlayerController player2)
        {
            //get the character selection for both characters
            SelectCharacter();
            
            Thread.Sleep(new TimeSpan(0, 0, 1));

            //notify both players who the other chose
            player1.SendOpponentPlayer(player2.selectedCharacter, 1);
            player2.SendOpponentPlayer(player1.selectedCharacter, 0);

            //create the game object
            ObjectManager GameObject = new ObjectManager(player1.clientInterface, (Fighter)player1.selectedCharacter, player2.clientInterface, (Fighter)player2.selectedCharacter);

            //generate the platforms and send them to both clients
            List<ServerPhysics.World_Objects.WorldObject> platformList = GameObject.platform_list;            
            foreach(ServerPhysics.World_Objects.Platform platform in platformList)
            {
                player1.SendPlatform(MatchObjectType.Platform, platform.id, platform.x, platform.y, platform.width, platform.height);
                player2.SendPlatform(MatchObjectType.Platform, platform.id, platform.x, platform.y, platform.width, platform.height);
            }

            //notify both clients the match is starting
            player1.SendMessage(ServerCommands.START_MATCH);
            player2.SendMessage(ServerCommands.START_MATCH);

            //run the game
            bool player1Win = GameObject.start_physics();
            
            //at this point the game is over and leave the game
            NotifyGameEnd();

            player1.LeaveGame();
            player2.LeaveGame();

            Thread.Sleep(new TimeSpan(0, 0, 0, 1));
        }

        /// <summary>
        /// Sends both players a notification that the game ended
        /// </summary>
        private void NotifyGameEnd()
        {
            player1.SendMessage(ServerCommands.END_GAME_INSTANCE);
            player2.SendMessage(ServerCommands.END_GAME_INSTANCE);
        }

        /// <summary>
        /// places both characters into a selection state
        /// </summary>
        public void SelectCharacter()
        {
            player1.SendMessage(ServerCommands.SELECT_CHARACTER);
            player2.SendMessage(ServerCommands.SELECT_CHARACTER);
            player1.GetCharacter();
            player2.GetCharacter();

            while (!player1.HasSelectdCharacter() || !player2.HasSelectdCharacter())
            {
                Thread.Sleep(new TimeSpan(0, 0, 1));
            }
        }
    }
}
