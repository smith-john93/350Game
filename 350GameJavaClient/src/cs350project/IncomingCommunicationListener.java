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
public interface IncomingCommunicationListener {
    void updatePlayerCharacter(short objectID, int stateCode, int x, int y);
}
