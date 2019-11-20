/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.KeyMap;
import cs350project.screens.Screen;
import cs350project.screens.mainmenu.MainMenuScreen;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;

/**
 *
 * @author Mark Masone
 */
public class GameFrame extends JFrame {
    
    private static GameFrame gameFrame;
    
    private Screen screen;
    private KeyMap keyMap;

    private GameFrame() {

    }

    public static GameFrame getInstance() {
        if(gameFrame == null) {
            gameFrame = new GameFrame();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setTitle("CS350 Project");
            gameFrame.pack();
            Settings settings = Settings.getSettings();
            Dimension screenDimension = settings.getScreenDimension();
            gameFrame.setSize(screenDimension);
        }
        return gameFrame;
    }
    
    public void showScreen(Screen screen) {
        if(this.screen != null) {
            remove(this.screen);
        }
        setKeyMap(screen.getKeyMap());
        add(screen);
        setVisible(true);
        requestFocus();
        this.screen = screen;
    }
    
    @Override
    public void setSize(Dimension screenDimension) {
        Insets insets = getInsets();
        int windowW = screenDimension.width + insets.left + insets.right;
        int windowH = screenDimension.height + insets.top + insets.bottom;
        super.setSize(windowW, windowH);
    }
    
    private void setKeyMap(KeyMap keyMap) {
        if(keyMap != null) {
            if (this.keyMap != null) {
                removeKeyListener(this.keyMap);
            }
            this.keyMap = keyMap;
            addKeyListener(keyMap);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameFrame.getInstance().showScreen(new MainMenuScreen());
    }
}
