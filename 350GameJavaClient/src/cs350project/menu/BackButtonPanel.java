/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.menu;

import cs350project.Settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class BackButtonPanel extends JPanel {
    
    private final ActionListener actionListener;
    
    public BackButtonPanel(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        // Make the background transparent.
        setBackground(Settings.TRANSPARENT);
   
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150,50));
        backButton.setFont(Settings.BUTTON_FONT);
        backButton.addActionListener(actionListener);
        add(backButton);
    }
}
