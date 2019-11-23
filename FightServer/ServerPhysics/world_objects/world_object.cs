using System;
using System.Drawing;

namespace WindowsFormsApplication1
{
    public class world_object
    {
        public int id = -1;
        public int x = 150;
        public int y = 200;
        public int width = 20;
        public int height = 5;
        public System.Drawing.Color clr = System.Drawing.Color.Red;
        public object_manager manager;

      

        public virtual void draw_tick(System.Drawing.Graphics g)
        {
            draw(g);
        }

        public virtual void game_tick()
        {
          
        }

        public virtual void draw(System.Drawing.Graphics g)
        {
            Brush b = new System.Drawing.SolidBrush(clr);
            
            //draw the platform
            g.FillRectangle(b, x, y, width, height);

        }

        public bool isTouching(world_object p)
        {
            return (x < p.x + p.width &&
                    x + width > p.x &&
                    y < p.y + p.height &&
                    y + height > p.y);
            // collision detected
        }

        public bool isTouchingAny()
        {
            foreach (platform plat in manager.platform_list)
            {
                if (isTouching(plat)) return true;
            }
            return false;
        }

    }
}
