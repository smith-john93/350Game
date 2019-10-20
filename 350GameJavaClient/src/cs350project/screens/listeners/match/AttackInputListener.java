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
public interface AttackInputListener extends MatchInputListener {
    void punch();
    void kick();
    void block();
    void crouch();
}
