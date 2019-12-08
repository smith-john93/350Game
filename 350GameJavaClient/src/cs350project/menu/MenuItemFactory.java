/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.menu;

import cs350project.Settings;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
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
        return createButton(Settings.MENU_BUTTON_DIMENSION,text);
    }
    
    public static JButton createSmallButton(String text) {
        JButton jButton = createButton(Settings.MENU_BUTTON_SMALL_DIMENSION,text);
        FontMetrics fontMetrics = jButton.getFontMetrics(jButton.getFont());
        int stringWidth = fontMetrics.stringWidth(text);
        Insets margin = jButton.getMargin();
        int width = margin.left + stringWidth + margin.right;
        if(width > Settings.MENU_BUTTON_SMALL_DIMENSION.width) {
            jButton.setPreferredSize(
                    new Dimension(
                            margin.left + width + margin.right,
                            Settings.MENU_BUTTON_SMALL_DIMENSION.height
                    )
            );
        }
        return jButton;
    }
    
    public static JButton createButton(Dimension size, String text) {
        JButton button = new JButton(text);
        button.setFont(Settings.BUTTON_FONT);
        button.setPreferredSize(size);
        return button;
    }
    
    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(Settings.DIMENSION_MENU_TEXT_FIELD);
        return textField;
    }
    
    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(Settings.DIMENSION_MENU_TEXT_FIELD);
        return passwordField;
    }
    
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Settings.FONT_MENU_HEADING);
        label.setForeground(Settings.MENU_FOREGROUND_COLOR);
        return label;
    }
}
