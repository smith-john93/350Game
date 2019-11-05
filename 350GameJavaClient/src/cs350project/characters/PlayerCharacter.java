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
    
    protected final CharacterResources resources;
    private int direction;
    private Image currentFrame;
    private int frameIndex = 0;
    private Image[] frames;
    private long lastFrameTime = 0;
    private int droppedFrames = 0;
    private final int defaultStateCode;
    private CharacterResource characterResource;
    private int stateCode;
    private final short objectID;
    public static final int FRAME_DELAY = 200;
    
    public PlayerCharacter(short objectID, int defaultStateCode, CharacterResource cr) {
        this.objectID = objectID;
        this.defaultStateCode = defaultStateCode;
        resources = new CharacterResources(defaultStateCode, cr);
        direction = 1;
    }

    public short getObjectID() {
        return objectID;
    }
    
    public CharacterResources getCharacterResources() {
        return resources;
    }
    
    public void enableState(int stateCode) {
        // Make sure the bits are off before or.
        if((this.stateCode & stateCode) == 0x0) {
            setState(this.stateCode | stateCode);
        }
    }
    
    public void disableState(int stateCode) {
        // Make sure the bits are on before xor.
        if((this.stateCode & stateCode) == stateCode) {
            setState(this.stateCode ^ stateCode);
        }
    }
    
    public void setState(int stateCode) {
        this.stateCode = stateCode;
        characterResource = resources.getResource(stateCode);
        if(characterResource == null) {
            characterResource = resources.getResource(defaultStateCode);
        }
        setFrames(characterResource.getFrames());
    }
    
    private void setFrames(Image[] frames) {
        this.frames = frames;
        frameIndex = 0;
        currentFrame = getScaledImage(frames[frameIndex]);
        //lastFrameTime = 0;
    }
    
    public int getFramesCount(int stateCode) {
        return resources.getResource(stateCode).getFramesCount();
    }

    public void setDirection(int direction) {
        this.direction = direction;
        //lastFrameTime = 0;
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
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - lastFrameTime;
        if(diff >= FRAME_DELAY) {
            //if(frameIndex == frames.length) {
                //setState(defaultStateCode);
            //}
            if(diff != currentTime && diff >= FRAME_DELAY * 2) {
                int fastForward = (int)((diff - FRAME_DELAY) / FRAME_DELAY);
                frameIndex += fastForward;
                droppedFrames += fastForward;
                System.out.println("dropped frames " + droppedFrames);
                if(frameIndex < frames.length || characterResource.loops()) {
                    frameIndex %= frames.length;
                } else {
                    frameIndex = frames.length - 1;
                }
            }
            currentFrame = getScaledImage(frames[frameIndex]);
            lastFrameTime = System.currentTimeMillis();
            if(frameIndex < frames.length - 1) {
                frameIndex++;
            } else if(characterResource.loops()) {
                frameIndex = 0;
                //System.out.println("reset");
            }
        }
        g2d.drawImage(currentFrame,0,0,null);
    }
    
    public abstract CharacterClass getCharacterClass();
}
