package cs350project.screens.login;

import cs350project.Settings;
import cs350project.menu.MenuItemFactory;
import cs350project.menu.MenuPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends MenuPanel<LoginInputListener> {
    
    @Override
    public JComponent[] getMenuItems() {
        
        JLabel usernameLabel = MenuItemFactory.createFieldLabel(Settings.FIELD_NAME_USERNAME);
        JLabel passwordLabel = MenuItemFactory.createFieldLabel(Settings.FIELD_NAME_PASSWORD);
        
        JTextField usernameField = MenuItemFactory.createTextField();
        JPasswordField passwordField = MenuItemFactory.createPasswordField();
        
        JButton loginButton = MenuItemFactory.createButton("Log In");
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                for (LoginInputListener loginInputListener : inputListeners) {
                    loginInputListener.login(
                            usernameField.getText(), 
                            passwordField.getPassword()
                    );
                }
            }
        });
        
        return new JComponent[]{
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            loginButton
        };
    }
}
