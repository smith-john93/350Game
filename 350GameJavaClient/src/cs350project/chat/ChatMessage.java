/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.chat;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Mark Masone
 */
public class ChatMessage {
    
    private final String message,fontFamily;
    private final int fontSize,x,arcw,arch,padding;
    private int y;
    private double opacity;
    private int height;
    
    public ChatMessage(String message) {
        this.message = message;
        fontFamily = "Arial";
        fontSize = 16;
        x = 20;
        y = 800;
        arcw = 32;
        arch = 32;
        padding = 10;
        opacity = 1;
        height = 0;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void draw(Graphics2D g2d) {
        Font chatFont = new Font(fontFamily,Font.BOLD,fontSize);
        g2d.setFont(chatFont);
        Rectangle2D r2D = g2d.getFontMetrics().getStringBounds(message, g2d);
        Shape chat = new RoundRectangle2D.Double(
                x,
                y,
                padding + r2D.getWidth() + padding,
                padding + r2D.getHeight() + padding,
                arcw,
                arch
        );
        int a = (int)(opacity * 255);
        int alphaMask = new Color(255,255,255,a).getRGB();
        int rgba = Color.DARK_GRAY.getRGB() & alphaMask;
        g2d.setColor(new Color(rgba,true));
        g2d.draw(chat);
        g2d.fill(chat);
        rgba = Color.WHITE.getRGB() & alphaMask;
        g2d.setColor(new Color(rgba,true));
        g2d.drawString(message, x + padding, y + padding + fontSize);
        height = padding + (int)r2D.getHeight() + padding;
    }
}
