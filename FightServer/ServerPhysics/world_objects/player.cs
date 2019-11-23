using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Net;

public enum Fighter
{
    ganchev, coffman, trump, lego
}

public enum Control
{
    movingleft = 0x01,
    kick = 0x02,
    kick2 = 0x04,
    attack = 0x08,
    block = 0x10,
    jump = 0x20,
    crouch = 0x40,
    movingright = 0x80,
}

namespace WindowsFormsApplication1
{
    public class player : world_object
    {
        public bool isactive = true;

        private int fuel = 0;
        public int health = 100;

        public int move_cooldown = 0;
        public int hit_cooldown = 0;
    
        private double ySpeed = 0.0;
        private double xSpeed = 0.0;

        private bool jumpingLastTick = false;

        //cotrol variables
        private bool jumping = false;
        private bool movingLeft = false;
        private bool movingRight = false;
        private bool attacking = false;

        private byte control_byte = (byte)0;

        private Fighter type;

        public System.Net.Sockets.NetworkStream player_stream;
 

        public player(Fighter f, object_manager o, int xx, int yy, System.Net.Sockets.NetworkStream strm)
        {
            id = o.world_object_count++;
            type = f;
            manager = o;

            x = xx;
            y = yy;
            width = 30;
            height = 30;


            player_stream = strm;

        }

        //------------------------------byte manipulators------------------------------------

        public void set_control_bit(Control c)
        {
                //xor the bytes
                this.control_byte = (byte)(this.control_byte | (byte)c);
        }

        public void unset_control_bit(Control c)
        {
            //xor the bytes
            this.control_byte = (byte)(this.control_byte & ~(byte)c);
        }

        public bool get_control_bit(Control c)
        {
            return (byte)(this.control_byte & (byte)c) == (byte)c;
        }

        public void set_control_byte(byte b)
        {
            this.control_byte = b;
        }

        //---------------------------------------------------------------------------------


        public override void game_tick()
        {

            //control bits
            jumping = get_control_bit(Control.jump);
            movingLeft = get_control_bit(Control.movingleft);
            movingRight = get_control_bit(Control.movingright);
            attacking = get_control_bit(Control.attack);





            if (attacking && move_cooldown <= 0)
            {
                if (type == Fighter.ganchev)
                {
                    this.manager.create_projectile(x, y, this);
                }
                else
                {
                    this.manager.create_attack(x, y, this);
                }
            }

            //stats
            if (health <= 0)
            {
                this.manager.control_list.Remove(this);
            }

            if (move_cooldown > 0) move_cooldown--;
            if (hit_cooldown > 0) hit_cooldown--;

            //attacks
            if(hit_cooldown <=0)
            {
                checkAttacks();
            }

            //physics

            bool jumpPressed = (jumping && !jumpingLastTick);


            if(movingLeft && movingRight)
            {
                if(xSpeed>1) xSpeed--;
                else if(xSpeed<-1) xSpeed++;
                else xSpeed=0;
            }
            else if(movingRight)
            {
                if(xSpeed>6) xSpeed--;
                else if(xSpeed<4) xSpeed++;
                else xSpeed=5;
            }
            else if(movingLeft)
            {
                if(xSpeed>-4) xSpeed--;
                else if(xSpeed<-4) xSpeed++;
                else xSpeed=-5;
            }
            else
            {
                if(xSpeed>1) xSpeed--;
                else if(xSpeed<-1) xSpeed++;
                else xSpeed=0;
            }

            x = x + (int)Math.Ceiling(xSpeed);

            bool touchingWall = false;
            int xsign = (int) Math.Sign(xSpeed);
            if(xsign!=0) {
                while (isTouchingAny()){
                    x -= xsign;
                    xSpeed = 0;

                    touchingWall =true;
                }
            }

            ySpeed = ySpeed + 1;


            y = y + (int)Math.Ceiling(ySpeed);
            bool touchingGround = false;
            int ysign = (int) Math.Sign(ySpeed);
            if(ysign!=0) {
                while (isTouchingAny()) {
                    y -= ysign;
                    ySpeed = 0;

                    if(ysign==1)
                    {
                        touchingGround =true;
                        fuel = 100;
                    }
                }
            }

            if(jumpPressed && touchingGround)
            {

                 ySpeed = -13;
                
            }

            if (y >= 700 ) {

                y = 30;
            }


            if(type == Fighter.coffman)
            {
                if(fuel>0 && jumping)
                {
                    if(ySpeed > -2)
                    {
                        ySpeed = -2;
                        fuel-=1;

                        if(xSpeed>.5) xSpeed=.5;
                        else if(xSpeed<-.5)  xSpeed=-.5;
                    }

                }
            }

            if(type == Fighter.ganchev) {
                if (touchingWall) {
                    if (jumpPressed && ySpeed > 0) {
                        ySpeed = -10;
                        xSpeed = 10 * xsign * -1;

                    } else if (ySpeed > 0) {
                        ySpeed = 1;
                    }
                }
                else if(fuel>0 && jumpPressed && !touchingGround)
                {
                    ySpeed = -10;
                    fuel=0;
                }
            }

            jumpingLastTick = jumping;
        }

        public override void draw(System.Drawing.Graphics g)
        {
            base.draw(g);

            Brush b = new System.Drawing.SolidBrush(System.Drawing.Color.Red);

            //draw red health bar
            g.FillRectangle(b, x, y - 20,100, 10);

            b = new System.Drawing.SolidBrush(System.Drawing.Color.Green);
            //draw the platform
            g.FillRectangle(b, x, y - 20,health, 10);
        }

        public void key_down(char c)
        {
            if (isactive)
            {
                if (c == 'W')
                {
                    set_control_bit(Control.jump);
                }
                if (c == 'A')
                {
                    set_control_bit(Control.movingleft);
                }
                if (c == 'D')
                {
                    set_control_bit(Control.movingright);
                }
                if (c == 'S')
                {
                    set_control_bit(Control.attack);
                }
            }
        }

        public void key_up(char c)
        {
            if (isactive)
            {
                if(c == 'W')
                {
                    unset_control_bit(Control.jump);
                }
                if (c == 'A')
                {
                    unset_control_bit(Control.movingleft);
                }
                if (c == 'D')
                {
                    unset_control_bit(Control.movingright);
                }
                if (c == 'S')
                {
                    unset_control_bit(Control.attack);
                }
                if( c == 'N')
                {
                    if (type == Fighter.lego)
                    {
                        type = Fighter.ganchev;
                        clr = clr = System.Drawing.Color.DarkGray;
                    }
                    else if (type == Fighter.ganchev)
                    {
                        type = Fighter.coffman;
                        clr = System.Drawing.Color.Green;
                    }
                    else if (type == Fighter.coffman)
                    {
                        type = Fighter.lego;
                        clr = System.Drawing.Color.Red;
                    }
                }
            }
        }

        public void checkAttacks()
        {
            for (int i = manager.attack_list.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
            {
                attack atk = (attack)manager.attack_list[i];
                if (isTouching(atk))
                {
                    atk.hit(this);
                }
            }

        }

    }
}
