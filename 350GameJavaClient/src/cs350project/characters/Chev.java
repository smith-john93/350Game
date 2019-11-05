package cs350project.characters;

import java.io.IOException;

public class Chev extends PlayerCharacter {
    public Chev(short objectID) throws IOException {
        super(objectID, CharacterState.IDLE, new CharacterResource(
                "characters/Ganchev.png",
                CharacterResource.Type.STILL
        ));
        resources.setResource(CharacterState.THUMBNAIL, new CharacterResource(
                "charSelectThumbs/ganchevThumb.png",
                CharacterResource.Type.STILL
        ));
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.MAGE;
    }
}
