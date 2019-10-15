/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author Mark Masone
 */
public abstract class PlayerCharacter extends JComponent {
    
    private CharacterState currentState;
    private final CharacterResources resources;
    private int direction;
    
    public PlayerCharacter(CharacterState defaultCharacterState, String defaultFileName) {
        currentState = defaultCharacterState;
        resources = new CharacterResources(defaultCharacterState,defaultFileName);
        direction = 1;
    }
    
    public CharacterResources getCharacterResources() {
        return resources;
    }
    
    public void setState(CharacterState state) {
        currentState = state;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private Image getScaledImage(Image srcImg){
        Rectangle bounds = getBounds();
        int width = bounds.width;
        int height = bounds.height;
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int scaledImageX = 0;
        if(direction < 0)
            scaledImageX = width;
        g2.drawImage(srcImg, scaledImageX, 0, width * direction, height, null);
        g2.dispose();

        return resizedImg;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Image scaledImage = getScaledImage(resources.getStateImage(currentState));
        g2d.drawImage(scaledImage,0,0,null);
    }
    
    public abstract CharacterClass getCharacterClass();
}
