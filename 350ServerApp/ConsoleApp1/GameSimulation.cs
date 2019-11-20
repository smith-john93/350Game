﻿using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using GameServer.Enumerations;
using WindowsFormsApplication1;

namespace GameServer
{
    public class GameSimulation
    {
        public bool RequestShutdown;
        PlayerController player1;
        PlayerController player2;

        public GameSimulation(PlayerController player)
        {
            player1 = player;
        }

        public void Player2Join(PlayerController player)
        {
            player2 = player;
        }

        public void Run()
        {
            while (true && !RequestShutdown)
            {
                if (player2 == null)
                {
                    continue;
                }
                else
                {
                    Console.WriteLine($"Game creation start.");
                    Thread.Sleep(new TimeSpan(0, 0, 5));
                    Simulation(player1, player2);
                }
            }
        }


        public void Simulation(PlayerController player1, PlayerController player2)
        {

            SelectCharacter();
            
            Console.WriteLine($"Player1 {player1.selectedCharacter}, Player2: {player2.selectedCharacter}");
            while (true)
            {
                Console.WriteLine("In Game");
                Thread.Sleep(new TimeSpan(0, 0, 1));
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



            string a = "not picked";
            while (player1.CharacterPicked == false || player2.CharacterPicked == false)
            {
                Console.WriteLine($"Player1: {(player1.CharacterPicked == true  ? GetEnumName(player1.selectedCharacter) : "Not Selected" )}, Player2: {(player2.CharacterPicked == true ? GetEnumName(player2.selectedCharacter) : "Not Selected")}");
                Thread.Sleep(new TimeSpan(0, 0, 1));
            }                          
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
