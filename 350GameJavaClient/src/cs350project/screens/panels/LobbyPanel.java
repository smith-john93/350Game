/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;

import cs350project.menu.Menu;
import cs350project.menu.MenuActionListener;
import cs350project.screens.listeners.LobbyInputListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;

/**
 *
 * @author Mark Masone
 */
public class LobbyPanel extends Panel<LobbyInputListener> {
    
    private final JButton createMatchButton;
    private final JButton settingsButton;

    public LobbyPanel() {
        createMatchButton = new JButton("Create Match");
        settingsButton = new JButton("Settings");
        Menu menu = new Menu();
        menu.addButton(createMatchButton);
        menu.addButton(settingsButton);
        menu.addMenuActionListener(new MenuActionListener() {
            @Override
            public void buttonClicked(JButton button) {
                for(LobbyInputListener lobbyInputListener : inputListeners) {
                    if(button == createMatchButton) {
                        lobbyInputListener.createMatch();
                    } else if(button == settingsButton) {
                        lobbyInputListener.showSettings();
                    }
                }
            }
        });
        add(menu);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        paintBackground(g2d,"/resources/background.jpg");
    }
}
