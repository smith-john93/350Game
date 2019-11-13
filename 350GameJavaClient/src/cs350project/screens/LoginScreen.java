package cs350project.screens;

import cs350project.CS350Project;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.LoginPanel;
import java.util.Arrays;
import cs350project.screens.listeners.LoginInputListener;

public class LoginScreen extends Screen implements LoginInputListener {

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
        loginPanel.addInputListener(this);
        addPanel(loginPanel);
    }

    @Override
    public void login(String username, char[] password) {
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
                CS350Project.showScreen(new LobbyScreen());
            }
        }
        Arrays.fill(password,'0'); // Clear the password array for security.
    }
}
