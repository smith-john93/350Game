using System;
using System.Drawing;

namespace ServerPhysics.World_Objects
{
    public class Attack : WorldObject
    {
        public int lifetime = 60;
        public int damage = 10;
        public Player owner;
        public int xoffset = 45;
        public int yoffset = 15;

        public Attack(int x, int y, Player owner, ObjectManager o)
        {
            id = o.world_object_count++;
            this.x = x;
            this.y = y;
            this.owner = owner;
            this.manager = o;

            this.owner.move_cooldown = lifetime;

            this.clr = System.Drawing.Color.Blue;

            o.attack_list.Add(this);
        }
        public Attack()
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

        public virtual void hit(Player p)
        {
            if(owner != p)
            { 
                p.health -= damage;
                p.hit_cooldown = lifetime+5;
            }
        }
    }
}
