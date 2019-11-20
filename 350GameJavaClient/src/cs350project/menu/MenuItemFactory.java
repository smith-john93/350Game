/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.menu;

import cs350project.Settings;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Mark Masone
 */
public class MenuItemFactory {
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Settings.BUTTON_FONT);
        button.setPreferredSize(Settings.MENU_BUTTON_DIMENSION);
        return button;
    }
    
    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(Settings.MENU_TEXT_FIELD_DIMENSION);
        return textField;
    }
    
    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(Settings.MENU_TEXT_FIELD_DIMENSION);
        return passwordField;
    }
    
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Settings.MENU_HEADING_FONT);
        label.setForeground(Settings.MENU_FOREGROUND_COLOR);
        return label;
    }
}
