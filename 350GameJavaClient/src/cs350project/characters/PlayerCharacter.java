/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import cs350project.ImageResource;
import cs350project.screens.match.MatchObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public static final int FRAME_DELAY = 200;
    private final HashMap<Integer,Timer> attackTimers;
    private final int[] attackStates;
    private final int attackStateMask;
    private final int movementStateMask;
    private int attackStateCode;
    private final int matchWidth = 200;
    private final int matchHeight = 200;
    private final int thumbnailWidth = 100;
    private final int thumbnailHeight = 100;
    private int health;
    private int playerID;
    
    public PlayerCharacter() {
        characterResourceManager = new CharacterResourceManager(getClass());
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
    
    public int getHealth() {
        return health;
    }
    
    public int getPlayerID() {
        return playerID;
    }
    
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    
    public void loadThumbnailResource() {
        characterResourceManager.loadImageResource(
                CharacterState.THUMBNAIL,
                thumbnailWidth,
                thumbnailHeight
        );
        setSize(thumbnailWidth, thumbnailHeight);
    }
    
    public void loadAllImageResources() {
        characterResourceManager.loadAllImageResources(matchWidth,matchHeight);
        setSize(matchWidth, matchHeight);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        for(int attackState : attackStates) {
            ImageResource characterResource = characterResourceManager.getImageResource(attackState);
            if(characterResource != null) {
                int attackFramesCount = characterResource.getFrames().length;
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
    }
    
    private void enableState(int stateCode) {
        // Make sure the bits are off before or.
        if((this.stateCode & stateCode) == 0x0) {
            setState(this.stateCode | stateCode);
        }
    }
    
    private void disableState(int stateCode) {
        //System.out.println("disabling state: " + stateCode);
        // Make sure the bits are on before xor.
        if((this.stateCode & stateCode) == stateCode) {
            setState(this.stateCode ^ stateCode);
        }
    }
    
    public void setState(int stateCode) {
        //System.out.println("setting state: " + stateCode);
        this.stateCode = stateCode;
        ImageResource characterResource = characterResourceManager.getImageResource(stateCode);
        if(characterResource != null) {
            setFrames(characterResource.getFrames());
        }
    }
    
    private void setFrames(Image[] frames) {
        this.frames = frames;
        frameIndex = 0;
        currentFrame = frames[frameIndex];
        //lastFrameTime = 0;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        //lastFrameTime = 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("repaint player character");
        Graphics2D g2d = (Graphics2D)g;
        
        boolean movingRight = (stateCode & CharacterState.MOVING_RIGHT) == CharacterState.MOVING_RIGHT;
        boolean movingLeft = (stateCode & CharacterState.MOVING_LEFT) == CharacterState.MOVING_LEFT;

        if(movingRight) {
            setDirection(1);
        } else if(movingLeft) {
            setDirection(-1);
        }
        
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - lastFrameTime;
        if(diff >= FRAME_DELAY) {
            
            ImageResource characterResource = characterResourceManager.getImageResource(stateCode);
            
            if(characterResource != null && frames != null) {
                if(diff != currentTime && diff >= FRAME_DELAY * 2) {
                    int fastForward = (int)((diff - FRAME_DELAY) / FRAME_DELAY);
                    frameIndex += fastForward;
                    droppedFrames += fastForward;
                    //System.out.println("dropped frames " + droppedFrames);
                    if(frameIndex < frames.length || characterResource.loops()) {
                        frameIndex %= frames.length;
                    } else {
                        frameIndex = frames.length - 1;
                    }
                }

                currentFrame = frames[frameIndex];
                lastFrameTime = System.currentTimeMillis();

                if(frameIndex < frames.length - 1) {
                    frameIndex++;
                } else if(characterResource.loops()) {
                    frameIndex = 0;
                }
            }
        }
        
        if(currentFrame != null) {
            if(direction > 0) {
                g2d.drawImage(currentFrame,0,0,null);
            } else {
                g2d.drawImage(currentFrame,200,0,-200,200,null);
            }
        }
    }
    
    // This method may require synchronization
    @Override
    public void receiveData(DataInputStream dataInputStream) throws IOException {
        int newStateCode = dataInputStream.readByte();
        if(newStateCode < 0) {
            newStateCode ^= 0xffffff00;
        }
        System.out.println("new state code " + newStateCode);
        if((newStateCode | attackStateCode) != stateCode) {
            int newAttackStateCode = newStateCode & attackStateMask;
            //System.out.println("new state code: " + newStateCode);
            //System.out.println("old attack state: " + attackStateCode);
            //System.out.println("new attack state: " + newAttackStateCode);
            Timer attackTimer = attackTimers.get(attackStateCode);
            Timer newAttackTimer = attackTimers.get(newAttackStateCode);
            
            // If the attack is a new or different attack
            if(newAttackTimer != null && newAttackTimer != attackTimer) {
                if(attackTimer != null && attackTimer.isRunning()) {
                    attackTimer.stop();
                    //System.out.println("starting different attack timer");
                } else {
                    //System.out.println("starting new attack timer");
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
        health = dataInputStream.readByte();
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        //System.out.println("health: " + health);
        //System.out.println(" stateCode: " + stateCode + " x: " + x + " y: " + y);
        setLocation(x,y);
    }
    
    public abstract CharacterClass getCharacterClass();
    public abstract CharacterType getCharacterType();
}
