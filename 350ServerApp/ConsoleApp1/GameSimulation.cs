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
            
            Console.WriteLine($"Game creation start.");
            Simulation(player1, player2);
            
        }


        public void Simulation(PlayerController player1, PlayerController player2)
        {

            SelectCharacter();

            Thread.Sleep(new TimeSpan(0, 0, 1));
            //command byte is CreateMatchObject
            //byte for platform (matchObjectType)
            //5 2bytes for matchobjectId, 2 for x, 2 for y, 2 for height
            Console.WriteLine("Creating match object");
            //player1.SendMessage(ServerCommands.CREATE_MATCH_OBJECT);
            //player2.SendMessage(ServerCommands.CREATE_MATCH_OBJECT);

            player1.SendOpponentPlayer(player2.selectedCharacter);
            player2.SendOpponentPlayer(player1.selectedCharacter);


            //player1.SendPlatform(MatchObjectType.Platform, 4, 53, 65, 45, 70);
            //player2.SendPlatform(MatchObjectType.Platform, 4, 53, 65, 45, 70);

            ObjectManager GameObject = new ObjectManager(player1.clientInterface, (Fighter)player1.selectedCharacter, player2.clientInterface, (Fighter)player2.selectedCharacter);

            List<ServerPhysics.World_Objects.WorldObject> platformList = GameObject.platform_list;
            
            foreach(ServerPhysics.World_Objects.Platform platform in platformList)
            {
                player1.SendPlatform(MatchObjectType.Platform, platform.id, platform.x, platform.y, platform.width, platform.height);
                player2.SendPlatform(MatchObjectType.Platform, platform.id, platform.x, platform.y, platform.width, platform.height);
            }

            Console.WriteLine("Sent Platform");

            player1.SendMessage(ServerCommands.START_MATCH);
            player2.SendMessage(ServerCommands.START_MATCH);
            Console.WriteLine("Sent start match");

            Console.WriteLine("Starting physics");
            GameObject.start_physics();

            //Task.Run(() => player1.EchoCommand(player2));
            //Task.Run(() => player2.EchoCommand(player1));

            while(true)
            {
                continue;
            }


            //Console.WriteLine($"Game spawned for {player1.user} and {player2.user}");
            //Thread player1Thread = new Thread(player1.listen);
            //player1Thread.Start();
            //Thread player2Thread = new Thread(player2.listen);
            //player2Thread.Start();
        }

        public void SelectCharacter()
        {
            player1.SendMessage(ServerCommands.SELECT_CHARACTER);
            player2.SendMessage(ServerCommands.SELECT_CHARACTER);
            player1.GetCharacter();
            player2.GetCharacter();

            while (player1.CharacterPicked == false || player2.CharacterPicked == false)
            {
                Console.WriteLine($"Player1: {(player1.CharacterPicked == true  ? GetEnumName(player1.selectedCharacter) : "Not Selected" )}, Player2: {(player2.CharacterPicked == true ? GetEnumName(player2.selectedCharacter) : "Not Selected")}");
                Thread.Sleep(new TimeSpan(0, 0, 1));
            }
            Console.WriteLine("Exited Loop");
        }

        private string GetEnumName(CharacterEnum num)
        {
            switch (num)
            {
                case CharacterEnum.Coffman:
                    return "Coffman";
                case CharacterEnum.Ganchev:
                    return "Ganchev";
                case CharacterEnum.Trump:
                    return "Trump";
                case CharacterEnum.Lego:
                    return "Lego";
                default:
                    return "Invalid Character Selection";
            }
        }
    }

    public class PlayerMatchup
    {
        public PlayerMatchup()
        {

        }

        public PlayerMatchup(PlayerSocketController player1, PlayerSocketController player2)
        {
            p1 = player1;
            p2 = player2;
        }
        public PlayerSocketController p1;
        public PlayerSocketController p2;
    }


}
