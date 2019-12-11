/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.communication.Communication;
import cs350project.screens.KeyMap;
import cs350project.screens.LoadingDialog;
import cs350project.screens.MessageDialog;
import cs350project.screens.Screen;
import cs350project.screens.mainmenu.MainMenuScreen;
import cs350project.screens.selection.SelectionScreen;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mark Masone
 */
public class GameFrame extends JFrame {
    
    private static GameFrame gameFrame;
    
    
    private Screen screen;
    private KeyMap keyMap;

    private GameFrame() {

    }

    public static GameFrame getInstance() {
        if(gameFrame == null) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        gameFrame = new GameFrame();
                        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        gameFrame.setTitle(Settings.GAME_TITLE);
                        gameFrame.pack();
                        Settings settings = Settings.getSettings();
                        Dimension screenDimension = settings.getScreenDimension();
                        gameFrame.setSize(screenDimension);
                    }
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return gameFrame;
    }
    
    public void showScreen(Screen screen) {
        if(SwingUtilities.isEventDispatchThread()) {
            if(this.screen != null) {
                remove(this.screen);
            }
            setKeyMap(screen.getKeyMap());
            add(screen);
            setVisible(true);
            requestFocus();
            this.screen = screen;
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        showScreen(screen);
                    }
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void setSize(Dimension screenDimension) {
        Insets insets = getInsets();
        int windowW = screenDimension.width + insets.left + insets.right;
        int windowH = screenDimension.height + insets.top + insets.bottom;
        super.setSize(windowW, windowH);
    }
    
    private void setKeyMap(KeyMap keyMap) {
        if(keyMap != null) {
            if (this.keyMap != null) {
                removeKeyListener(this.keyMap);
            }
            this.keyMap = keyMap;
            addKeyListener(keyMap);
        }
    }
    
    private boolean setServerAddress(String serverAddress) {
        LoadingDialog serverAddressDialog = new LoadingDialog("Checking address...");
        serverAddressDialog.open();

        try {
            Communication.getInstance().setServerAddress(serverAddress);
            serverAddressDialog.close();
            Settings.getSettings().saveSetting(
                    Settings.SETTING_SERVER_ADDRESS, 
                    serverAddress
            );
            return true;
        } catch (UnknownHostException ex) {
            serverAddressDialog.close();
            MessageDialog.showErrorMessage(
                    gameFrame,
                    ex.getMessage(), 
                    gameFrame.getClass()
            );
        }
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Communication comm = Communication.getInstance();
        //GameFrame.getInstance().showScreen(new TitleScreen());
        
        GameFrame.getInstance().showScreen(new MainMenuScreen());

        /*try {
            GameFrame.getInstance().showScreen(new SelectionScreen());
        } catch (IOException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        String serverAddress = Settings.getSettings().getSetting(Settings.SETTING_SERVER_ADDRESS);
        
        if(serverAddress == null) {
        
            while(true) {
                serverAddress = JOptionPane.showInputDialog(
                        gameFrame, 
                        "Enter server address.", 
                        Settings.GAME_TITLE,
                        JOptionPane.PLAIN_MESSAGE
                );

                if(serverAddress == null) {
                    System.exit(0);
                }

                if(serverAddress.isBlank()) {
                    MessageDialog.showErrorMessage(
                            gameFrame,
                            "This field cannot be blank.", 
                            gameFrame.getClass()
                    );
                    continue;
                }

                if(gameFrame.setServerAddress(serverAddress)) {
                    break;
                }
            }
        } else {
            gameFrame.setServerAddress(serverAddress);
        }
    }
}
