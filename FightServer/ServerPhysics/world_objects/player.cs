using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Net;
using ServerPhysics.DataTransferObject;

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

namespace ServerPhysics.World_Objects
{
    public class Player : WorldObject
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

        private static int MOVEMENTSPEED = 20;
        private static int WALLJUMPSPEED = -40;
        private static int JUMPSPEED = -13;
        private static int MAXFUEL = 100;
        private static int HOVERSPEED = 5;
        private static int ACCELERATION = 5;

        private byte control_byte = (byte)0;
        private Player Opponent;
        private Fighter type;

        public System.Net.Sockets.NetworkStream player_stream;
 
        public void AddOpponent(Player p)
        {
            Opponent = p;
        }
        
        public PlayerStats GetPlayerStats()
        {
            return new PlayerStats(x, y, control_byte, (byte)health);
        }

        public Player(Fighter f, ObjectManager o, int xx, int yy, System.Net.Sockets.NetworkStream strm)
        {
            Console.WriteLine("O");
            id = o.world_object_count++;
            type = f;
            manager = o;

            x = xx;
            y = yy;
            width = 200;
            height = 200;

            player_stream = strm;

            o.player_list.Add(this);
        }
        

        public Player(Fighter f, ObjectManager o, int xx, int yy)
        {
            id = o.world_object_count++;
            type = f;
            manager = o;

            x = xx;
            y = yy;
            width = 200;
            height = 200;

            o.player_list.Add(this);
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

        async public void GetPlayerByte()
        {
            while(true)
            {
                byte i = (byte)player_stream.ReadByte();
                this.control_byte = (byte)player_stream.ReadByte();
            }
        }

        async public void UpdatePlayer()
        {
            PlayerStats s = new PlayerStats(x, y, control_byte, (byte)health);
            await Task.Run(() => SendGameUpdate(s, true));
            await Task.Run(() => SendGameUpdate(Opponent.GetPlayerStats(), false));

        }
        async public void SendGameUpdate(PlayerStats stats, bool local)
        {

            //Console.WriteLine("Sending update");
            player_stream.WriteByte((byte)2);

            //send 2 byte ID, 0 for this, 1 for opponent
            player_stream.WriteByte(0);
            if (local)
                player_stream.WriteByte(0);
            else
                player_stream.WriteByte(1);


            
            //send 1 byte character state
            player_stream.WriteByte(stats.controlByte);

            //send 2 byte x-cord
            player_stream.WriteByte((byte)(stats.x >>8));
            player_stream.WriteByte((byte)stats.x);


            //send 2 byte y-cord
            player_stream.WriteByte((byte)(stats.y >> 8));
            player_stream.WriteByte((byte)stats.y);
            
            //send health
            player_stream.WriteByte((byte)stats.health);
        }

        //---------------------------------------------------------------------------------


        public override void game_tick()
        {
            //Console.WriteLine("Tick");
            //Console.WriteLine(this.x);

            //control bits
            jumping = get_control_bit(Control.jump);
            movingLeft = get_control_bit(Control.movingleft);
            movingRight = get_control_bit(Control.movingright);
            attacking = get_control_bit(Control.attack);

            #region physics
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
                this.manager.player_list.Remove(this);
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
                
                if(xSpeed>1) xSpeed-=ACCELERATION;
                else if(xSpeed<-1) xSpeed+= ACCELERATION;
                else xSpeed=0;
                
            }
            else if(movingRight)
            {
                
                if(xSpeed>(MOVEMENTSPEED+1)) xSpeed-= ACCELERATION;
                else if(xSpeed<(MOVEMENTSPEED-1)) xSpeed+= ACCELERATION;
                else xSpeed=MOVEMENTSPEED;
                
            }
            else if(movingLeft)
            {
                
                if(xSpeed>-(MOVEMENTSPEED-1)) xSpeed-= ACCELERATION;
                else if(xSpeed<-(MOVEMENTSPEED+1)) xSpeed+= ACCELERATION;
                else xSpeed=-MOVEMENTSPEED;
                
            }
            else
            {
                
                if(xSpeed>1) xSpeed-= ACCELERATION;
                else if(xSpeed<-1) xSpeed+= ACCELERATION;
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
                        fuel = MAXFUEL;
                    }
                }
            }

            if(jumpPressed && touchingGround)
            {

                 ySpeed = JUMPSPEED;
                
            }

            if (y >= 700 ) {

                y = 30;
            }


            if(type == Fighter.coffman)
            {
                if(fuel>0 && jumping)
                {
                    if(ySpeed > -HOVERSPEED)
                    {
                        ySpeed = -HOVERSPEED;
                        fuel-=1;

                        if(xSpeed>HOVERSPEED) xSpeed=HOVERSPEED;
                        else if(xSpeed<-HOVERSPEED)  xSpeed=-HOVERSPEED;
                    }

                }
            }

            if(type == Fighter.ganchev) {
                if (touchingWall) {
                    if (jumpPressed && ySpeed > 0) {
                        ySpeed = JUMPSPEED;
                        xSpeed = WALLJUMPSPEED * xsign;

                    } else if (ySpeed > 0) {
                        ySpeed = HOVERSPEED;
                    }
                }
                else if(fuel>0 && jumpPressed && !touchingGround)
                {
                    ySpeed = JUMPSPEED;
                    fuel=0;
                }
            }

            jumpingLastTick = jumping;
            #endregion

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
                Attack atk = (Attack)manager.attack_list[i];
                if (isTouching(atk))
                {
                    atk.hit(this);
                }
            }

        }

    }
}
