package cs350project.screens.mainmenu;

import cs350project.menu.Menu;
import javax.swing.JButton;
import cs350project.menu.MenuActionListener;
import cs350project.screens.Panel;
import java.awt.Color;

public class MainMenuPanel extends Panel<MainMenuInputListener> {
    
    @Override
    public void addNotify() {
        super.addNotify();
        setLayout(null);
        setBackground(new Color(0,0,0,0));
        
        JButton loginButton = new JButton("Log In");
        JButton createAccountButton = new JButton("Create New Account");
        JButton settingsButton = new JButton("Settings");
        Menu menu = new Menu();
        menu.addButton(loginButton);
        menu.addButton(createAccountButton);
        menu.addButton(settingsButton);
        menu.addMenuActionListener(new MenuActionListener(){
            @Override
            public void buttonClicked(JButton button) {
                for (MainMenuInputListener mainMenuInputListener : inputListeners) {
                    if(button == loginButton) {
                        mainMenuInputListener.login();
                    } else if(button == createAccountButton) {
                        mainMenuInputListener.createAccount();
                    } else if(button == settingsButton) {
                        mainMenuInputListener.showSettings();
                    }
                }
            }
        });
        add(menu);
    }
}
