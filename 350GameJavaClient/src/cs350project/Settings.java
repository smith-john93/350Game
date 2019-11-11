/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;

import cs350project.characters.CharacterState;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */
public class Settings {
    
    private static Settings settings;
    private int screenW;
    private int screenH;
    private final HashMap<Integer,KeyEvent[]> keyMappings;

    public static final Font BUTTON_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font BUTTON_FONT_MEDIUM = new Font(Font.MONOSPACED,Font.BOLD,24);
    public static final Font LIST_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font SETTING_FONT = new Font(Font.MONOSPACED,Font.PLAIN,24);
    public static final Font HEADING_FONT = new Font("Arial",Font.BOLD,40);
    public static final Font HEADING1_FONT = new Font("Arial",Font.BOLD,24);

    public static final Color TRANSPARENT = new Color(0,0,0,0);

    public static final Insets NO_BOTTOM_INSET = new Insets(20,20,0,20);
    public static final Insets ALL_INSETS = new Insets(20,20,20,20);
    public static final Insets NO_RIGHT_INSET = new Insets(20,20,20,0);
    public static final Insets TOP_LEFT_INSETS = new Insets(20,20,0,0);
    
    private Settings() {
        screenW = 1600;
        screenH = 900;
        keyMappings = new HashMap<>();
    }
    
    public static Settings getSettings() {
        if(settings == null) {
            settings = new Settings();
        }
        return settings;
    }
    
    public int getScreenWidth() {
        return screenW;
    }
    
    public void setScreenWidth(int screenW) {
        this.screenW = screenW;
    }
    
    public int getScreenHeight() {
        return screenH;
    }
    
    public void setScreenHeight(int screenH) {
        this.screenH = screenH;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(0,0,screenW,screenH);
    }
    
    public void setKeyMappings(int characterState, KeyEvent[] keys) {
        keyMappings.put(characterState, keys);
    }
    
    public KeyEvent[] getKeyMappings(int characterState) {
        return keyMappings.get(characterState);
    }
}
