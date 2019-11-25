package cs350project.characters;

import cs350project.GameResource;

public class CharacterResourceData {

    private final String fileName;
    private final GameResource.Type type;
    private final int[] stateCodes;

    public CharacterResourceData(String fileName, GameResource.Type type, int stateCode) {
        this(fileName, type, new int[]{stateCode});
    }

    public CharacterResourceData(String fileName, GameResource.Type type, int[] stateCodes) {
        this.fileName = fileName;
        this.type = type;
        this.stateCodes = stateCodes;
    }

    public String getFileName() {
        return fileName;
    }

    public GameResource.Type getType() {
        return type;
    }

    public int[] getStateCodes() {
        return stateCodes;
    }
}
