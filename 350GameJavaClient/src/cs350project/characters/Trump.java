package cs350project.characters;

public class Trump extends PlayerCharacter {
    public Trump(int objectID) {
        super(objectID);
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NINJA;
    }
    
    @Override
    public CharacterType getCharacterType() {
        return CharacterType.TRUMP;
    }
}
