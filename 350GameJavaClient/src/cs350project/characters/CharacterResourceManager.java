/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import cs350project.GameResource;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class CharacterResourceManager {
    
    private final HashMap<Integer,GameResource> resources;
    private final int defaultStateCode;
    private CharacterResourceDataList characterResourceDataList;
    
    public CharacterResourceManager(Class<? extends PlayerCharacter> c, int defaultStateCode) {
        this.defaultStateCode = defaultStateCode;
        resources = new HashMap<>();
        characterResourceDataList = new CharacterResourceDataList(c);
    }
    
    public void setResource(int stateCode, GameResource characterResource) {
        System.out.println(stateCode + " " + characterResource.getFileName());
        resources.put(stateCode, characterResource);
    }
    
    public GameResource getResource(int stateCode) {
        //System.out.println("get resource " + stateCode);
        GameResource characterResource = resources.get(stateCode);
        if(characterResource == null) {
            characterResource = resources.get(defaultStateCode);
        }
        return characterResource;
    }

    void loadGameResource(int stateCode, int width, int height) {
        CharacterResourceData characterResourceData = characterResourceDataList.get(stateCode);
        if(characterResourceData != null) {
            try {
                setResource(stateCode, new GameResource(
                        characterResourceData.getFileName(),
                        characterResourceData.getType(),
                        width,
                        height
                ));
            } catch (IOException e) {
                //System.err.println(e);
            }
        }
    }

    void loadAllGameResources(int width, int height) {
        for(CharacterResourceData characterResourceData : characterResourceDataList) {
            for(int stateCode : characterResourceData.getStateCodes()) {
                loadGameResource(stateCode,width,height);
            }
        }
    }
}
