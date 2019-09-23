/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Mark Masone
 */
public abstract class PlayerCharacter {
    
    private CharacterState currentState;
    private final CharacterResources resources;
    private int x;
    private int y;
    private int width;
    private int height;
    private int direction;
    
    public PlayerCharacter(CharacterResources resources, CharacterState startState) {
        currentState = startState;
        this.resources = resources;
        direction = 1;
    }
    
    public void setState(CharacterState state) {
        currentState = state;
    }
    
    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int x = 0;
        if(direction < 0)
            x = w;
        g2.drawImage(srcImg, x, 0, w * direction, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    public void draw(Graphics2D g2d) {
        Image scaledImage = getScaledImage(resources.getStateImage(currentState),width,height);
        g2d.drawImage(scaledImage,x,y,null);
    }
}
