using System;
using System.Drawing;

namespace WindowsFormsApplication1
{
    public class projectile : attack
    {
        public int speed = 10;

        public projectile(int x, int y, player owner, object_manager o)
        {
            id = o.world_object_count++;
            this.x = x;
            this.y = y;
            this.owner = owner;
            this.manager = o;

            this.damage = 4;

            this.owner.move_cooldown = 3;

            this.clr = System.Drawing.Color.DarkMagenta;

            o.attack_list.Add(this);
        }

        public override void game_tick()
        {
            x += speed;


            if (lifetime < 0)
            {
                this.manager.attack_list.Remove(this);
            }
            else
            {
                lifetime--;
            }

            if (isTouchingAny())
            {
                this.manager.attack_list.Remove(this);
            }
        }

        public override void hit(player p)
        {
            if (owner != p)
            {
                p.health -= damage;
                p.hit_cooldown = 3;

                this.manager.attack_list.Remove(this);
            }
        }
    }
}
