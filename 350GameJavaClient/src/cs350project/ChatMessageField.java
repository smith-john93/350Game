/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 *
 * @author Mark Masone
 */
public class ChatMessageField extends JComponent {
    
    private int x = 0;
    private int y = 0;
    private int arcw = 25;
    private int arch = 25;
    private int padding = 10;
    private int h = 24;
    private int w = 500;
    private JTextField jtf;
    
    public ChatMessageField() {
        setBounds(0,0,padding + w + padding,padding + h + padding);
        jtf = new JTextField();
        jtf.setBounds(padding,padding,w,h);
        jtf.setColumns(40);
        add(jtf);
        setVisible(false);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Shape bubble = new RoundRectangle2D.Double(
                x,
                y,
                padding + w + padding,
                padding + h + padding,
                arcw,
                arch
        );
        g2d.setColor(Color.DARK_GRAY);
        g2d.draw(bubble);
        g2d.fill(bubble);
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible)
            jtf.requestFocus();
    }

    @Override
    public void addKeyListener(java.awt.event.KeyListener kl) {
        jtf.addKeyListener(kl);
    }

    @Override
    public void removeKeyListener(java.awt.event.KeyListener kl) {
        jtf.removeKeyListener(kl);
    }

    public String getText() {
        return jtf.getText();
    }

    public void clearText() {
        jtf.setText("");
    }

}
