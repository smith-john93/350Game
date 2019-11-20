/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.selection;

import cs350project.Settings;
import cs350project.characters.CharacterState;
import cs350project.characters.*;
import cs350project.screens.Panel;
import java.awt.Rectangle;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class SelectionPanel extends Panel {

    private final PlayerCharacter[] characters;
    private final SelectionOverlay selectionOverlay;

    public SelectionPanel(short objectID) throws IOException {
        characters = new PlayerCharacter[]{
                new Ganchev(objectID),
                new Coffman(objectID),
                new Trump(objectID),
                new LegoMan(objectID)
        };
        
        Rectangle[] characterBorders = new Rectangle[characters.length];
        for(int i = 0; i < characters.length; i++) {
            int x = 300 + (i * 140);
            int y = 300;
            int w = 100;
            int h = 100;
            PlayerCharacter character = characters[i];
            character.setBounds(x, y, w, h);
            character.setState(CharacterState.THUMBNAIL);
            characterBorders[i] = character.getBounds();
        }
        
        selectionOverlay = new SelectionOverlay(characterBorders);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        setLayout(null);
        setBackground(Settings.TRANSPARENT);
        
        for(PlayerCharacter character : characters) {
            add(character);
            setComponentZOrder(character,0);
        }
        
        add(selectionOverlay);
        setComponentZOrder(selectionOverlay,1);
    }
    
    public PlayerCharacter getPlayer1Selection() {
        return characters[selectionOverlay.getSelectedCharacter()];
    }
    
    public PlayerCharacter getPlayer2Selection() {
        return null;
    }
    
    public SelectionOverlay getSelectionOverlay() {
        return selectionOverlay;
    }
}
