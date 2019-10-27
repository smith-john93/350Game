/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;
import cs350project.screens.listeners.ScreenListener;
import cs350project.Settings;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.Panel;
import javax.swing.JComponent;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 */
public abstract class Screen extends JComponent {
    
    private final ArrayList<ScreenListener> screenListeners;
    
    public Screen() {
        screenListeners = new ArrayList<>();
    }
    
    public void addScreenListener(ScreenListener screenListener) {
        screenListeners.add(screenListener);
    }
    
    public void addPanel(Panel panel) {
        Settings settings = Settings.getSettings();
        int screenW = settings.getScreenWidth();
        int screenH = settings.getScreenHeight();
        panel.setBounds(0, 0, screenW, screenH);
        panel.setLayout(null);
        add(panel);
    }
    
    protected void showScreen(Screen screen) {
        for(ScreenListener screenListener : screenListeners) {
            screenListener.showScreen(screen);
        }
    }
    
    public abstract KeyMap getKeyMap();
    public abstract void showPanel();
}
