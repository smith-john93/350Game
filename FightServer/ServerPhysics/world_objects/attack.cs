using System;
using System.Drawing;

namespace WindowsFormsApplication1
{
    public class attack : world_object
    {
        public int lifetime = 60;
        public int damage = 10;
        public player owner;
        public int xoffset = 45;
        public int yoffset = 15;

        public attack(int x, int y, player owner, object_manager o)
        {
            id = o.world_object_count++;
            this.x = x;
            this.y = y;
            this.owner = owner;
            this.manager = o;

            this.owner.move_cooldown = lifetime;

            this.clr = System.Drawing.Color.Blue;
        }
        public attack()
        {
           
        }


        public override void game_tick()
        {
            x = owner.x + xoffset;
            y = owner.y + yoffset;

            if(lifetime<0)
            {
                this.manager.attack_list.Remove(this);
            }
            else
            {
                lifetime--;
            }
        }

        public virtual void hit(player p)
        {
            if(owner != p)
            { 
                p.health -= damage;
                p.hit_cooldown = lifetime+5;
            }
        }
    }
}
