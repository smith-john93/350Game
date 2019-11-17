package cs350project.screens;

import cs350project.CS350Project;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.CreateAccountPanel;
import cs350project.screens.listeners.CreateAccountInputListener;

public class CreateAccountScreen extends Screen implements CreateAccountInputListener {
    
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
        createAccountPanel.addInputListener(this);
        addPanel(createAccountPanel);
    }

    @Override
    public void createAccount() {
        CS350Project.showScreen(new LobbyScreen());
    }
}
