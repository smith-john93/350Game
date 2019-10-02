package cs350project.characters;

public class ElPresidente extends PlayerCharacter {
    public ElPresidente() {
        super(
                new CharacterResources(
                        "elpres.jpg",
                        "Punch-Hit.gif",
                        "Punch-Hit.gif",
                        "Punch-Hit.gif"
                ),
                CharacterState.IDLE
        );
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NINJA;
    }
}
