package cs350project.screens.createaccount;

import cs350project.Settings;
import cs350project.menu.MenuItemFactory;
import cs350project.menu.MenuPanel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class CreateAccountPanel extends MenuPanel<CreateAccountInputListener> {
    
    @Override
    public JComponent[] getMenuItems() {
        
        JLabel usernameLabel = MenuItemFactory.createFieldLabel(Settings.FIELD_NAME_USERNAME);
        JLabel passwordLabel = MenuItemFactory.createFieldLabel(Settings.FIELD_NAME_PASSWORD);
        
        JTextField usernameField = MenuItemFactory.createTextField();
        JPasswordField passwordField = MenuItemFactory.createPasswordField();

        JButton createAccountButton = MenuItemFactory.createButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (CreateAccountInputListener createAccountInputListener : inputListeners) {
                    createAccountInputListener.createAccount(
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
            createAccountButton
        };
    }
}
