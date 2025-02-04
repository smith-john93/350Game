package cs350project.screens.login;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.Validate;
import cs350project.screens.MessageDialog;
import cs350project.screens.lobby.LobbyScreen;
import cs350project.communication.Communication;
import cs350project.communication.CommunicationException;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.mainmenu.MainMenuScreen;
import java.io.DataInputStream;
import javax.swing.JPanel;

public class LoginScreen extends Screen implements LoginInputListener, IncomingCommandListener {

    private final Communication comm;
    private String username;
    private int loginAttempts = 4;

    public LoginScreen() {
        comm = Communication.getInstance();
    }

    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void login(String username, char[] password) {
        if(!Validate.chars(this,"Username",username.toCharArray())) {
            return;
        }
        if(!Validate.chars(this,"Password",password)) {
            return;
        }
        this.username = username;
        createLoadingDialog(new Loader() {
            @Override
            protected void load() throws CommunicationException {
                comm.login(username, password);
            }
        },"Logging in...");
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }

    @Override
    public JPanel getJPanel() {
        
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.addInputListener(this);
        comm.addIncomingCommandListener(this);
        comm.addIncomingCommandListener(Settings.getSettings());
        
        return loginPanel;
    }

    @Override
    public void back() {
        comm.removeIncomingCommandListener(this);
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        //System.out.println("command received: " + serverCommand);
        switch(serverCommand) {
            case USER_AUTH_PASS:
                System.out.println("user auth pass");
                comm.removeIncomingCommandListener(this);
                GameFrame.getInstance().showScreen(new LobbyScreen());
                break;
            case USER_AUTH_FAIL:
                MessageDialog.showErrorMessage("Invalid password. " + loginAttempts-- + " login attempts remaining.", getClass());
                break;
            case USER_AUTH_BLOCKED:
                MessageDialog.showErrorMessage("Logins for " + username + " have been temporarily blocked.", getClass());
                break;
        }
    }
}
