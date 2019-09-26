/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import cs350project.screens.SelectionScreen;

/**
 *
 * @author Mark Masone
 */
public class CS350Project {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.showScreen(new SelectionScreen());
    }
}
