package cs350project.characters;

public class ElPresidente extends PlayerCharacter {
    public ElPresidente() {
        super(CharacterState.IDLE,"characters/elpres.jpg");
        CharacterResources resources = getCharacterResources();
        resources.setResource(CharacterState.THUMBNAIL, "charSelectThumbs/trumpThumb.png");
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NINJA;
    }
}
