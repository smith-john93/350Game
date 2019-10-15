package cs350project.screens.panels;

import cs350project.menu.Menu;
import cs350project.screens.mouse.MainMenuMouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import java.util.ArrayList;
import cs350project.menu.MenuActionListener;

public class MainMenuPanel extends Panel {

    private ArrayList<MainMenuMouseListener> mainMenuMouseListeners;
    private final JButton loginButton;
    private final JButton createAccountButton;

    public MainMenuPanel() {
        mainMenuMouseListeners = new ArrayList<>();
        loginButton = new JButton("Log In");
        createAccountButton = new JButton("Create New Account");
        Menu menu = new Menu();
        menu.addButton(loginButton);
        menu.addButton(createAccountButton);
        menu.addMenuActionListener(new MenuActionListener(){
            @Override
            public void buttonClicked(JButton button) {
                for (MainMenuMouseListener mainMenuMouseListener : mainMenuMouseListeners) {
                    if(button == loginButton) {
                        mainMenuMouseListener.loginButtonClicked();
                    } else if(button == createAccountButton) {
                        mainMenuMouseListener.createAccountButtonClicked();
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
    
    public void addMainMenuButtonListener(MainMenuMouseListener mainMenuMouseListener) {
        mainMenuMouseListeners.add(mainMenuMouseListener);
    }
}
