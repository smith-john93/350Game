/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.MessageDialog;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class MatchObjectManager implements IncomingCommandListener {
    
    private static MatchObjectManager matchObjectManager;
    private final HashMap<Integer,MatchObject> matchObjects;
    private final ArrayList<MatchObjectManagerListener> matchObjectManagerListeners;
    
    private MatchObjectManager() {
        matchObjects = new HashMap<>();
        matchObjectManagerListeners = new ArrayList<>();
    }
    
    public static MatchObjectManager getInstance() {
        if(matchObjectManager == null) {
            matchObjectManager = new MatchObjectManager();
        }
        return matchObjectManager;
    }
    
    public Collection<MatchObject> getMatchObjects() {
        return matchObjects.values();
    }
    
    public void addMatchObjectManagerListener(MatchObjectManagerListener matchObjectManagerListener) {
        matchObjectManagerListeners.add(matchObjectManagerListener);
    }
    
    private void set(int id, MatchObject matchObject) {
        if(!matchObjects.containsKey(id)) {
            matchObjects.put(id, matchObject);
            System.out.println("match object added: " + id);
            return;
        }
        throw new IllegalArgumentException("Attempted to overwrite match object. ID: " + id);
    }
    
    public void setPlayerCharacters(PlayerCharacter player, PlayerCharacter opponent) {
        set(0,player);
        set(1,opponent);
    }
    
    public void receiveData(int id, DataInputStream dataInputStream) throws IOException {
        System.out.println("receive data: " + id);
        MatchObject matchObject = matchObjects.get(id);
        matchObject.receiveData(dataInputStream);
        fireMatchObjectChanged();
    }
    
    private void fireMatchObjectChanged() {
        for(MatchObjectManagerListener matchObjectManagerListener : matchObjectManagerListeners) {
            matchObjectManagerListener.matchObjectChanged();
        }
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        String error = "";
        int id;
        try {
            switch(serverCommand) {
                case CREATE_MATCH_OBJECT:
                    error = "Unable to create match objects.";

                    byte type = dataInputStream.readByte();
                    id = dataInputStream.readShort();
                    MatchObject matchObject;
                    switch(type) {
                        case MatchObjectType.PLATFORM:
                            matchObject = new Platform();
                            matchObject.receiveData(dataInputStream);
                            break;
                        default:
                            throw new IllegalArgumentException(error + " Invalid match object type.");
                    }
                    set(id, matchObject);
                    break;
                case UPDATE_MATCH:
                    id = dataInputStream.readShort();
                    receiveData(id,dataInputStream);
            }
        } catch(IOException e) {
            MessageDialog.showErrorMessage(error, getClass());
        } catch(IllegalArgumentException e) {
            MessageDialog.showErrorMessage(e.getMessage(), getClass());
        }
    }
}
