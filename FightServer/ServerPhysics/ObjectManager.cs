using System;
using System.Drawing;
using System.Collections.Generic;
using ServerPhysics.World_Objects;
using System.Threading;
using System.Threading.Tasks;


namespace ServerPhysics
{
    public class ObjectManager
    {
        public List<List<WorldObject>> world_object_list;

        public List<WorldObject> platform_list;// a list of objects that act as platforms
        public List<WorldObject> player_list;// a list of objects that players control
        public List<WorldObject> attack_list;// a list of attacks

        public int world_object_count = 2;

        
        public  ObjectManager()
        {
            world_object_list = new List<List<WorldObject>>();

            // Create a list of platforms.
            platform_list = new List<WorldObject>();
            world_object_list.Add(platform_list);
            player_list = new List<WorldObject>();
            world_object_list.Add(player_list);
            attack_list = new List<WorldObject>();
            world_object_list.Add(attack_list);

            create_platform(50, 300, 150, 200);
            create_platform(150, 450, 450, 50);
            create_platform(600, 400, 100, 100);
            create_platform(700, 100, 50, 400);
            create_platform(550, 100, 50, 250);
            create_platform(300, 150, 100, 25);
            create_platform(150, 150, 100, 25);           
   

            //new Player(Fighter.lego, this, 150, 30);
            //Player p = new Player(Fighter.lego, this, 500, 30);
            //p.isactive = false;

            //start_physics();
        }

        public ObjectManager(System.Net.Sockets.NetworkStream player1_stream,Fighter PlayerOneFighter, System.Net.Sockets.NetworkStream player2_stream, Fighter PlayerTwoFighter)
        {
            world_object_list = new List<List<WorldObject>>();

            // Create a list of platforms.
            platform_list = new List<WorldObject>();
            world_object_list.Add(platform_list);
            player_list = new List<WorldObject>();
            world_object_list.Add(player_list);
            attack_list = new List<WorldObject>();
            world_object_list.Add(attack_list);

            /*
            create_platform(50, 300, 150, 200);
            create_platform(150, 450, 450, 50);
            create_platform(600, 400, 100, 100);
            create_platform(700, 100, 50, 400);
            create_platform(550, 100, 50, 250);
            create_platform(300, 150, 100, 25);
            create_platform(150, 150, 100, 25);
            */

            create_platform(-10, 700, 1400, 100);
            create_platform(-10, 300, 50, 400);
            create_platform(1300, 300, 50, 400);

            Player player1 = new Player(PlayerOneFighter, this, 150, 30, player1_stream);
            //player_list.Add(player1);
            Player player2 = new Player(PlayerTwoFighter, this, 500, 30, player2_stream);

            player1.AddOpponent(player2);
            player2.AddOpponent(player1);
            //player_list.Add(player2);            
        }

        public void start_physics()
        {
            Console.WriteLine($"player count in game: {player_list.Count}");
            Console.WriteLine($"platform count in game: {platform_list.Count}");
            foreach (Player p in player_list)
            {
                Console.WriteLine("in for each");
                Task.Run(() => p.GetPlayerByte());
            }

            Console.WriteLine("out of for loop");
            while(true)
            {
                //Thread.Sleep(new TimeSpan(0, 0, 0, 0, 100));
                Thread.Sleep(50);
                //Console.WriteLine("ticking...");
                game_tick();
                UpdatePlayers();
            }
        }

        async private void UpdatePlayers()
        {
            foreach(Player p in player_list)
            {
                Task.Run(() => p.UpdatePlayer());
            }
            // Task.Run(() => player_list[0].SendGameUpdate(player_list[1].x, player_list[1].y, player_list[1].control_byte, (byte)player_list[1].health));
            //Task.Run(() => player_list[1].SendGameUpdate(player_list[0].x, player_list[0].y, player_list[0].control_byte, (byte)player_list[0].health));

        }
        public void create_platform(int x, int y, int width, int height)
        {
            Platform p = new Platform(x, y, width, height, this);                     
        }

        public void create_attack(int x, int y, Player owner)
        {
            Attack a = new Attack(x, y, owner, this);

            attack_list.Add(a);
        }

        public void create_projectile(int x, int y, Player owner)
        {
            Attack a = new Projectile(x, y, owner, this);

            attack_list.Add(a);
        }

        ~ObjectManager()
        {
            //should delete list
        }

 
        /*
        public void draw(System.Drawing.Graphics g)
        {
            g.Clear(System.Drawing.Color.White);

            foreach(List<WorldObject> wol in world_object_list)
            {
                for (int i = wol.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
                {
                    wol[i].draw_tick(g);
                }
            }
        }
        */

        public void game_tick()
        {
            foreach (List<WorldObject> wol in world_object_list)
            {
                for (int i = wol.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
                {
                    wol[i].game_tick();
                }
            }
        }

        public void key_down(char c)
        {
            foreach (Player play in player_list)
            {
                play.key_down(c);
            }
        }

        public void key_up(char c)
        {
            foreach (Player play in player_list)
            {
                play.key_up(c);
            }
        }
    }
}
