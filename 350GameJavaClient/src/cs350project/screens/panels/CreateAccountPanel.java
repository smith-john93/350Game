package cs350project.screens.panels;

import cs350project.menu.Menu;
import cs350project.screens.mouse.CreateAccountMouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import cs350project.menu.MenuActionListener;

public class CreateAccountPanel extends Panel {
    
    private final ArrayList<CreateAccountMouseListener> createAccountMouseListeners;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton createAccountButton;
    
    public CreateAccountPanel() {
        createAccountMouseListeners = new ArrayList<>();
        setLayout(null);
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        createAccountButton = new JButton("Create Account");
        Menu menu = new Menu();
        menu.addField(usernameField);
        menu.addField(passwordField);
        menu.addButton(createAccountButton);
        menu.addMenuMouseListener(new MenuActionListener() {
            @Override
            public void buttonClicked(JButton button) {
                for (CreateAccountMouseListener createAccountMouseListener : createAccountMouseListeners) {
                    if(button == createAccountButton) {
                        createAccountMouseListener.createAccountButtonClicked();
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
    
    public void addCreateAccountMouseListener(CreateAccountMouseListener createAccountMouseListener) {
        createAccountMouseListeners.add(createAccountMouseListener);
    }
}
