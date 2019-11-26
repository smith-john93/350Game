/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import cs350project.ImageResource;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class CharacterResourceManager {
    
    private final HashMap<Integer,ImageResource> characterResources;
    private final CharacterResourceData[] characterResourcesData;
    
    public CharacterResourceManager(Class<? extends PlayerCharacter> c) {
        characterResources = new HashMap<>();
        String characterName = c.getSimpleName();
        String characterFolder = "characters/" + characterName + "/";
        characterResourcesData = new CharacterResourceData[]{
                new CharacterResourceData(
                        characterFolder + "Idle.gif",
                        ImageResource.Type.LOOPS,
                        CharacterState.IDLE
                ),
                new CharacterResourceData(
                        characterFolder + "Block.png",
                        ImageResource.Type.STILL,
                        new int[]{
                                CharacterState.BLOCKING,
                                CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                                CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Crouch.png",
                        ImageResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING,
                                CharacterState.CROUCHING | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "HighKick.gif",
                        ImageResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.HIGH_KICK,
                                CharacterState.HIGH_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.HIGH_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Jump.png",
                        ImageResource.Type.STILL,
                        new int[]{
                                CharacterState.JUMPING,
                                CharacterState.JUMPING | CharacterState.MOVING_LEFT,
                                CharacterState.JUMPING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowKick.png",
                        ImageResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.LOW_KICK,
                                CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "Punch.gif",
                        ImageResource.Type.PLAYS_ONCE,
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
                        ImageResource.Type.LOOPS,
                        new int[]{
                                CharacterState.MOVING_LEFT,
                                CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowBlock.png",
                        ImageResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.BLOCKING,
                                CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowKick.png",
                        ImageResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.LOW_KICK,
                                CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        characterFolder + "LowPunch.png",
                        ImageResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.PUNCH,
                                CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResourceData(
                        "charSelectThumbs/" + characterName + "Thumb.png",
                        ImageResource.Type.STILL,
                        CharacterState.THUMBNAIL
                )
        };
    }
    
    public void setImageResource(int stateCode, ImageResource characterResource) {
        characterResources.put(stateCode, characterResource);
    }
    
    public ImageResource getImageResource(int stateCode) {
        return characterResources.get(stateCode);
    }

    void loadImageResource(int stateCode, int width, int height) {
        for(CharacterResourceData characterResourceData : characterResourcesData) {
            for(int sc : characterResourceData.getStateCodes()) {
                if(sc == stateCode) {
                    loadImageResource(characterResourceData,width,height);
                }
            }
        }
    }
    
    private void loadImageResource(CharacterResourceData characterResourceData, int width, int height) {
        try {
            ImageResource imageResource = new ImageResource(
                    characterResourceData.getFileName(),
                    characterResourceData.getType(),
                    width,
                    height
            );
            for(int stateCode : characterResourceData.getStateCodes()) {
                setImageResource(stateCode,imageResource);
            }
        } catch (IOException e) {
            //System.err.println(e);
        }
    }

    void loadAllImageResources(int width, int height) {
        for(CharacterResourceData characterResourceData : characterResourcesData) {
            loadImageResource(characterResourceData,width,height);
        }
    }
    
    private class CharacterResourceData {

        private final String fileName;
        private final ImageResource.Type type;
        private final int[] stateCodes;

        private CharacterResourceData(String fileName, ImageResource.Type type, int stateCode) {
            this(fileName, type, new int[]{stateCode});
        }

        private CharacterResourceData(String fileName, ImageResource.Type type, int[] stateCodes) {
            this.fileName = fileName;
            this.type = type;
            this.stateCodes = stateCodes;
        }

        private String getFileName() {
            return fileName;
        }

        private ImageResource.Type getType() {
            return type;
        }

        private int[] getStateCodes() {
            return stateCodes;
        }
    }   
}
