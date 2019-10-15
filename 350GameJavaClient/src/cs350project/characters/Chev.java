package cs350project.characters;

public class Chev extends PlayerCharacter {
    public Chev() {
        super(CharacterState.IDLE,"characters/Ganchev.png");
        CharacterResources resources = getCharacterResources();
        resources.setResource(CharacterState.THUMBNAIL, "charSelectThumbs/ganchevThumb.png");
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.MAGE;
    }
}
