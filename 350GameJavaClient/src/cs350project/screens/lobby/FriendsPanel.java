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
class FriendsPanel extends JPanel {
    
    private final LobbyPanel lobbyPanel;
    private final JList<String> friendsList;
    private final ArrayList<LobbyInputListener> inputListeners;
    
    FriendsPanel(LobbyPanel lobbyPanel, ArrayList<LobbyInputListener> inputListeners) {
        this.lobbyPanel = lobbyPanel;
        this.inputListeners = inputListeners;
        friendsList = new JList<>();
        friendsList.setFont(Settings.LIST_FONT);
        friendsList.setModel(new DefaultListModel<>());
    }
    
    public void addFriend(String name) {
        DefaultListModel<String> defaultListModel = (DefaultListModel<String>)friendsList.getModel();
        defaultListModel.addElement(name);
    }
    
    public void removeSelectedFriend() {
        int index = friendsList.getSelectedIndex();
        if(index > -1) {
            DefaultListModel<String> defaultListModel = (DefaultListModel<String>)friendsList.getModel();
            defaultListModel.remove(index);
        }
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        lobbyPanel.buildJPanel(this,"FRIENDS",friendsList);
        GridBagConstraints gbc = lobbyPanel.gbc;
              
        JButton addButton = lobbyPanel.buildJButton("Add");
        JPanel friendsPanel = this;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while(true) {
                    String friendName = JOptionPane.showInputDialog(
                            friendsPanel, 
                            "Friend's Name", 
                            "Add Friend", 
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if(friendName == null) {
                        break;
                    } else if(friendName.isBlank()) {
                        JOptionPane.showMessageDialog(friendsPanel, "Friend's name cannot be blank.");
                    } else {
                        /*for(LobbyInputListener lobbyInputListener : inputListeners) {
                            lobbyInputListener.addFriend();
                        }*/
                        addFriend(friendName);
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
        friendsPanel.add(addButton,gbc);
        
        JButton removeButton = lobbyPanel.buildJButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*for(LobbyInputListener lobbyInputListener : inputListeners) {
                    lobbyInputListener.removeFriend();
                }*/
                removeSelectedFriend();
            }
        });
        gbc.insets = Settings.INSETS_MENU_ALL;
        gbc.gridx = 1;
        friendsPanel.add(removeButton,gbc);
    }
}
