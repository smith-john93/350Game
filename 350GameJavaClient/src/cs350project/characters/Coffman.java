/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class Coffman extends PlayerCharacter {
    public Coffman(short objectID) throws IOException {
        super(objectID, CharacterState.IDLE, new CharacterResource(
                "characters/Coffman.png", 
                CharacterResource.Type.LOOPS
        ));
        resources.setResource(CharacterState.THUMBNAIL, new CharacterResource(
                "charSelectThumbs/coffmanThumb.png",
                CharacterResource.Type.STILL
        ));
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.MAGE;
    }
}
