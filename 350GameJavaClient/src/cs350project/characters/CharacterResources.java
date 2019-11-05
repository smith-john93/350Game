/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author Mark Masone
 */
public class CharacterResources {
    
    private final HashMap<Integer,CharacterResource> resources;
    private CharacterResource thumbnail;
    
    public CharacterResources(int defaultStateCode, CharacterResource cr) {
        resources = new HashMap<>();
        resources.put(defaultStateCode, cr);
    }
    
    public void setResource(int stateCode, CharacterResource cr) {
        System.out.println(stateCode + " " + cr.getFileName());
        resources.put(stateCode, cr);
    }
    
    public void setResource(CharacterResource cr, int[] stateCodes) {
        for(int stateCode : stateCodes) {
            setResource(stateCode,cr);
        }
    }
    
    public CharacterResource getResource(int stateCode) {
        System.out.println("get resource " + stateCode);
        return resources.get(stateCode);
    }
}
