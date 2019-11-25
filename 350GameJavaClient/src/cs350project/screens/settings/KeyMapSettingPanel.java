/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.Settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mark Masone
 */
class KeyMapSettingPanel extends JPanel {
    
    private final String label;
    private String[] keyChars;
    private final SettingsPanel settingsPanel;
    private final int mapCode;
    private final JTextField settingText;
    
    KeyMapSettingPanel(SettingsPanel settingsPanel, int mapCode, String label) {
        this.settingsPanel = settingsPanel;
        this.label = label;
        this.mapCode = mapCode;
        settingText = new JTextField();
    }
    
    int getMapCode() {
        return mapCode;
    }
    
    String[] getKeyChars() {
        return keyChars;
    }
    
    void setKeyChars(String[] keyChars) {
        this.keyChars = keyChars;
        updateText();
    }
    
    private void updateText() {
        System.out.println("update text");
        StringBuilder sb = new StringBuilder();
        if(keyChars != null) {
            for(int i = 0; i < keyChars.length; i++) {
                if(i != 0) {
                    sb.append(", ");
                }
                sb.append(keyChars[i]);
            }
        }
        settingText.setText(sb.toString());
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        
        setLayout(new GridBagLayout());
        setBackground(Settings.TRANSPARENT);
        //setPreferredSize(new Dimension(600,40));
        //setMaximumSize(new Dimension(440,80));
        
        JLabel settingLabel = new JLabel(label);
        settingLabel.setForeground(Color.white);
        settingLabel.setFont(Settings.HEADING1_FONT);
        
        settingText.setFont(Settings.SETTING_FONT);
        int settingTextHeight = getFontMetrics(Settings.SETTING_FONT).getHeight();
        settingText.setPreferredSize(new Dimension(200,settingTextHeight));
        settingText.setDisabledTextColor(Color.black);
        settingText.setEnabled(false);
        
        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("key released on key map setting");
                if(settingsPanel != null) {
                    settingsPanel.changeKeyMapping(e.getKeyCode(),mapCode);
                }
                transferFocusUpCycle();
            }
        };
        
        settingText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("key map setting clicked");
                requestFocus();
            }
        });
        
        addFocusListener(new FocusListener() {
            
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("key map setting focus gained");
                addKeyListener(keyListener);
                settingText.setText("Press a key");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("key map setting focus lost");
                removeKeyListener(keyListener);
                updateText();
            }
        });
        
        gbc.gridy = 0;
        add(settingLabel,gbc);
        gbc.gridy = 1;
        add(settingText,gbc);
    }
}
