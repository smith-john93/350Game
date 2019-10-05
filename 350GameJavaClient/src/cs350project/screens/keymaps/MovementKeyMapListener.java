/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;

/**
 *
 * @author Mark Masone
 */
public interface MovementKeyMapListener extends MatchKeyMapListener {
    public void startMoveLeft();
    public void endMoveLeft();
    public void startMoveRight();
    public void endMoveRight();
    public void startJump();
    public void endJump();
}
