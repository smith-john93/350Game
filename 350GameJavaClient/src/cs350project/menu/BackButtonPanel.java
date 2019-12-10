/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.menu;

import cs350project.Settings;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Mark Masone
 */
public class BackButtonPanel extends JPanel {
    
    private final ActionListener actionListener;
    public static final String ACTION_COMMAND = "Back";
    
    public BackButtonPanel(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
        setBorder(new EmptyBorder(Settings.INSETS_MENU_TOP));
        // Make the background transparent.
        setBackground(Settings.TRANSPARENT);
   
        JButton backButton = MenuItemFactory.createSmallButton(ACTION_COMMAND);
        backButton.addActionListener(actionListener);
        add(backButton);
    }
}
