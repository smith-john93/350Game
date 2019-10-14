package cs350project.screens;

import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.mouse.MainMenuMouseListener;
import cs350project.screens.panels.MainMenuPanel;

public class MainMenuScreen extends Screen implements MainMenuMouseListener {

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
        mainMenuPanel.addMainMenuButtonListener(this);
        addPanel(mainMenuPanel);
    }

    @Override
    public void loginButtonClicked() {
        for(ScreenListener screenListener : screenListeners) {
            screenListener.showScreen(new LoginScreen());
        }
    }

    @Override
    public void createAccountButtonClicked() {
        for(ScreenListener screenListener : screenListeners) {
            screenListener.showScreen(new CreateAccountScreen());
        }
    }
}
