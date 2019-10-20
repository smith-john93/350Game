package cs350project.screens.panels;

import cs350project.menu.Menu;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import java.util.ArrayList;
import cs350project.menu.MenuActionListener;
import cs350project.screens.listeners.MainMenuInputListener;

public class MainMenuPanel extends Panel {

    private ArrayList<MainMenuInputListener> mainMenuInputListeners;
    private final JButton loginButton;
    private final JButton createAccountButton;

    public MainMenuPanel() {
        mainMenuInputListeners = new ArrayList<>();
        loginButton = new JButton("Log In");
        createAccountButton = new JButton("Create New Account");
        Menu menu = new Menu();
        menu.addButton(loginButton);
        menu.addButton(createAccountButton);
        menu.addMenuActionListener(new MenuActionListener(){
            @Override
            public void buttonClicked(JButton button) {
                for (MainMenuInputListener mainMenuInputListener : mainMenuInputListeners) {
                    if(button == loginButton) {
                        mainMenuInputListener.login();
                    } else if(button == createAccountButton) {
                        mainMenuInputListener.createAccount();
                    }
                }
            }
        });
        add(menu);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        paintBackground(g2d,"/resources/background.jpg");
    }
    
    public void addMainMenuInputListener(MainMenuInputListener mainMenuInputListener) {
        mainMenuInputListeners.add(mainMenuInputListener);
    }
}
