package cs350project;

import java.awt.*;

public class Platform {

    public int x = 150;
    public int y = 200;
    public int width = 150;
    public int height = 200;

    Platform(int x,int y, int width, int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    protected void paint(Graphics g) {

        //draw the platform
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
    }


}
