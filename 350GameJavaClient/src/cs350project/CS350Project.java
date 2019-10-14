/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.MainMenuScreen;
import cs350project.screens.Screen;
import cs350project.screens.ScreenListener;
import cs350project.screens.SelectionScreen;
import cs350project.screens.keymaps.KeyMap;

/**
 *
 * @author Mark Masone
 */
public class CS350Project implements ScreenListener {
 
    private Screen screen;
    private final GameFrame gameFrame;
    
    public CS350Project() {
        gameFrame = new GameFrame();
    }
    
    @Override
    public void showScreen(Screen screen) {
        if(this.screen != null) {
            gameFrame.remove(this.screen);
        }
        screen.addScreenListener(this);
        KeyMap keyMap = screen.getKeyMap();
        gameFrame.setKeyMap(keyMap);
        gameFrame.add(screen);
        gameFrame.setVisible(true);
        gameFrame.requestFocus();
        screen.showPanel();
        this.screen = screen;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CS350Project cs350Project = new CS350Project();
        cs350Project.showScreen(new MainMenuScreen());
    }
}
