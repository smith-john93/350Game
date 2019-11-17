using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;


public enum Fighter
{
    normal, ninja, mage
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
    private bool jumping = false;
    private bool movingLeft = false;
    private bool movingRight = false;

    private Fighter type;


    

    public player(Fighter f, object_manager o, int xx, int yy)
    {
        type = f;
        manager = o;

        x = xx;
        y = yy;
        width = 30;
        height = 30;
    }



    public override void game_tick()
    {

        //stats
        if(health <= 0)
        {
            this.manager.world_object_list.Remove(this);
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

        if(jumpPressed)
        {
            if(touchingGround)
            {
                ySpeed = -13;
            }
        }

        if (y >= 700 ) {

            y = 30;
        }


        if(type == Fighter.mage)
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

        if(type == Fighter.ninja) {
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
                if (!jumping)
                {
                    jumping = true;
                }
            }
            if (c == 'A')
            {
                movingLeft = true;
            }
            if (c == 'D')
            {
                movingRight = true;
            }
            if (c == 'S')
            {
                if (move_cooldown <= 0)
                {
                    if(type == Fighter.ninja)
                    {
                        this.manager.create_projectile(x, y, this);
                    }
                    else
                    {
                        this.manager.create_attack(x, y, this);
                    }
                }
            }
        }
    }

    public void key_up(char c)
    {
        if (isactive)
        {
            if (c == 'W')
            {

                jumping = false;

            }
            if (c == 'A')
            {
                movingLeft = false;
            }
            if (c == 'D')
            {
                movingRight = false;
            }
            if (c == 'N')
            {
                if (type == Fighter.normal)
                {
                    type = Fighter.ninja;
                    clr = clr = System.Drawing.Color.DarkGray;
                }
                else if (type == Fighter.ninja)
                {
                    type = Fighter.mage;
                    clr = System.Drawing.Color.Green;
                }
                else if (type == Fighter.mage)
                {
                    type = Fighter.normal;
                    clr = System.Drawing.Color.Red;
                }
            }
        }
    }

    public void checkAttacks()
    {
        for (int i = manager.attack_list.Count - 1; i >= 0; i--)///must be a for loop because it is changing the list inside of it
        {
            attack atk = manager.attack_list[i];
            if (isTouching(atk))
            {
                atk.hit(this);
            }
        }

    }

    }
}
