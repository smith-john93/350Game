/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.title;

import cs350project.GameFrame;
import cs350project.screens.BackgroundImage;
import cs350project.screens.KeyMap;
import cs350project.screens.Screen;
import cs350project.screens.mainmenu.MainMenuScreen;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class TitleScreen extends Screen implements TitleInputListener {

    private final TitleKeyMap titleKeyMap = new TitleKeyMap();
    
    @Override
    public BackgroundImage getBackgroundImage() {
        return null;
    }

    @Override
    public JPanel getJPanel() {
        String continueKeyText = titleKeyMap.getContinueKeyText().toUpperCase();
        TitlePanel titlePanel = new TitlePanel(continueKeyText);
        titlePanel.addInputListener(this);
        return titlePanel;
    }

    @Override
    public KeyMap getKeyMap() {
        titleKeyMap.addInputListener(this);
        return titleKeyMap;
    }

    @Override
    public void start() {
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }
    
}
