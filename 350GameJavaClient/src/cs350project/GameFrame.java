/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.keymaps.KeyMap;
import java.awt.Insets;
import javax.swing.JFrame;

/**
 *
 * @author Mark Masone
 */
public class GameFrame extends JFrame {
    
    private KeyMap keyMap;

    private static GameFrame gameFrame;

    private GameFrame() {

    }

    public static GameFrame getInstance() {
        if(gameFrame == null) {
            gameFrame = new GameFrame();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setTitle("CS350 Project");
            gameFrame.pack();
            Insets insets = gameFrame.getInsets();
            Settings settings = Settings.getSettings();
            int screenW = settings.getScreenWidth();
            int screenH = settings.getScreenHeight();
            int windowW = screenW + insets.left + insets.right;
            int windowH = screenH + insets.top + insets.bottom;
            System.out.println("windowW: " + windowW);
            System.out.println("windowH: " + windowH);
            gameFrame.setSize(windowW, windowH);
        }
        return gameFrame;
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
