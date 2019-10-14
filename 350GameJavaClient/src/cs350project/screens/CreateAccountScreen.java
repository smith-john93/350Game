package cs350project.screens;

import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.mouse.CreateAccountMouseListener;
import cs350project.screens.panels.CreateAccountPanel;

public class CreateAccountScreen extends Screen implements CreateAccountMouseListener {
    
    private final CreateAccountPanel createAccountPanel;
    
    public CreateAccountScreen() {
        createAccountPanel = new CreateAccountPanel();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void showPanel() {
        createAccountPanel.addCreateAccountMouseListener(this);
        addPanel(createAccountPanel);
    }

    @Override
    public void createAccountButtonClicked() {
        for(ScreenListener screenListener : screenListeners) {
            screenListener.showScreen(new SelectionScreen());
        }
    }
}
