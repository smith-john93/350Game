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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mark Masone
 */
class FiniteSetting extends JPanel {
    
    private final SettingsPanel settingsPanel;
    private final int characterState;
    private final String label;
    
    FiniteSetting(SettingsPanel settingsPanel, int characterState, String label) {
        this.settingsPanel = settingsPanel;
        this.characterState = characterState;
        this.label = label;
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
        
        JTextField settingText = new JTextField();
        settingText.setFont(Settings.SETTING_FONT);
        int settingTextHeight = getFontMetrics(Settings.SETTING_FONT).getHeight();
        settingText.setPreferredSize(new Dimension(200,settingTextHeight));
        settingText.setEnabled(false);
        settingText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String currentKeys = settingText.getText();
                Settings settings = Settings.getSettings();
                KeyEvent[] keyEvents = settings.getKeyMappings(characterState);
                settingText.setText("Press a key");
                settingsPanel.finiteSettingClicked(label);
                addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        boolean addKey = true;
                        KeyEvent[] newKeyEvents;
                        System.out.println("key typed: " + e.getKeyChar());
                        StringBuilder sb = new StringBuilder(currentKeys);
                        if(keyEvents != null) {
                            for(KeyEvent keyEvent : keyEvents) {
                                if(keyEvent.getKeyChar() == e.getKeyChar()) {
                                    settingText.setText(currentKeys);
                                    addKey = false;
                                }
                            }
                            if(addKey) {
                                System.out.println("add key");
                                sb.append(", ");
                                int length = keyEvents.length;
                                newKeyEvents = Arrays.copyOf(keyEvents, length + 1);
                                newKeyEvents[length] = e;
                            } else {
                                removeKeyListener(this);
                                return;
                            }
                        } else {
                            newKeyEvents = new KeyEvent[]{e};
                        }
                        sb.append(e.getKeyChar());
                        settingText.setText(sb.toString());
                        settings.setKeyMappings(characterState, newKeyEvents);
                        removeKeyListener(this);
                    }
                });
                requestFocus();
            }
        });
        
        gbc.gridy = 0;
        add(settingLabel,gbc);
        gbc.gridy = 1;
        add(settingText,gbc);
    }
}
