/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.characters.CharacterState;
import cs350project.characters.CharacterType;
import cs350project.characters.Coffman;
import cs350project.characters.Ganchev;
import cs350project.characters.LegoMan;
import cs350project.screens.MessageDialog;
import cs350project.characters.PlayerCharacter;
import cs350project.characters.Trump;
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
    private int playerID = 0;
    private PlayerCharacter player;
    private PlayerCharacter opponent;
    
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
    
    public PlayerCharacter getPlayer() {
        return player;
    }
    
    public PlayerCharacter getOpponent() {
        return opponent;
    }
    
    public void addMatchObjectManagerListener(MatchObjectManagerListener matchObjectManagerListener) {
        matchObjectManagerListeners.add(matchObjectManagerListener);
    }
    
    private void set(int id, MatchObject matchObject) {
        if(!matchObjects.containsKey(id)) {
            matchObjects.put(id, matchObject);
            //System.out.println("match object added: " + id);
            return;
        }
        throw new IllegalArgumentException("Attempted to overwrite match object. ID: " + id);
    }
    
    public void unsetAll() {
        matchObjects.clear();
    }
    
    public void setPlayer(PlayerCharacter player) {
        this.player = player;
        //setPlayerCharacter(playerID,player);
    }
    
    private void setPlayerCharacter(int id, PlayerCharacter playerCharacter) {
        playerCharacter.loadAllImageResources();
        playerCharacter.setState(CharacterState.IDLE);
        set(id,playerCharacter);
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        //System.out.println("match object manager received command " + serverCommand);
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
            //System.out.println("update match object with ID: " + id);
            MatchObject matchObject = matchObjects.get(id);
            if(matchObject != null) {
                matchObject.receiveData(dataInputStream);
                fireMatchObjectChanged();
            } else {
                MessageDialog.showErrorMessage(
                        "Update failed. Cannot find match object with ID " + id + ".", 
                        getClass()
                );
            }
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Update failed. I/O exception.", getClass());
        }
    }

    private void createMatchObject(DataInputStream dataInputStream) {
        String error = "Unable to create match object.";
        try {
            MatchObjectType type = MatchObjectType.parse(dataInputStream.readByte());
            int id = dataInputStream.readShort();
            //System.out.println("match object type received: " + type + " with ID: " + id);
            MatchObject matchObject = null;
            switch (type) {
                case PLATFORM:
                    matchObject = new Platform();
                    set(id, matchObject);
                    break;
                case PLAYER_CHARACTER:
                    CharacterType characterType = CharacterType.parse(dataInputStream.readByte());
                    //System.out.println("character type received: " + characterType);
                    switch(characterType) {
                        case GANCHEV:
                            opponent = new Ganchev();
                            break;
                        case COFFMAN:
                            opponent = new Coffman();
                            break;
                        case TRUMP:
                            opponent = new Trump();
                            break;
                        case LEGOMAN:
                            opponent = new LegoMan();
                            break;
                        default:
                            return;
                    }
                    opponent.setPlayerID(id);
                    setPlayerCharacter(id,opponent);
                    if(id == 0) {
                        playerID = 1;
                    }
                    player.setPlayerID(playerID);
                    setPlayerCharacter(playerID,player);
                    matchObject = opponent;
                    break;
            }
            if(matchObject != null) {
                matchObject.receiveData(dataInputStream);
            }
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
