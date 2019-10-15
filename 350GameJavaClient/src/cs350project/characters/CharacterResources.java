/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author Mark Masone
 */
public class CharacterResources {
    
    private final HashMap<CharacterState,String> resources;
    private final CharacterState defaultCharacterState;
    
    public CharacterResources(CharacterState defaultCharacterState, String fileName) {
        this.defaultCharacterState = defaultCharacterState;
        resources = new HashMap<>();
        resources.put(defaultCharacterState, fileName);
    }
    
    public void setResource(CharacterState characterState, String fileName) {
        resources.put(characterState, fileName);
    }
    
    public Image getStateImage(CharacterState characterState) {
        String fileName = resources.get(characterState);
        if(fileName == null) {
            fileName = resources.get(defaultCharacterState);
        }
        URL url = PlayerCharacter.class.getResource("/resources/" + fileName);
        ImageIcon characterImageIcon = new ImageIcon(url);
        
        // This could be useful later.
        /*
        ImageInputStream iis = ImageIO.createImageInputStream(url.openStream());
        java.util.Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
        while(it.hasNext()) {
            ImageReader ir = it.next();
            if(ir.getFormatName().equals("gif")) {
                ir.setInput(iis);
                int i = ir.getNumImages(true); // using false (no search) may be more efficient?
                character = ir.read(i - 1);
                return;
            }
        }*/
        
        return characterImageIcon.getImage();
    }
}
