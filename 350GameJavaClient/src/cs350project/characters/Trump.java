package cs350project.characters;

public class Trump extends PlayerCharacter {
    public Trump(short objectID) {
        super(objectID, CharacterState.IDLE);
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NINJA;
    }
}
