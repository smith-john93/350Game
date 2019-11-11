package cs350project.screens.mainmenu;

import cs350project.CS350Project;
import cs350project.screens.BackgroundImage;
import cs350project.screens.createaccount.CreateAccountScreen;
import cs350project.screens.login.LoginScreen;
import cs350project.screens.Screen;
import cs350project.screens.settings.SettingsScreen;
import cs350project.screens.KeyMap;
import javax.swing.JPanel;

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
    public void login() {
        CS350Project.showScreen(new LoginScreen());
    }

    @Override
    public void createAccount() {
        CS350Project.showScreen(new CreateAccountScreen());
    }
    
    @Override
    public void showSettings() {
        CS350Project.showScreen(new SettingsScreen());
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        mainMenuPanel.addInputListener(this);
        return mainMenuPanel;
    }
}
