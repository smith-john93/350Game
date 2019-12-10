/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.lobby;

import cs350project.menu.BackButtonPanel;
import cs350project.Settings;
import cs350project.menu.MenuItemFactory;
import cs350project.screens.Panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mark Masone
 */
public class LobbyPanel extends Panel<LobbyInputListener> {

    final GridBagConstraints _gbc;
    private final EmptyBorder lobbyBorder;
    private final int buttonHeight;
    private final MatchesPanel matchesPanel;
    
    private final String settingsCommand = "Settings";
    private final ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(LobbyInputListener lobbyInputListener : inputListeners) {
                switch(e.getActionCommand()) {
                    case settingsCommand:
                        lobbyInputListener.settings();
                        break;
                    case BackButtonPanel.ACTION_COMMAND:
                        lobbyInputListener.back();
                        break;
                }
            }
        }
    };
    
    public LobbyPanel() {
        
        lobbyBorder = new EmptyBorder(Settings.INSETS_MENU_ALL);
        
        _gbc = new GridBagConstraints();
        _gbc.fill = GridBagConstraints.BOTH;
        _gbc.anchor = GridBagConstraints.NORTH;
        _gbc.weightx = 0.5;
        
        buttonHeight = 100;
        
        matchesPanel = new MatchesPanel(this,inputListeners);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        setLayout(new GridBagLayout());
        setBorder(lobbyBorder);
        setBackground(Settings.TRANSPARENT);
        
        JButton settingsButton = MenuItemFactory.createSmallButton(settingsCommand);
        settingsButton.addActionListener(buttonListener);
        settingsButton.setMargin(Settings.INSETS_MENU_ALL);
        
        JPanel settingsButtonPanel = new JPanel();
        settingsButtonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,0,0));
        settingsButtonPanel.setBorder(new EmptyBorder(Settings.INSETS_MENU_TOP));
        settingsButtonPanel.setBackground(Settings.TRANSPARENT);
        settingsButtonPanel.add(settingsButton);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        gbc.gridy = 0;
        
        gbc.insets = Settings.INSETS_MENU_RIGHT;
        gbc.gridx = 0;
        add(matchesPanel,gbc);
        
        gbc.insets = Settings.INSETS_NONE;
        
        gbc.gridx = 1;
        add(new FriendsPanel(this,inputListeners),gbc);
        
        gbc.weighty = 0;
        gbc.gridy = 1;
        
        gbc.gridx = 0;
        add(new BackButtonPanel(buttonListener),gbc);
        
        gbc.gridx = 1;
        add(settingsButtonPanel,gbc);
    }
    
    public void addMatch(String matchName) {
        matchesPanel.addMatch(matchName);
    }
    
    public void removeMatch(String matchName) {
        matchesPanel.removeMatch(matchName);
    }
    
    JButton buildJButton(String label) {
        JButton jButton = new JButton(label);
        jButton.setPreferredSize(new Dimension(0,buttonHeight));
        jButton.setFont(Settings.BUTTON_FONT);
        return jButton;
    }
    
    JPanel buildJPanel(JPanel jPanel, String label, JList<String> jList) {
        
        jPanel.setLayout(new GridBagLayout());
        jPanel.setBackground(Color.black);
        jPanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        //int leftBorder = lobbyBorder.getBorderInsets().left;
        //int spacing = leftBorder + leftBorder / 2;
        //jPanel.setPreferredSize(new Dimension(getWidth() / 2 - spacing, getHeight()));
        
        JLabel jLabel = MenuItemFactory.createHeadingLabel(label);
        _gbc.insets = Settings.INSETS_MENU_NO_BOTTOM;
        _gbc.gridx = 0;
        _gbc.gridy = 0;
        _gbc.gridwidth = 3;
        _gbc.weighty = 0;
        jPanel.add(jLabel,_gbc);
        
        JScrollPane jScrollPane = new JScrollPane(jList);
        _gbc.gridx = 0;
        _gbc.gridy = 1;
        _gbc.weighty = 1;
        jPanel.add(jScrollPane,_gbc);
        
        return jPanel;
    }
}
