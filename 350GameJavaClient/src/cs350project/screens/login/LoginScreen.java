package cs350project.screens.login;

import cs350project.screens.lobby.LobbyScreen;
import cs350project.CS350Project;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import java.io.DataInputStream;
import java.util.Arrays;
import javax.swing.JPanel;

public class LoginScreen extends Screen implements LoginInputListener, IncomingCommandListener {

    private final LoginPanel loginPanel;

    public LoginScreen() {
        loginPanel = new LoginPanel();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        loginPanel.addInputListener(this);
    }

    @Override
    public void login(String username, char[] password) {
        /*String dummyPassword = "1";
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
        Arrays.fill(password,'0'); // Clear the password array for security.*/
        Communication comm = Communication.getInstance();
        comm.connect();
        comm.addIncomingCommandListener(this);
        comm.sendCommand(ClientCommand.LOGIN);
        comm.sendCredentials(username, password);
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        return loginPanel;
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        switch(serverCommand) {
            case USER_AUTH_PASS:
                System.out.println("password good");
                break;
            case USER_AUTH_FAIL:
                System.out.println("password fail");
                break;
            case USER_AUTH_BLOCKED:
                System.out.println("auth blocked");
                break;
        }
        Communication.getInstance().removeIncomingCommandListener(this);
    }
}
