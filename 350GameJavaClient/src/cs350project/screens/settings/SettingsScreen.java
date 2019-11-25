/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.mainmenu.MainMenuScreen;
import javax.swing.JPanel;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class SettingsScreen extends Screen implements SettingsInputListener {

    private final SettingsPanel settingsPanel;
    private final HashMap<Integer, Integer> keyMappings;

    public SettingsScreen() {
        settingsPanel = new SettingsPanel();
        keyMappings = new HashMap<>();
        keyMappings.putAll(Settings.getSettings().getKeyMappings());
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }

    @Override
    public JPanel getJPanel() {
        settingsPanel.addInputListener(this);
        settingsPanel.setKeyMappings(keyMappings);
        return settingsPanel;
    }

    @Override
    public void back() {
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }

    @Override
    public void save() {
        Settings.getSettings().setKeyMappings(keyMappings);
    }
}
