/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import cs350project.GameResource;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class CharacterResourceManager {
    
    private final HashMap<Integer,GameResource> resources;
    private final int defaultStateCode;
    
    public CharacterResourceManager(int defaultStateCode) {
        this.defaultStateCode = defaultStateCode;
        resources = new HashMap<>();
    }
    
    public void setResource(int stateCode, GameResource characterResource) {
        System.out.println(stateCode + " " + characterResource.getFileName());
        resources.put(stateCode, characterResource);
    }
    
    public GameResource getResource(int stateCode) {
        //System.out.println("get resource " + stateCode);
        GameResource characterResource = resources.get(stateCode);
        if(characterResource == null) {
            characterResource = resources.get(defaultStateCode);
        }
        return characterResource;
    }
    
    private class CharacterResourceData {
        
        private String fileName;
        private GameResource.Type type;
        private int[] stateCodes;
        
        private CharacterResourceData(String fileName, GameResource.Type type, int stateCode) {
            this(fileName, type, new int[]{stateCode});
        }
        
        private CharacterResourceData(String fileName, GameResource.Type type, int[] stateCodes) {
            this.fileName = fileName;
            this.type = type;
            this.stateCodes = stateCodes;
        }
    }

    void loadResources(PlayerCharacter character) {
        String name = character.getClass().getSimpleName();
        String characterFolder = "characters/" + name + "/";
        CharacterResourceData[] characterResourcesData = {
                new CharacterResourceData(
                        characterFolder + "Idle.gif",
                        GameResource.Type.LOOPS,
                        CharacterState.IDLE
                ),
                new CharacterResourceData(
                        characterFolder + "Block.png",
                        GameResource.Type.STILL,
                        new int[]{
                                CharacterState.BLOCKING,
                                CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                                CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Crouch.png",
                        GameResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING,
                                CharacterState.CROUCHING | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "HighKick.gif",
                        GameResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.HIGH_KICK,
                                CharacterState.HIGH_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.HIGH_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Jump.png",
                        GameResource.Type.STILL,
                        new int[]{
                                CharacterState.JUMPING,
                                CharacterState.JUMPING | CharacterState.MOVING_LEFT,
                                CharacterState.JUMPING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowKick.png",
                        GameResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.LOW_KICK,
                                CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Punch.gif",
                        GameResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.PUNCH,
                                CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                                CharacterState.PUNCH | CharacterState.MOVING_RIGHT,
                                CharacterState.PUNCH | CharacterState.JUMPING,
                                CharacterState.PUNCH | CharacterState.MOVING_LEFT | CharacterState.JUMPING,
                                CharacterState.PUNCH | CharacterState.MOVING_RIGHT | CharacterState.JUMPING,
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Walk.gif",
                        GameResource.Type.LOOPS,
                        new int[]{
                                CharacterState.MOVING_LEFT,
                                CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowBlock.png",
                        GameResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.BLOCKING,
                                CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowKick.png",
                        GameResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.LOW_KICK,
                                CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowPunch.png",
                        GameResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.PUNCH,
                                CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        "charSelectThumbs/" + name + "Thumb.png",
                        GameResource.Type.STILL,
                        CharacterState.THUMBNAIL
                )
        };

        for(CharacterResourceData characterResourceData : characterResourcesData) {
            for(int stateCode : characterResourceData.stateCodes) {
                try {
                    Rectangle bounds = character.getBounds();
                    setResource(stateCode, new GameResource(
                            characterResourceData.fileName,
                            characterResourceData.type,
                            bounds.width,
                            bounds.height
                    ));
                } catch(IOException e) {
                    //System.err.println(e);
                }
            }
        }
    }
}
