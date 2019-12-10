/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.lobby;

import cs350project.menu.BackButtonListener;

/**
 *
 * @author Mark Masone
 */
public interface LobbyInputListener extends BackButtonListener {
    void createMatch(String matchName);
    void joinMatch(String matchName);
    void settings();
}
