using System;
using System.Drawing;

namespace ServerPhysics.World_Objects
{
    public class Platform : WorldObject
    {
        public Platform(int x, int y, int width, int height, ObjectManager o)
        {
            id = o.world_object_count++;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            manager = o;

            this.clr = System.Drawing.Color.Black;

            o.platform_list.Add(this);
        }


    }
}
