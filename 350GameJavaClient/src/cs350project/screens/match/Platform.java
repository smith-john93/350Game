package cs350project.screens.match;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;

public class Platform extends MatchObject {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        //draw the platform
        g2D.setColor(Color.black);
        g2D.fillRect(0,0, getWidth(), getHeight());
    }

    @Override
    public void receiveData(DataInputStream dataInputStream) {
        System.out.print("ready to receive data");
        try {
            short[] data = new short[4];
            for(int i = 0; i < data.length; i++) {
                data[i] = dataInputStream.readShort();
            }
            System.out.print("received data ");
            for(short s : data) {
                System.out.print("," + s);
            }
            //System.out.println();
            setBounds(data[0],data[1],data[2],data[3]);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
