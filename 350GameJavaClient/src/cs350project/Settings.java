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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mark Masone
 */
public class Settings {
    
    private static Settings settings;
    private Dimension screenDimension;
    private HashMap<Integer, Integer> keyMappings;

    public static final Dimension[] SCREEN_DIMENSIONS = {
        new Dimension(1366,768),
        new Dimension(1600,900),
        new Dimension(1920,1080)
    };
    
    public static final String MENU_BACKGROUND_FILE = "/resources/menu/background.jpg";
    
    public static final Font BUTTON_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font BUTTON_FONT_MEDIUM = new Font(Font.MONOSPACED,Font.BOLD,24);
    public static final Font LIST_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font SETTING_FONT = new Font(Font.MONOSPACED,Font.PLAIN,24);
    public static final Font MENU_HEADING_FONT = new Font("Arial",Font.BOLD,40);
    public static final Font HEADING1_FONT = new Font("Arial",Font.BOLD,24);

    public static final Color TRANSPARENT = new Color(0,0,0,0);
    public static final Color MENU_BACKGROUND_COLOR = Color.black;
    public static final Color MENU_FOREGROUND_COLOR = Color.white;
    
    public static final Dimension MENU_BUTTON_DIMENSION = new Dimension(500,100);
    public static final Dimension MENU_TEXT_FIELD_DIMENSION = new Dimension(300,30);
    
    public static final Border MENU_BORDER = new LineBorder(Color.white, 5);

    public static final int MENU_PADDING = 15;
    public static final Insets INSETS_MENU_NO_TOP = new Insets(0,MENU_PADDING,MENU_PADDING,MENU_PADDING);
    public static final Insets INSETS_MENU_NO_BOTTOM = new Insets(MENU_PADDING,MENU_PADDING,0,MENU_PADDING);
    public static final Insets INSETS_MENU_NO_RIGHT = new Insets(MENU_PADDING,MENU_PADDING,MENU_PADDING,0);
    public static final Insets INSETS_MENU_TOP = new Insets(MENU_PADDING,0,0,0);
    public static final Insets INSETS_MENU_LEFT = new Insets(0,MENU_PADDING,0,0);
    public static final Insets INSETS_MENU_RIGHT = new Insets(0,0,0,MENU_PADDING);
    public static final Insets INSETS_MENU_TOP_LEFT = new Insets(MENU_PADDING,MENU_PADDING,0,0);
    public static final Insets INSETS_MENU_LEFT_BOTTOM = new Insets(0,MENU_PADDING,MENU_PADDING,0);
    public static final Insets INSETS_MENU_LEFT_RIGHT = new Insets(0,MENU_PADDING,0,MENU_PADDING);
    public static final Insets INSETS_MENU_ALL = new Insets(MENU_PADDING,MENU_PADDING,MENU_PADDING,MENU_PADDING);
    public static final Insets INSETS_NONE = new Insets(0,0,0,0);
    
    private Settings() {
        screenDimension = new Dimension(1366,768);
        keyMappings = new HashMap<>();
        keyMappings.put(KeyEvent.VK_W,CharacterState.JUMPING);
        keyMappings.put(KeyEvent.VK_D,CharacterState.MOVING_RIGHT);
        keyMappings.put(KeyEvent.VK_A,CharacterState.MOVING_LEFT);
        keyMappings.put(KeyEvent.VK_S,CharacterState.CROUCHING);
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
    
    public void setKeyMappings(HashMap<Integer, Integer> keyMappings) {
        this.keyMappings = keyMappings;
    }
    
    public HashMap<Integer, Integer> getKeyMappings() {
        return keyMappings;
    }
}
