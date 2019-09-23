package cs350project.characters;

public class Chev extends PlayerCharacter {
    public Chev() {
        super(
                new CharacterResources(
                        "Ganchev.png",
                        "Punch-Hit.gif",
                        "Punch-Hit.gif",
                        "Punch-Hit.gif"
                ),
                CharacterState.IDLE
        );
    }
}
