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
        

        public GameSimulation() 
        {
            player1Joined = false;
        }

        public bool PlayerOneJoined()
        {
            return player1Joined;
        }

        public bool PlayerTwoJoined()
        {
            return player2 != null ? true : false;
        }

        public void AddPlayer1(PlayerController player)
        {
            player1 = player;
            player1Joined = true;

        }

        public void AddPlayer2(PlayerController player)
        {
            player2 = player;
        }


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


        public void Simulation(PlayerController player1, PlayerController player2)
        {

            SelectCharacter();

            Thread.Sleep(new TimeSpan(0, 0, 1));

            player1.SendOpponentPlayer(player2.selectedCharacter, 1);
            player2.SendOpponentPlayer(player1.selectedCharacter, 0);

            ObjectManager GameObject = new ObjectManager(player1.clientInterface, (Fighter)player1.selectedCharacter, player2.clientInterface, (Fighter)player2.selectedCharacter);

            List<ServerPhysics.World_Objects.WorldObject> platformList = GameObject.platform_list;
            
            foreach(ServerPhysics.World_Objects.Platform platform in platformList)
            {
                player1.SendPlatform(MatchObjectType.Platform, platform.id, platform.x, platform.y, platform.width, platform.height);
                player2.SendPlatform(MatchObjectType.Platform, platform.id, platform.x, platform.y, platform.width, platform.height);
            }

            player1.SendMessage(ServerCommands.START_MATCH);
            player2.SendMessage(ServerCommands.START_MATCH);


            bool player1Win = GameObject.start_physics();

            //NotifyGameEnd();

            player1.LeaveGame();
            player2.LeaveGame();
        }

        private void NotifyGameEnd()
        {
            player1.SendMessage(ServerCommands.END_GAME_INSTANCE);
            player2.SendMessage(ServerCommands.END_GAME_INSTANCE);
        }

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
