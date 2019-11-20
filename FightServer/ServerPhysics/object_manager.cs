using System;
using System.Drawing;
using System.Collections.Generic;



namespace WindowsFormsApplication1
{
    public class object_manager
    {
        public List<world_object> world_object_list;
        public List<platform> platform_list;// a list of objects that act as platforms
        public List<player> control_list;// a list of objects that players control
        public List<attack> attack_list;// a list of attacks



        public  object_manager()
        {
            // Create a list of platforms.
            world_object_list = new List<world_object>();
            platform_list = new List<platform>();
            control_list = new List<player>();
            attack_list = new List<attack>();

            create_platform(50, 300, 150, 200);
            create_platform(150, 450, 450, 50);
            create_platform(600, 400, 100, 100);
            create_platform(700, 100, 50, 400);
            create_platform(550, 100, 50, 250);
            create_platform(300, 150, 100, 25);
            create_platform(150, 150, 100, 25);

            player p = create_player(Fighter.normal, 150, 30);
            p = create_player(Fighter.normal, 500, 30);
            p.isactive = false;
        }

        public void create_platform(int x, int y, int width, int height)
        {
            platform p = new platform(x, y, width, height, this);

            world_object_list.Add(p);
            platform_list.Add(p);
        }

        public player create_player(Fighter f, int x, int y)
        {
            player p = new player(f, this, x, y);

            world_object_list.Add(p);
            control_list.Add(p);

            return p;
        }

        public void create_attack(int x, int y, player owner)
        {
            attack a = new attack(x, y, owner, this);

            world_object_list.Add(a);
            attack_list.Add(a);
        }

        public void create_projectile(int x, int y, player owner)
        {
            attack a = new projectile(x, y, owner, this);

            world_object_list.Add(a);
            attack_list.Add(a);
        }

        ~object_manager()
        {
            //should delete list
        }

 

        public void draw(System.Drawing.Graphics g)
        {
            g.Clear(System.Drawing.Color.White);

            for (int i = world_object_list.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
            {
                world_object_list[i].draw_tick(g);
            }
        }

        public void game_tick()
        {
            for (int i = world_object_list.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
            {
                world_object_list[i].game_tick();
            }
        }

        public void key_down(char c)
        {
            foreach (player play in control_list)
            {
                play.key_down(c);
            }
        }

        public void key_up(char c)
        {
            foreach (player play in control_list)
            {
                play.key_up(c);
            }
        }
    }
}
