using System;
using System.Drawing;

namespace ServerPhysics.World_Objects
{
    public class Platform : WorldObject
    {
        /// <summary>
        /// Makes a platform
        /// </summary>
        /// <param name="x"></param>
        /// <param name="y"></param>
        /// <param name="width"></param>
        /// <param name="height"></param>
        /// <param name="o"></param>
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
