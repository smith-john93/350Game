package cs350project.screens.mainmenu;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.screens.BackgroundImage;
import cs350project.screens.createaccount.CreateAccountScreen;
import cs350project.screens.login.LoginScreen;
import cs350project.screens.Screen;
import cs350project.screens.settings.SettingsScreen;
import cs350project.screens.KeyMap;
import javax.swing.JPanel;

public class MainMenuScreen extends Screen implements MainMenuInputListener {

    @Override
    public KeyMap getKeyMap() {
        return null;
    }
   
    @Override
    public void login() {
        GameFrame.getInstance().showScreen(new LoginScreen());
    }

    @Override
    public void createAccount() {
        GameFrame.getInstance().showScreen(new CreateAccountScreen());
    }
    
    @Override
    public void showSettings() {
        GameFrame.getInstance().showScreen(new SettingsScreen());
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }

    @Override
    public JPanel getJPanel() {
        
        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        mainMenuPanel.addInputListener(this);
        
        return mainMenuPanel;
    }
}
