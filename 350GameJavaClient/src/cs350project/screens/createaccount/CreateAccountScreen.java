package cs350project.screens.createaccount;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.Validate;
import cs350project.communication.Communication;
import cs350project.communication.CommunicationException;
import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.MessageDialog;
import cs350project.screens.lobby.LobbyScreen;
import cs350project.screens.BackgroundImage;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;
import cs350project.screens.mainmenu.MainMenuScreen;

import javax.swing.JPanel;
import java.io.DataInputStream;

public class CreateAccountScreen extends Screen implements CreateAccountInputListener, IncomingCommandListener {

    private final Communication comm;
    private String username;
    
    public CreateAccountScreen() {
        comm = Communication.getInstance();
    }
    
    @Override
    public KeyMap getKeyMap() {
        return null;
    }

    @Override
    public void createAccount(String username, char[] password) {
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
                comm.createAccount(username, password);
            }
        },"Creating account...");
    }

    @Override
    public void back() {
        comm.removeIncomingCommandListener(this);
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage(Settings.MENU_BACKGROUND_FILE);
    }

    @Override
    public JPanel getJPanel() {
        CreateAccountPanel createAccountPanel = new CreateAccountPanel();
        createAccountPanel.addInputListener(this);
        comm.addIncomingCommandListener(this);
        comm.addIncomingCommandListener(Settings.getSettings());
        return createAccountPanel;
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        switch(serverCommand) {
            case USER_AUTH_PASS:
                comm.removeIncomingCommandListener(this);
                GameFrame.getInstance().showScreen(new LobbyScreen());
                break;
            case USER_AUTH_FAIL:
                MessageDialog.showErrorMessage("Username \"" + username + "\" is already taken.", getClass());
                break;
        }
    }
}
