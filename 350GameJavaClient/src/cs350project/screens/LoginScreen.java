package cs350project.screens;

import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.mouse.LoginMouseListener;
import cs350project.screens.panels.LoginPanel;
import java.util.Arrays;

public class LoginScreen extends Screen implements LoginMouseListener {

    private final LoginPanel loginPanel;

    public LoginScreen() {
        loginPanel = new LoginPanel();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void showPanel() {
        loginPanel.addLoginMouseListener(this);
        addPanel(loginPanel);
    }

    @Override
    public void loginButtonClicked(String username, char[] password) {
        String dummyPassword = "1";
        String dummyUsername = "1";
        if(username.equals(dummyUsername) && password.length > 0) {
            boolean passwordIsValid = true;
            for(int i = 0; i < password.length; i++) {
                if(dummyPassword.charAt(i) != password[i]) {
                    passwordIsValid = false;
                    break;
                }
            }
            if(passwordIsValid) {
                for(ScreenListener screenListener : screenListeners) {
                    screenListener.showScreen(new SelectionScreen());
                }
            }
        }
        Arrays.fill(password,'0'); // Clear the password array for security.
    }
}
