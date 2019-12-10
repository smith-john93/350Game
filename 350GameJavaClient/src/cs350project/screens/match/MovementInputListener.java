/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

/**
 *
 * @author Mark Masone
 */
public interface MovementInputListener extends MatchInputListener {
    void startMovement(int stateCode);
    void endMovement(int stateCode);
}
