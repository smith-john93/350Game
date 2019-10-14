/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Mark Masone
 */
public class CharacterResources {
    
    private final String idleStateImageFileName;
    private final String jumpStateImageFileName;
    private final String punchStateImageFileName;
    private final String kickStateImageFileName;
    
    public CharacterResources(
            String idleStateImageFileName,
            String jumpStateImageFileName,
            String punchStateImageFileName,
            String kickStateImageFileName
    ) {
        this.idleStateImageFileName = idleStateImageFileName;
        this.jumpStateImageFileName = jumpStateImageFileName;
        this.punchStateImageFileName = punchStateImageFileName;
        this.kickStateImageFileName = kickStateImageFileName;
    }
    
    private String getStateImageFileName(CharacterState state) {
        switch(state) {
            case IDLE: return idleStateImageFileName;
            case JUMP: return jumpStateImageFileName;
            case PUNCH: return punchStateImageFileName;
            case KICK:  return kickStateImageFileName;
            default: return null;
        }
    }
    
    public Image getStateImage(CharacterState state) {
        URL url = PlayerCharacter.class.getResource("/resources/characters/" + getStateImageFileName(state));
        ImageIcon character = new ImageIcon(url);
        
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
        
        return character.getImage();
    }
}
