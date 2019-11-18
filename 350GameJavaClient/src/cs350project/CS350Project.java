/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.mainmenu.MainMenuScreen;
import cs350project.screens.Screen;
import cs350project.screens.KeyMap;

/**
 *
 * @author Mark Masone
 */
public class CS350Project {
 
    private static Screen screen;

    public static void showScreen(Screen screen) {
        GameFrame gameFrame = GameFrame.getInstance();
        if(CS350Project.screen != null) {
            gameFrame.remove(CS350Project.screen);
        }
        KeyMap keyMap = screen.getKeyMap();
        gameFrame.setKeyMap(keyMap);
        gameFrame.add(screen);
        gameFrame.setVisible(true);
        gameFrame.requestFocus();
        CS350Project.screen = screen;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CS350Project.showScreen(new MainMenuScreen());
    }
}
