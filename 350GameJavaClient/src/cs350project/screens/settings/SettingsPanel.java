/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.GameFrame;
import cs350project.Settings;
import cs350project.characters.CharacterState;
import cs350project.menu.MenuItemFactory;
import cs350project.menu.MenuPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class SettingsPanel extends MenuPanel<SettingsInputListener> {
    
    private final Settings settings = Settings.getSettings();
    private HashMap<Integer,Integer> keyMappings = new HashMap<>();
    
    private int screenDimensionsIndex = 0;
    private final int screenDimensionsLength = Settings.SCREEN_DIMENSIONS.length;
    
    private final String moveLeft = "Move Left";
    private final String moveRight = "Move Right";
    private final String crouch = "Crouch";
    private final String punch = "Punch";
    private final String lowKick = "Low Kick";
    private final String block = "Block";
    private final String jump = "Jump";
    private final String highKick = "High Kick";
    
    private IncrementalSetting screenDimensionSetting;
    private IncrementalSetting musicVolumeSetting;
    
    private KeyMapSettingPanel[] keyMapSettingPanels;
    
    public void setKeyMappings(HashMap<Integer,Integer> keyMappings) {
        this.keyMappings = keyMappings;
    }
    
    public HashMap<Integer, Integer> getKeyMappings() {
        return keyMappings;
    }
    
    private void updateScreenDimension() {
        Dimension screenDimension = Settings.SCREEN_DIMENSIONS[screenDimensionsIndex];
        settings.setScreenDimension(screenDimension);
        screenDimensionSetting.setText(screenDimension.width + " x " + screenDimension.height);
        GameFrame.getInstance().setSize(screenDimension);
    }
    
    void settingIncrement(IncrementalSetting incrementalSetting) {
        if(incrementalSetting == screenDimensionSetting) {
            if(screenDimensionsIndex == screenDimensionsLength - 1) {
                screenDimensionsIndex = 0;
            } else {
                screenDimensionsIndex++;
            }
            updateScreenDimension();
        }
    }
    
    void settingDecrement(IncrementalSetting incrementalSetting) {
        if(incrementalSetting == screenDimensionSetting) {
            if(screenDimensionsIndex == 0) {
                screenDimensionsIndex = screenDimensionsLength - 1;
            } else {
                screenDimensionsIndex--;
            }
            updateScreenDimension();
        }
    }

    @Override
    public JComponent[] getMenuItems() {
        
        setAnchor(GridBagConstraints.LINE_START);
        
        screenDimensionSetting = new IncrementalSetting(this);
        musicVolumeSetting = new IncrementalSetting(this);

        keyMapSettingPanels = new KeyMapSettingPanel[]{
            new KeyMapSettingPanel(this,CharacterState.MOVING_LEFT,moveLeft),
            new KeyMapSettingPanel(this,CharacterState.MOVING_RIGHT,moveRight),
            new KeyMapSettingPanel(this,CharacterState.CROUCHING,crouch),
            new KeyMapSettingPanel(this,CharacterState.PUNCH,punch),
            new KeyMapSettingPanel(this,CharacterState.LOW_KICK,lowKick),
            new KeyMapSettingPanel(this,CharacterState.BLOCKING,block),
            new KeyMapSettingPanel(this,CharacterState.JUMPING,jump),
            new KeyMapSettingPanel(this,CharacterState.HIGH_KICK,highKick)
        };
        
        JPanel settingsGridPanel = new JPanel();
        settingsGridPanel.setLayout(new GridBagLayout());
        settingsGridPanel.setBackground(Settings.TRANSPARENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        gbc.gridy = 0;
        
        JPanel[][] gridPanels = {
            {screenDimensionSetting, keyMapSettingPanels[0], keyMapSettingPanels[1]},
            {musicVolumeSetting, keyMapSettingPanels[2], keyMapSettingPanels[3]},
            {null, keyMapSettingPanels[4], keyMapSettingPanels[5]},
            {null, keyMapSettingPanels[6], keyMapSettingPanels[7]}
        };
        
        for(int row = 0; row < gridPanels.length; row++) {
            gbc.gridx = 0;
            for(int col = 0; col < gridPanels[row].length; col++) {
                JPanel gridPanel = gridPanels[row][col];
                if(gridPanel != null) {
                    if(row == 0 && col == 0) {
                        gbc.insets = Settings.INSETS_NONE;
                    } else if(row == 0) {
                        gbc.insets = Settings.INSETS_MENU_LEFT;
                    } else if(col == 0) {
                        gbc.insets = Settings.INSETS_MENU_TOP;
                    } else {
                        gbc.insets = Settings.INSETS_MENU_TOP_LEFT;
                    }
                    settingsGridPanel.add(gridPanel,gbc);
                }
                gbc.gridx++;
            }
            gbc.gridy++;
        }
        
        JLabel settingsLabel = MenuItemFactory.createLabel("SETTINGS");
        
        updateScreenDimension();
        updateKeyMapSettings();
        
        JButton saveButton = MenuItemFactory.createButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(SettingsInputListener settingsInputListener : inputListeners) {
                    //System.out.println("save");
                    settingsInputListener.save();
                }
            }
        });
        
        return new JComponent[]{settingsLabel,settingsGridPanel,saveButton};
    }
    
    public void changeKeyMapping(int keyCode, int actionCode) {
        keyMappings.put(keyCode, actionCode);
        updateKeyMapSettings();
    }
    
    private void updateKeyMapSettings() {
        //System.out.println("update key map settings");
        HashMap<Integer, ArrayList<String>> actionMappings = new HashMap<>();
        for(Entry<Integer, Integer> entry : keyMappings.entrySet()) {
            int value = entry.getValue();
            ArrayList<String> keyCodes = actionMappings.get(value);
            if(keyCodes == null) {
                keyCodes = new ArrayList<>();
            }
            keyCodes.add(KeyEvent.getKeyText(entry.getKey()));
            actionMappings.put(value, keyCodes);
        }
        
        for(KeyMapSettingPanel keyMapSettingPanel : keyMapSettingPanels) {
            ArrayList<String> keyCharsList = actionMappings.get(keyMapSettingPanel.getMapCode());
            String[] keyChars;
            if(keyCharsList == null) {
                keyChars = new String[0];
            } else {
                keyChars = new String[keyCharsList.size()];
                int i = 0;
                for(String keyChar : keyCharsList) {
                    keyChars[i++] = keyChar;
                    //System.out.println(keyChar);
                }
            }
            keyMapSettingPanel.setKeyChars(keyChars);
        }
    }
}
