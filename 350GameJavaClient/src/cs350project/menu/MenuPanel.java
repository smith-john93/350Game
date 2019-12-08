/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.menu;

import cs350project.Settings;
import cs350project.screens.InputListener;
import cs350project.screens.Panel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 * @param <E>
 */
public abstract class MenuPanel<E extends InputListener> extends Panel<E> {
    
    private boolean backButtonEnabled = true;
    private int anchor = GridBagConstraints.CENTER;
    
    public void setBackButtonEnabled(boolean backButtonEnabled) {
        this.backButtonEnabled = backButtonEnabled;
    }
    
    public void setAnchor(int anchor) {
        this.anchor = anchor;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new GridBagLayout());
        borderedPanel.setBackground(Settings.MENU_BACKGROUND_COLOR);
        borderedPanel.setBorder(Settings.BORDER_MENU);
        
        /* Keep getMenuItems here to ensure that calls to the setters within 
        it will take visible affect on the panel.
        */
        JComponent[] menuItems = getMenuItems();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = anchor;
        gbc.weightx = 0.5;
        
        if(menuItems != null) {
            int size = menuItems.length;
            for(int i = 0; i < size; i++) {
                if(i == 0) {
                    gbc.insets = Settings.INSETS_MENU_ALL;
                } else {
                    gbc.insets = Settings.INSETS_MENU_NO_TOP;
                }
                gbc.gridy = i;
                borderedPanel.add(menuItems[i],gbc);
            }
        }
        
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new GridBagLayout());
        centeredPanel.setBackground(Settings.TRANSPARENT);
        
        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        centeredPanel.add(borderedPanel,gbc);
        
        if(backButtonEnabled) {
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            centeredPanel.add(new BackButtonPanel(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for(E inputListener : inputListeners) {
                        if(inputListener instanceof BackButtonListener) {
                            ((BackButtonListener)inputListener).back();
                        }
                    }
                }
            }),gbc);
        }

        setLayout(new GridBagLayout());
        setBackground(Settings.TRANSPARENT);

        gbc = new GridBagConstraints();
        add(centeredPanel,gbc);
    }
    
    public abstract JComponent[] getMenuItems();
}
