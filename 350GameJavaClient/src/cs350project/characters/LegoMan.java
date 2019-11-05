/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class LegoMan extends PlayerCharacter {
    
    public LegoMan(short objectID) throws IOException {
        super(objectID, CharacterState.IDLE, new CharacterResource(
                "characters/Lego/Idle.gif",
                CharacterResource.Type.LOOPS
        ));
        
        CharacterResource[] crouchingResources = {
            new CharacterResource(
                    "characters/Lego/Crouch.png",
                    CharacterResource.Type.STILL
            ),
            new CharacterResource(
                    "characters/Lego/LowKick.png",
                    CharacterResource.Type.STILL
            ),
            new CharacterResource(
                    "characters/Lego/LowPunch.png",
                    CharacterResource.Type.STILL
            ),
            new CharacterResource(
                    "characters/Lego/LowBlock.png",
                    CharacterResource.Type.STILL
            )
        };
        
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/Block.png",
                    CharacterResource.Type.STILL
                ),
                new int[]{
                    CharacterState.BLOCKING,
                    CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                    CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/Crouch.png",
                    CharacterResource.Type.STILL
                ),
                new int[]{
                    CharacterState.CROUCHING,
                    CharacterState.CROUCHING | CharacterState.MOVING_LEFT,
                    CharacterState.CROUCHING | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/HighKick.gif",
                    CharacterResource.Type.PLAYS_ONCE
                ),
                new int[]{
                    CharacterState.HIGH_KICK,
                    CharacterState.HIGH_KICK | CharacterState.MOVING_LEFT,
                    CharacterState.HIGH_KICK | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/Jump.png",
                    CharacterResource.Type.STILL
                ),
                new int[]{
                    CharacterState.JUMPING,
                    CharacterState.JUMPING | CharacterState.MOVING_LEFT,
                    CharacterState.JUMPING | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/LowKick.png",
                    CharacterResource.Type.PLAYS_ONCE
                ),
                new int[]{
                    CharacterState.LOW_KICK,
                    CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                    CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/Punch.gif",
                    CharacterResource.Type.PLAYS_ONCE
                ),
                new int[]{
                    CharacterState.PUNCH,
                    CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                    CharacterState.PUNCH | CharacterState.MOVING_RIGHT,
                    CharacterState.PUNCH | CharacterState.JUMPING,
                    CharacterState.PUNCH | CharacterState.MOVING_LEFT | CharacterState.JUMPING,
                    CharacterState.PUNCH | CharacterState.MOVING_RIGHT | CharacterState.JUMPING,
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/Walk.gif",
                    CharacterResource.Type.LOOPS
                ),
                new int[]{
                    CharacterState.MOVING_LEFT,
                    CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/LowBlock.png",
                    CharacterResource.Type.STILL
                ),
                new int[]{
                    CharacterState.CROUCHING | CharacterState.BLOCKING,
                    CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_LEFT,
                    CharacterState.CROUCHING | CharacterState.BLOCKING | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/LowKick.png",
                    CharacterResource.Type.STILL
                ),
                new int[]{
                    CharacterState.CROUCHING | CharacterState.LOW_KICK,
                    CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_LEFT,
                    CharacterState.CROUCHING | CharacterState.LOW_KICK | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(
                new CharacterResource(
                    "characters/Lego/LowPunch.png",
                    CharacterResource.Type.STILL
                ),
                new int[]{
                    CharacterState.CROUCHING | CharacterState.PUNCH,
                    CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_LEFT,
                    CharacterState.CROUCHING | CharacterState.PUNCH | CharacterState.MOVING_RIGHT
                }
        );
        resources.setResource(CharacterState.THUMBNAIL, new CharacterResource(
                "charSelectThumbs/legoManThumb.png",
                CharacterResource.Type.STILL
        ));
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NORMAL;
    }
}
