package cs350project.screens.match;

import java.awt.*;
import javax.swing.JComponent;

public class Platform extends JComponent {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        //draw the platform
        g2D.setColor(Color.black);
        g2D.fillRect(0,0, getWidth(), getHeight());
    }

}
