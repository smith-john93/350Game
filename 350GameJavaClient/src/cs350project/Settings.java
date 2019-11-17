/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;

/**
 *
 * @author Mark Masone
 */
public class Settings {
    
    private static Settings settings;
    private int screenW;
    private int screenH;
    
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
}
