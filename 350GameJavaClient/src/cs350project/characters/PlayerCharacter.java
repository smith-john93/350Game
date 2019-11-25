/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import cs350project.GameResource;
import cs350project.screens.match.MatchObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.Timer;

/**
 *
 * @author Mark Masone
 */
public abstract class PlayerCharacter extends MatchObject {
    
    protected final CharacterResourceManager characterResourceManager;
    private int direction;
    private Image currentFrame;
    private int frameIndex = 0;
    private Image[] frames;
    private long lastFrameTime = 0;
    private int droppedFrames = 0;
    private int stateCode;
    private final short objectID;
    public static final int FRAME_DELAY = 200;
    private final HashMap<Integer,Timer> attackTimers;
    private final int[] attackStates;
    private final int attackStateMask;
    private final int movementStateMask;
    private int attackStateCode;
    
    public PlayerCharacter(short objectID, int defaultStateCode) {
        this.objectID = objectID;
        characterResourceManager = new CharacterResourceManager(defaultStateCode);
        direction = 1;
        attackTimers = new HashMap<>();
        attackStates = new int[]{
            CharacterState.PUNCH,
            CharacterState.HIGH_KICK,
            CharacterState.LOW_KICK
        };
        attackStateMask = CharacterState.PUNCH | CharacterState.HIGH_KICK | CharacterState.LOW_KICK;
        movementStateMask = attackStateMask ^ 0xffff;
    }
    
    public void loadResources() {
        characterResourceManager.loadResources(this);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        for(int attackState : attackStates) {
            int attackFramesCount = getFramesCount(attackState);
            int attackTime = PlayerCharacter.FRAME_DELAY * (attackFramesCount + 1);
            Timer attackTimer = new Timer(attackTime, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    disableState(attackState);
                    
                    // this fixed the stuck attack glitch
                    attackStateCode = 0;
                }
            });
            attackTimer.setRepeats(false);
            attackTimers.put(attackState, attackTimer);
        }
    }

    public short getObjectID() {
        return objectID;
    }
    
    public CharacterResourceManager getCharacterResources() {
        return characterResourceManager;
    }
    
    private void enableState(int stateCode) {
        // Make sure the bits are off before or.
        if((this.stateCode & stateCode) == 0x0) {
            setState(this.stateCode | stateCode);
        }
    }
    
    private void disableState(int stateCode) {
        System.out.println("disabling state: " + stateCode);
        // Make sure the bits are on before xor.
        if((this.stateCode & stateCode) == stateCode) {
            setState(this.stateCode ^ stateCode);
        }
    }
    
    public void setState(int stateCode) {
        System.out.println("setting state: " + stateCode);
        this.stateCode = stateCode;
        GameResource characterResource = characterResourceManager.getResource(stateCode);
        setFrames(characterResource.getFrames());
    }
    
    private void setFrames(Image[] frames) {
        this.frames = frames;
        frameIndex = 0;
        currentFrame = getScaledImage(frames[frameIndex]);
        //lastFrameTime = 0;
    }
    
    public int getFramesCount(int stateCode) {
        GameResource characterResource = characterResourceManager.getResource(stateCode);
        if(characterResource == null) {
            return 0;
        }
        return characterResource.getFrames().length;
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
        
        boolean movingRight = (stateCode & CharacterState.MOVING_RIGHT) == CharacterState.MOVING_RIGHT;
        boolean movingLeft = (stateCode & CharacterState.MOVING_LEFT) == CharacterState.MOVING_LEFT;

        if(movingRight) {
            setDirection(1);
        } else if(movingLeft) {
            setDirection(-1);
        }
        
        GameResource characterResource = characterResourceManager.getResource(stateCode);
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
    
    // This method may require synchronization
    @Override
    public void receiveData(DataInputStream dataInputStream) throws IOException {
        int newStateCode = dataInputStream.readShort();
        if((newStateCode | attackStateCode) != stateCode) {
            int newAttackStateCode = newStateCode & attackStateMask;
            System.out.println("new state code: " + newStateCode);
            System.out.println("old attack state: " + attackStateCode);
            System.out.println("new attack state: " + newAttackStateCode);
            Timer attackTimer = attackTimers.get(attackStateCode);
            Timer newAttackTimer = attackTimers.get(newAttackStateCode);
            
            // If the attack is a new or different attack
            if(newAttackTimer != null && newAttackTimer != attackTimer) {
                if(attackTimer != null && attackTimer.isRunning()) {
                    attackTimer.stop();
                    System.out.println("starting different attack timer");
                } else {
                    System.out.println("starting new attack timer");
                }
                newAttackTimer.start();
                setState(newStateCode);
                attackStateCode = newAttackStateCode;
            } 
            // If no attack is in progress and there is a new movement
            else if(attackTimer == null || !attackTimer.isRunning()) {
                setState(newStateCode);
                //attackStateCode = 0;
            }
        }
        short x = dataInputStream.readShort();
        short y = dataInputStream.readShort();
        //System.out.println(" stateCode: " + stateCode + " x: " + x + " y: " + y);
        setLocation(x,y);
    }
    
    public abstract CharacterClass getCharacterClass();
    public abstract CharacterType getCharacterType();
}
