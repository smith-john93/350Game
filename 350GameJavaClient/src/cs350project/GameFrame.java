/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.ScreenListener;
import cs350project.screens.Screen;
import javax.swing.JFrame;
import cs350project.screens.keymaps.KeyMap;
import cs350project.screens.panels.Panel;

/**
 *
 * @author Mark Masone
 */
public class GameFrame extends JFrame implements ScreenListener {
    
    private Screen screen;
    private KeyMap keyMap;
    
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CS350 Project");
        setSize(1600, 900);
    }
    
    private void setKeyMap(KeyMap keyMap) {
        if(this.keyMap != null) {
            removeKeyListener(this.keyMap);
        }
        this.keyMap = keyMap;
        addKeyListener(keyMap);
    }
    
    @Override
    public void showScreen(Screen screen) {
        if(this.screen != null) {
            remove(this.screen);
        }
        screen.addScreenListener(this);
        Panel panel = screen.getPanel();
        panel.addPanelListener(screen);
        setKeyMap(screen.getKeyMap());
        add(screen);
        setVisible(true);
        this.screen = screen;
    }
}
