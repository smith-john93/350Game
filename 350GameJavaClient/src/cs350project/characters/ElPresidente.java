package cs350project.characters;

import java.io.IOException;

public class ElPresidente extends PlayerCharacter {
    public ElPresidente(short objectID) throws IOException {
        super(objectID, CharacterState.IDLE, new CharacterResource(
                "characters/elpres.jpg",
                CharacterResource.Type.STILL
        ));
        resources.setResource(CharacterState.THUMBNAIL, new CharacterResource(
                "charSelectThumbs/trumpThumb.png", 
                CharacterResource.Type.STILL
        ));
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NINJA;
    }
}
