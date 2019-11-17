/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

/**
 *
 * @author Mark Masone
 */
public class Coffman extends PlayerCharacter {
    public Coffman(short objectID) {
        super(objectID, CharacterState.IDLE);
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.MAGE;
    }
}
