package cs350project.characters;

import cs350project.GameResource;

import java.util.Iterator;

public class CharacterResourceDataList implements Iterable<CharacterResourceData> {

    private final CharacterResourceData[] characterResourcesDataList;

    public CharacterResourceDataList(Class<? extends PlayerCharacter> c) {
        String name = c.getSimpleName();
        String characterFolder = "characters/" + name + "/";
        characterResourcesDataList = new CharacterResourceData[]{
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
    }

    public CharacterResourceData get(int stateCode) {
        for(CharacterResourceData characterResourceData : characterResourcesDataList) {
            for(int sc : characterResourceData.getStateCodes()) {
                if(stateCode == sc) {
                    return characterResourceData;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<CharacterResourceData> iterator() {
        return new CharacterResourceDataIterator();
    }

    public class CharacterResourceDataIterator implements Iterator<CharacterResourceData> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < characterResourcesDataList.length;
        }

        @Override
        public CharacterResourceData next() {
            return characterResourcesDataList[index++];
        }
    }
}
