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
public class LegoMan extends PlayerCharacter {
    public LegoMan() {
        super(CharacterState.IDLE,"characters/Punch-Hit.gif");
        CharacterResources resources = getCharacterResources();
        resources.setResource(CharacterState.THUMBNAIL, "charSelectThumbs/legoManThumb.png");
    }

    @Override
    public CharacterClass getCharacterClass() {
        return CharacterClass.NORMAL;
    }
}
