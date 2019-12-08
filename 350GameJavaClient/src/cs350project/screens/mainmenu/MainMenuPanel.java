package cs350project.screens.mainmenu;

import javax.swing.JButton;
import cs350project.menu.MenuItemFactory;
import cs350project.menu.MenuPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

public class MainMenuPanel extends MenuPanel<MainMenuInputListener> {
    
    @Override
    public JComponent[] getMenuItems() {
        
        setBackButtonEnabled(false);
        
        final String logInCommand = "Log In";
        final String createAccountCommand = "Create New Account";
        //final String settingsCommand = "Settings";
        
        JButton loginButton = MenuItemFactory.createButton(logInCommand);
        JButton createAccountButton = MenuItemFactory.createButton(createAccountCommand);
        //JButton settingsButton = MenuItemFactory.createButton(settingsCommand);
        
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (MainMenuInputListener mainMenuInputListener : inputListeners) {
                    switch(e.getActionCommand()) {
                        case logInCommand:
                            mainMenuInputListener.login();
                            break;
                        case createAccountCommand:
                            mainMenuInputListener.createAccount();
                            break;
                        /*case settingsCommand:
                            mainMenuInputListener.showSettings();
                            break;*/
                    }
                }
            }
        };
        
        loginButton.addActionListener(buttonListener);
        createAccountButton.addActionListener(buttonListener);
        //settingsButton.addActionListener(buttonListener);
        
        return new JComponent[]{
            loginButton,
            createAccountButton,
            //settingsButton
        };
    }
}
