using System;
using System.Drawing;

namespace ServerPhysics.World_Objects
{
    public class Attack : WorldObject
    {
        public bool facingRight = true;

        public int lifetime = 5;
        public int damage = 10;
        public Player owner;
        public int xoffset = 50;
        public int yoffset = -20;
        public int cooldown = 60;

        public Attack(int x, int y, Player owner, ObjectManager o)
        {
            id = o.world_object_count++;
            this.x = x;
            this.y = y;
            this.owner = owner;
            this.manager = o;
            this.width=25;
            this.height=10;

            this.owner.move_cooldown = cooldown;

            this.clr = System.Drawing.Color.Blue;

            o.attack_list.Add(this);
        }
        public Attack()
        {
           
        }


        public override void game_tick()
        {

            int ownerwidth = owner.getWidth();
            int ownerheight = owner.getHeight();

            int ownerCenterX = owner.x + ownerwidth/2;
            int ownerCenterY = owner.y + ownerheight/2;

            if(facingRight)
            {
                x = ownerCenterX + xoffset;
                y = ownerCenterY + yoffset;
            }
            else
            {
                x = ownerCenterX - xoffset-width;
                y = ownerCenterY - yoffset;
            }
            


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
                p.hit_cooldown = lifetime+5;// after a player is hit by an attack they are immune to incoming attacks for a small second
                p.setYSpeed(p.getJumpSpeed());
            }
        }
    }
}
