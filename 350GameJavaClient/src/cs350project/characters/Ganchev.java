package cs350project.characters;

public class Ganchev extends PlayerCharacter {
    public Ganchev(int objectID) {
        super(objectID);
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
