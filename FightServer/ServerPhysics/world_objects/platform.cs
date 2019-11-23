using System;
using System.Drawing;

namespace WindowsFormsApplication1
{
    public class platform : world_object
    {
        public platform(int x, int y, int width, int height, object_manager o)
        {
            id = o.world_object_count++;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            manager = o;

            this.clr = System.Drawing.Color.Black;
        }


    }
}
