/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.ClientCommand;
import cs350project.Communication;
import cs350project.ServerCommand;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.MatchLoadingPanel;
import java.io.IOException;

/**
 *
 * @author Mark Masone
 */
public class MatchLoadingScreen extends Screen {

    private final MatchLoadingPanel matchLoadingPanel;
    private final Communication comm;
    private final Thread commandListener;
    
    public MatchLoadingScreen() {
        matchLoadingPanel = new MatchLoadingPanel();
        comm = new Communication();
        comm.connect();
        commandListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        byte command = comm.receiveCommand();
                        System.out.println("command received: " + command);
                        if(command == ServerCommand.CREATE_MATCH_OBJECT) {

                        } else if(command == ServerCommand.START_MATCH) {

                        }
                    }
                } catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
    
    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void showPanel() {
        commandListener.start();
        addPanel(matchLoadingPanel);
        comm.sendCommand(ClientCommand.CREATE_MATCH);
        System.out.println("command sent: create match");
    }
    
}
