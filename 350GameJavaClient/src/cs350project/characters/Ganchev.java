package cs350project.characters;

public class Ganchev extends PlayerCharacter {
    public Ganchev(short objectID) {
        super(objectID, CharacterState.IDLE);
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.MAGE;
    }

    @Override
    public CharacterType getCharacterType() {
        return CharacterType.GANCHEV;
    }
}
