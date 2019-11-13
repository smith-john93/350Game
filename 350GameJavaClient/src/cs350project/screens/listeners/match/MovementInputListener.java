/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.listeners.match;

import cs350project.screens.listeners.MatchInputListener;

/**
 *
 * @author Mark Masone
 */
public interface MovementInputListener extends MatchInputListener {
    void startMoveLeft();
    void endMoveLeft();
    void startMoveRight();
    void endMoveRight();
    void startJump();
    void endJump();
    void startBlock();
    void endBlock();
    void startCrouch();
    void endCrouch();
}
