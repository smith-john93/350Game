using System;
using System.Drawing;
using System.Collections.Generic;



namespace WindowsFormsApplication1
{
    public class object_manager
    {
        public List<List<world_object>> world_object_list;

        public List<world_object> platform_list;// a list of objects that act as platforms
        public List<world_object> player_list;// a list of objects that players control
        public List<world_object> attack_list;// a list of attacks

        public int world_object_count = 0;


        public  object_manager()
        {
            world_object_list = new List<List<world_object>>();

            // Create a list of platforms.
            platform_list = new List<world_object>();
            world_object_list.Add(platform_list);
            player_list = new List<world_object>();
            world_object_list.Add(player_list);
            attack_list = new List<world_object>();
            world_object_list.Add(attack_list);

            create_platform(50, 300, 150, 200);
            create_platform(150, 450, 450, 50);
            create_platform(600, 400, 100, 100);
            create_platform(700, 100, 50, 400);
            create_platform(550, 100, 50, 250);
            create_platform(300, 150, 100, 25);
            create_platform(150, 150, 100, 25);

            
    

            new player(Fighter.lego, this, 150, 30);
            player p = new player(Fighter.lego, this, 500, 30);
            p.isactive = false;

        }

        public object_manager(System.Net.Sockets.NetworkStream player1_stream,Fighter PlayerOneFighter, System.Net.Sockets.NetworkStream player2_stream, Fighter PlayerTwoFighter)
        {
            world_object_list = new List<List<world_object>>();

            // Create a list of platforms.
            platform_list = new List<world_object>();
            world_object_list.Add(platform_list);
            player_list = new List<world_object>();
            world_object_list.Add(player_list);
            attack_list = new List<world_object>();
            world_object_list.Add(attack_list);

            create_platform(50, 300, 150, 200);
            create_platform(150, 450, 450, 50);
            create_platform(600, 400, 100, 100);
            create_platform(700, 100, 50, 400);
            create_platform(550, 100, 50, 250);
            create_platform(300, 150, 100, 25);
            create_platform(150, 150, 100, 25);




            new player(PlayerOneFighter, this, 150, 30, player1_stream);
            player p = new player(PlayerTwoFighter, this, 500, 30, player2_stream);
            p.isactive = false;

        }

        public void create_platform(int x, int y, int width, int height)
        {
            platform p = new platform(x, y, width, height, this);
            
            
        }

        public void create_attack(int x, int y, player owner)
        {
            attack a = new attack(x, y, owner, this);

            attack_list.Add(a);
        }

        public void create_projectile(int x, int y, player owner)
        {
            attack a = new projectile(x, y, owner, this);

            attack_list.Add(a);
        }

        ~object_manager()
        {
            //should delete list
        }

 

        public void draw(System.Drawing.Graphics g)
        {
            g.Clear(System.Drawing.Color.White);

            foreach(List<world_object> wol in world_object_list)
            {
                for (int i = wol.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
                {
                    wol[i].draw_tick(g);
                }
            }
        }

        public void game_tick()
        {
            foreach (List<world_object> wol in world_object_list)
            {
                for (int i = wol.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
                {
                    wol[i].game_tick();
                }
            }
        }

        public void key_down(char c)
        {
            foreach (player play in player_list)
            {
                play.key_down(c);
            }
        }

        public void key_up(char c)
        {
            foreach (player play in player_list)
            {
                play.key_up(c);
            }
        }
    }
}
