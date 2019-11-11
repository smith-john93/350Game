package cs350project.screens.createaccount;

import cs350project.screens.lobby.LobbyScreen;
import cs350project.CS350Project;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import javax.swing.JPanel;

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
    public void addNotify() {
        super.addNotify();
        createAccountPanel.addInputListener(this);
    }

    @Override
    public void createAccount() {
        CS350Project.showScreen(new LobbyScreen());
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        return createAccountPanel;
    }
}
