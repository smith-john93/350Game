/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class CharacterResourceManager {
    
    private final HashMap<Integer,CharacterResource> resources;
    private final int defaultStateCode;
    
    public CharacterResourceManager(int defaultStateCode) {
        this.defaultStateCode = defaultStateCode;
        resources = new HashMap<>();
    }
    
    public void setResource(int stateCode, CharacterResource characterResource) {
        System.out.println(stateCode + " " + characterResource.getFileName());
        resources.put(stateCode, characterResource);
    }
    
    public CharacterResource getResource(int stateCode) {
        //System.out.println("get resource " + stateCode);
        CharacterResource characterResource = resources.get(stateCode);
        if(characterResource == null) {
            characterResource = resources.get(defaultStateCode);
        }
        return characterResource;
    }

    public void loadResources(Class<? extends PlayerCharacter> c) {
        String name = c.getSimpleName();
        String characterFolder = "characters/" + name + "/";
        CharacterResource[] characterResources = {
                new CharacterResource(
                        characterFolder + "Idle.gif",
                        CharacterResource.Type.LOOPS,
                        CharacterState.IDLE
                ),
                new CharacterResource(
                        characterFolder + "Block.png",
                        CharacterResource.Type.STILL,
                        new int[]{
                                CharacterState.BLOCKING,
                                CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                                CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "Crouch.png",
                        CharacterResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING,
                                CharacterState.CROUCHING | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "HighKick.gif",
                        CharacterResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.HIGH_KICK,
                                CharacterState.HIGH_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.HIGH_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "Jump.png",
                        CharacterResource.Type.STILL,
                        new int[]{
                                CharacterState.JUMPING,
                                CharacterState.JUMPING | CharacterState.MOVING_LEFT,
                                CharacterState.JUMPING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "LowKick.png",
                        CharacterResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.LOW_KICK,
                                CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "Punch.gif",
                        CharacterResource.Type.PLAYS_ONCE,
                        new int[]{
                                CharacterState.PUNCH,
                                CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                                CharacterState.PUNCH | CharacterState.MOVING_RIGHT,
                                CharacterState.PUNCH | CharacterState.JUMPING,
                                CharacterState.PUNCH | CharacterState.MOVING_LEFT | CharacterState.JUMPING,
                                CharacterState.PUNCH | CharacterState.MOVING_RIGHT | CharacterState.JUMPING,
                        }
                ),
                new CharacterResource(
                        characterFolder + "Walk.gif",
                        CharacterResource.Type.LOOPS,
                        new int[]{
                                CharacterState.MOVING_LEFT,
                                CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "LowBlock.png",
                        CharacterResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.BLOCKING,
                                CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "LowKick.png",
                        CharacterResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.LOW_KICK,
                                CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        characterFolder + "LowPunch.png",
                        CharacterResource.Type.STILL,
                        new int[]{
                                CharacterState.CROUCHING | CharacterState.PUNCH,
                                CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                                CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_RIGHT
                        }
                ),
                new CharacterResource(
                        "charSelectThumbs/" + name + "Thumb.png",
                        CharacterResource.Type.STILL,
                        CharacterState.THUMBNAIL
                )
        };

        for(CharacterResource characterResource : characterResources) {
            for(int stateCode : characterResource.getStateCodes()) {
                try {
                    characterResource.loadResource();
                    setResource(stateCode, characterResource);
                } catch(IOException e) {
                    //System.err.println(e);
                }
            }
        }
    }
}
