/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.keymaps.KeyMap;
import javax.swing.JFrame;

/**
 *
 * @author Mark Masone
 */
public class GameFrame extends JFrame {
    
    private KeyMap keyMap;
    
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CS350 Project");
        setSize(1600, 900);
    }
    
    public void setKeyMap(KeyMap keyMap) {
        if(keyMap != null) {
            if (this.keyMap != null) {
                removeKeyListener(this.keyMap);
            }
            this.keyMap = keyMap;
            addKeyListener(keyMap);
        }
    }
}
