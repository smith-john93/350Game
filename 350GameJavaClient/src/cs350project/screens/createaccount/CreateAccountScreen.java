package cs350project.screens.createaccount;

import cs350project.GameFrame;
import cs350project.communication.ClientCommand;
import cs350project.communication.Communication;
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
        if(comm.connect()) {
            comm.sendCommand(ClientCommand.CREATE_ACCOUNT);
            this.username = username;
            comm.sendCredentials(username, password);
        }
    }

    @Override
    public void back() {
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }

    @Override
    public BackgroundImage getBackgroundImage() {
        return new BackgroundImage("/resources/menu/background.jpg");
    }

    @Override
    public JPanel getJPanel() {
        CreateAccountPanel createAccountPanel = new CreateAccountPanel();
        createAccountPanel.addInputListener(this);
        comm.addIncomingCommandListener(this);
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
