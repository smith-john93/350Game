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
public interface KeyboardInputListener {
    public void messageKeyPressed();
    public void messageCommit();
    public void keyTyped(char value);
}
