package cs350project.screens.createaccount;

import cs350project.menu.Menu;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import cs350project.menu.MenuActionListener;
import cs350project.screens.Panel;

public class CreateAccountPanel extends Panel<CreateAccountInputListener> {
    
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton createAccountButton;
    
    public CreateAccountPanel() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        createAccountButton = new JButton("Create Account");
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        setLayout(null);
        
        Menu menu = new Menu();
        menu.addField(usernameField);
        menu.addField(passwordField);
        menu.addButton(createAccountButton);
        menu.addMenuActionListener(new MenuActionListener() {
            @Override
            public void buttonClicked(JButton button) {
                for (CreateAccountInputListener createAccountInputListener : inputListeners) {
                    if(button == createAccountButton) {
                        createAccountInputListener.createAccount();
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
}
