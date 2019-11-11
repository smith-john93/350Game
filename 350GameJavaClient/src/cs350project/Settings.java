/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;

import java.awt.Font;
import java.awt.Rectangle;

/**
 *
 * @author Mark Masone
 */
public class Settings {
    
    private static Settings settings;
    private int screenW;
    private int screenH;
    public static final Font BUTTON_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font LIST_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font HEADING_FONT = new Font("Arial",Font.BOLD,40);
    
    private Settings() {
        screenW = 1600;
        screenH = 900;
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
}
