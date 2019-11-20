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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

    final GridBagConstraints gbc;
    private final EmptyBorder lobbyBorder;
    private final int buttonHeight;
    
    public LobbyPanel() {
        
        lobbyBorder = new EmptyBorder(20,20,20,20);
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 0.5;
        
        buttonHeight = 100;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        setBorder(lobbyBorder);
        setBackground(Settings.TRANSPARENT);
        
        add(new MatchesPanel(this,inputListeners),BorderLayout.LINE_START);
        add(new FriendsPanel(this,inputListeners),BorderLayout.LINE_END);
        add(new BackButtonPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(LobbyInputListener lobbyInputListener : inputListeners) {
                    lobbyInputListener.back();
                }
            }
        }),BorderLayout.PAGE_END);
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
        int leftBorder = lobbyBorder.getBorderInsets().left;
        int spacing = leftBorder + leftBorder / 2;
        jPanel.setPreferredSize(new Dimension(getWidth() / 2 - spacing, getHeight()));
        
        JLabel jLabel = MenuItemFactory.createLabel(label);
        gbc.insets = Settings.INSETS_MENU_NO_BOTTOM;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 0;
        jPanel.add(jLabel,gbc);
        
        JScrollPane jScrollPane = new JScrollPane(jList);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        jPanel.add(jScrollPane,gbc);
        
        return jPanel;
    }
}
