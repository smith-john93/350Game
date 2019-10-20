package cs350project.screens.panels;

import cs350project.menu.Menu;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import cs350project.menu.MenuActionListener;
import cs350project.screens.listeners.LoginInputListener;

public class LoginPanel extends Panel {
    private final ArrayList<LoginInputListener> loginInputListeners;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;

    public LoginPanel() {
        loginInputListeners = new ArrayList<>();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Log In");
        Menu menu = new Menu();
        menu.addField(usernameField);
        menu.addField(passwordField);
        menu.addButton(loginButton);
        menu.addMenuActionListener(new MenuActionListener() {
            @Override
            public void buttonClicked(JButton button) {
                for(LoginInputListener loginInputListener : loginInputListeners) {
                    if(button == loginButton) {
                        char[] password = passwordField.getPassword();
                        loginInputListener.login(usernameField.getText(),password);
                        Arrays.fill(password,'0'); // Clear the password array for security.
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

    public void addLoginInputListener(LoginInputListener loginMenuInputListener) {
        loginInputListeners.add(loginMenuInputListener);
    }
}
