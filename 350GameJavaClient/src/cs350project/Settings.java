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
    private Dimension screenDimension;
    private final HashMap<Integer,KeyEvent[]> keyMappings;

    public static final Dimension[] SCREEN_DIMENSIONS = {
        new Dimension(1366,768),
        new Dimension(1600,900),
        new Dimension(1920,1080)
    };
    
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
        screenDimension = new Dimension(1366,768);
        keyMappings = new HashMap<>();
    }
    
    public static Settings getSettings() {
        if(settings == null) {
            settings = new Settings();
        }
        return settings;
    }
    
    public Dimension getScreenDimension() {
        return screenDimension;
    }
    
    public void setScreenDimension(Dimension screenDimension) {
        this.screenDimension = screenDimension;
    }
    
    public Rectangle getScreenBounds() {
        return new Rectangle(0,0,screenDimension.width,screenDimension.height);
    }
    
    public void setKeyMappings(int characterState, KeyEvent[] keys) {
        keyMappings.put(characterState, keys);
    }
    
    public KeyEvent[] getKeyMappings(int characterState) {
        return keyMappings.get(characterState);
    }
}
