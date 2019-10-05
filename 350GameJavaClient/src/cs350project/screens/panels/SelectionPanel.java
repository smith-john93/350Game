/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import cs350project.characters.*;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class SelectionPanel extends JPanel {
    
    private int selectedIndex;
    private final int selections;
    private final PlayerCharacter[] characters;
    
    public SelectionPanel() {
        setBounds(0,0,1600,900);
        selectedIndex = 0;
        selections = 4;
        characters = new PlayerCharacter[]{new Chev(),new Coffman(),new ElPresidente(),new LegoMan()};
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        URL url = SelectionPanel.class.getResource("/resources/background.jpg");
        
        try {
            BufferedImage background = ImageIO.read(url);
            g2d.drawImage(background, 0, 0, this);
        } catch(Exception e) {
            
        }
        
        for(int i = 0; i < selections; i++) {
            if(i == selectedIndex)
                g2d.setColor(Color.red);
            else
                g2d.setColor(Color.white);
            int x = 300 + (i * 140);
            int y = 300;
            int w = 100;
            int h = 100;
            g2d.drawRect(x, y, w, h);
            PlayerCharacter character = characters[i];
            character.setBounds(x, y, w, h);
            character.draw(g2d);
        }
    }
    
    public PlayerCharacter getPlayer1Selection() {
        return characters[selectedIndex];
    }
    
    public PlayerCharacter getPlayer2Selection() {
        return null;
    }

    public void selectNextRight() {
        if(selectedIndex < selections - 1) {
            selectedIndex++;
            repaint();
        }
    }

    public void selectNextLeft() {
        if(selectedIndex > 0) {
            selectedIndex--;
            repaint();
        }
    }
}
