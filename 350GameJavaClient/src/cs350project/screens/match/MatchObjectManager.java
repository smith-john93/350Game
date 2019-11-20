/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.screens.MessageDialog;
import cs350project.characters.PlayerCharacter;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

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
    
    public void clear() {
        matchObjects.clear();
    }
    
    public void setPlayerCharacters(PlayerCharacter player, PlayerCharacter opponent) {
        set(0,player);
        set(1,opponent);
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        System.out.println("match object manager received command " + serverCommand);
        switch(serverCommand) {
            case CREATE_MATCH_OBJECT:
                createMatchObject(dataInputStream);
                break;
            case UPDATE_MATCH:
                updateMatch(dataInputStream);
        }
    }

    private void updateMatch(DataInputStream dataInputStream) {
        try {
            int id = dataInputStream.readShort();
            //System.out.println("receive data: " + id);
            MatchObject matchObject = matchObjects.get(id);
            matchObject.receiveData(dataInputStream);
            fireMatchObjectChanged();
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Unable to update match object.", getClass());
        }
    }

    private void createMatchObject(DataInputStream dataInputStream) {
        String error = "Unable to create match object.";
        try {
            MatchObjectType type = MatchObjectType.parse(dataInputStream.readByte());
            int id = dataInputStream.readShort();
            MatchObject matchObject = null;
            switch (type) {
                case PLATFORM:
                    System.out.println("got match object type platform");
                    matchObject = new Platform();
                    matchObject.receiveData(dataInputStream);
            }
            set(id, matchObject);
        } catch(IOException e) {
            MessageDialog.showErrorMessage(error, getClass());
        } catch(NoSuchElementException e) {
            MessageDialog.showErrorMessage(error + " " + e.getMessage(), getClass());
        }
    }

    private void fireMatchObjectChanged() {
        for(MatchObjectManagerListener matchObjectManagerListener : matchObjectManagerListeners) {
            matchObjectManagerListener.matchObjectChanged();
        }
    }
}
