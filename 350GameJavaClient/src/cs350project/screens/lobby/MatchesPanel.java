/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.lobby;

import cs350project.Settings;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
class MatchesPanel extends JPanel {
    
    private final LobbyPanel lobbyPanel;
    private final JList<String> matchesList;
    private final ArrayList<LobbyInputListener> inputListeners;
    
    MatchesPanel(LobbyPanel lobbyPanel, ArrayList<LobbyInputListener> inputListeners) {
        this.lobbyPanel = lobbyPanel;
        this.inputListeners = inputListeners;
        matchesList = new JList<>();
        matchesList.setFont(Settings.LIST_FONT);
        matchesList.setModel(new DefaultListModel<>());
    }
    
    void addMatch(String matchName) {
        //System.out.println("add match");
        DefaultListModel<String> defaultListModel = (DefaultListModel<String>)matchesList.getModel();
        defaultListModel.addElement(matchName);
    }
    
    void removeMatch(String matchName) {
        //System.out.println("remove match");
    }
    
    public void removeSelectedMatch() {
        int index = matchesList.getSelectedIndex();
        if(index > -1) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>)matchesList.getModel();
            defaultListModel.remove(index);
        }
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        JPanel matchesPanel = lobbyPanel.buildJPanel(this,"MATCHES",matchesList);
        GridBagConstraints gbc = lobbyPanel.gbc;
        
        JButton createButton = lobbyPanel.buildJButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // delete the next 4 lines after testing is completed
                /*
                addMatch("match1");
                for(LobbyInputListener lobbyInputListener : inputListeners) {
                    lobbyInputListener.createMatch("match1");
                }*/
                
                // uncomment this block after testing is completed
                while(true) {
                    String matchName = JOptionPane.showInputDialog(
                            matchesPanel, 
                            "Match Name", 
                            "Create Match", 
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if(matchName == null) {
                        break;
                    } else if(matchName.isBlank()) {
                        JOptionPane.showMessageDialog(matchesPanel, "Match name cannot be blank.");
                    } else {
                        addMatch(matchName);
                        for(LobbyInputListener lobbyInputListener : inputListeners) {
                            lobbyInputListener.createMatch(matchName);
                        }
                        break;
                    }
                }
            }
        });
        gbc.insets = Settings.INSETS_MENU_NO_RIGHT;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        matchesPanel.add(createButton,gbc);
        
        JButton deleteButton = lobbyPanel.buildJButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*for(LobbyInputListener lobbyInputListener : inputListeners) {
                    lobbyInputListener.deleteMatch();
                }*/
                removeSelectedMatch();
            }
        });
        gbc.gridx = 1;
        matchesPanel.add(deleteButton,gbc);
        
        JButton joinButton = lobbyPanel.buildJButton("Join");
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(LobbyInputListener lobbyInputListener : inputListeners) {
                    lobbyInputListener.joinMatch(matchesList.getSelectedValue());
                }
            }
        });
        gbc.insets = Settings.INSETS_MENU_ALL;
        gbc.gridx = 2;
        matchesPanel.add(joinButton,gbc);
    }
}
