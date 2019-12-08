package cs350project.screens.login;

import cs350project.menu.MenuItemFactory;
import cs350project.menu.MenuPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends MenuPanel<LoginInputListener> {
    
    @Override
    public JComponent[] getMenuItems() {
        JTextField usernameField = MenuItemFactory.createTextField();
        JPasswordField passwordField = MenuItemFactory.createPasswordField();
        
        // delete the next 2 lines after testing is completed
        usernameField.setText("1");
        passwordField.setText("1");
        
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
        
        return new JComponent[]{usernameField,passwordField,loginButton};
    }
}
