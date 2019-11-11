/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.settings;

import cs350project.Settings;
import cs350project.characters.CharacterState;
import cs350project.menu.BackButtonPanel;
import cs350project.screens.Panel;
import cs350project.screens.lobby.LobbyInputListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

import javax.swing.border.LineBorder;

/**
 *
 * @author Mark Masone
 */
public class SettingsPanel extends Panel<SettingsInputListener> {
    
    private final String moveLeft = "Move Left";
    private final String moveRight = "Move Right";
    private final String crouch = "Crouch";
    private final String punch = "Punch";
    private final String lowKick = "Low Kick";
    private final String block = "Block";
    private final String jump = "Jump";
    private final String highKick = "High Kick";
    
    @Override
    public void addNotify() {
        super.addNotify();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0.5;

        setLayout(new GridBagLayout());

        setBackground(Color.black);

        setBorder(new LineBorder(Color.white, 5));

        IncrementalSetting resolutionSetting = new IncrementalSetting();
        IncrementalSetting musicVolumeSetting = new IncrementalSetting();
        FiniteSetting moveLeftSetting = new FiniteSetting(this,CharacterState.MOVING_LEFT,moveLeft);
        FiniteSetting moveRightSetting = new FiniteSetting(this,CharacterState.MOVING_RIGHT,moveRight);
        FiniteSetting crouchSetting = new FiniteSetting(this,CharacterState.CROUCHING,crouch);
        FiniteSetting punchSetting = new FiniteSetting(this,CharacterState.PUNCH,punch);
        FiniteSetting lowKickSetting = new FiniteSetting(this,CharacterState.LOW_KICK,lowKick);
        FiniteSetting blockSetting = new FiniteSetting(this,CharacterState.BLOCKING,block);
        FiniteSetting jumpSetting = new FiniteSetting(this,CharacterState.JUMPING,jump);
        FiniteSetting highKickSetting = new FiniteSetting(this,CharacterState.HIGH_KICK,highKick);

        JLabel settingsLabel = new JLabel("SETTINGS");
        settingsLabel.setFont(Settings.HEADING_FONT);
        settingsLabel.setForeground(Color.white);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = Settings.TOP_LEFT_INSETS;
        add(settingsLabel,gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(resolutionSetting,gbc);
        gbc.gridy = 2;
        add(musicVolumeSetting,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(moveLeftSetting,gbc);
        gbc.gridy = 2;
        add(crouchSetting,gbc);
        gbc.gridy = 3;
        add(lowKickSetting,gbc);
        gbc.gridy = 4;
        gbc.insets = Settings.NO_RIGHT_INSET;
        add(jumpSetting,gbc);

        gbc.insets = Settings.NO_BOTTOM_INSET;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(moveRightSetting,gbc);
        gbc.gridy = 2;
        add(punchSetting,gbc);
        gbc.gridy = 3;
        add(blockSetting,gbc);
        gbc.gridy = 4;
        gbc.insets = Settings.ALL_INSETS;
        add(highKickSetting,gbc);
    }
    
    void finiteSettingClicked(String label) {
        switch(label) {
            case moveLeft:
                
        }
    }
    
    //@Override
    //public Dimension getPreferredSize() {
        //return new Dimension(800,600);
    //}
}
