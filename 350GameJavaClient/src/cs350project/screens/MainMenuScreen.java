package cs350project.screens;

import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.MainMenuPanel;
import cs350project.screens.listeners.MainMenuInputListener;

public class MainMenuScreen extends Screen implements MainMenuInputListener {

    private final MainMenuPanel mainMenuPanel;

    public MainMenuScreen() {
        mainMenuPanel = new MainMenuPanel();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void showPanel() {
        mainMenuPanel.addInputListener(this);
        addPanel(mainMenuPanel);
    }

    @Override
    public void login() {
        showScreen(new LoginScreen());
    }

    @Override
    public void createAccount() {
        showScreen(new CreateAccountScreen());
    }
}
