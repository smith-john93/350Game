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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public class SettingsPanel extends MenuPanel<SettingsInputListener> {
    
    private final Settings settings = Settings.getSettings();
    
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
    
    void finiteSettingClicked(String label) {
        switch(label) {
            case moveLeft:
                
        }
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

        FiniteSetting moveLeftSetting = new FiniteSetting(this,CharacterState.MOVING_LEFT,moveLeft);
        FiniteSetting moveRightSetting = new FiniteSetting(this,CharacterState.MOVING_RIGHT,moveRight);
        FiniteSetting crouchSetting = new FiniteSetting(this,CharacterState.CROUCHING,crouch);
        FiniteSetting punchSetting = new FiniteSetting(this,CharacterState.PUNCH,punch);
        FiniteSetting lowKickSetting = new FiniteSetting(this,CharacterState.LOW_KICK,lowKick);
        FiniteSetting blockSetting = new FiniteSetting(this,CharacterState.BLOCKING,block);
        FiniteSetting jumpSetting = new FiniteSetting(this,CharacterState.JUMPING,jump);
        FiniteSetting highKickSetting = new FiniteSetting(this,CharacterState.HIGH_KICK,highKick);
        
        JPanel settingsGridPanel = new JPanel();
        settingsGridPanel.setLayout(new GridBagLayout());
        settingsGridPanel.setBackground(Settings.TRANSPARENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;
        gbc.gridy = 0;
        
        JPanel[][] gridPanels = {
            {screenDimensionSetting, moveLeftSetting, moveRightSetting},
            {musicVolumeSetting, crouchSetting, punchSetting},
            {null, lowKickSetting, blockSetting},
            {null, jumpSetting, highKickSetting}
        };
        
        for(int row = 0; row < gridPanels.length; row++) {
            gbc.gridx = 0;
            for(int col = 0; col < gridPanels[row].length; col++) {
                JPanel settingPanel = gridPanels[row][col];
                if(settingPanel != null) {
                    if(row == 0 && col == 0) {
                        gbc.insets = Settings.INSETS_NONE;
                    } else if(row == 0) {
                        gbc.insets = Settings.INSETS_MENU_LEFT;
                    } else if(col == 0) {
                        gbc.insets = Settings.INSETS_MENU_TOP;
                    } else {
                        gbc.insets = Settings.INSETS_MENU_TOP_LEFT;
                    }
                    settingsGridPanel.add(settingPanel,gbc);
                }
                gbc.gridx++;
            }
            gbc.gridy++;
        }
        
        JLabel settingsLabel = MenuItemFactory.createLabel("SETTINGS");
        
        updateScreenDimension();
        
        return new JComponent[]{settingsLabel,settingsGridPanel};
    }
}
