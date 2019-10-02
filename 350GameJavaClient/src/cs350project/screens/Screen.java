/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;
import cs350project.screens.keymaps.KeyMap;
import javax.swing.JComponent;
import java.util.ArrayList;
import cs350project.screens.panels.Panel;
import cs350project.screens.panels.PanelListener;

/**
 *
 * @author Mark Masone
 */
public abstract class Screen extends JComponent implements PanelListener {
    
    protected final ArrayList<ScreenListener> screenListeners;
    
    public Screen() {
        screenListeners = new ArrayList();
    }
    
    public void addScreenListener(ScreenListener sl) {
        screenListeners.add(sl);
    }
    
    public abstract KeyMap getKeyMap();
    public abstract Panel getPanel();
}
