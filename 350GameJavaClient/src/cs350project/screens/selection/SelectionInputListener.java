/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.selection;

import cs350project.screens.InputListener;

/**
 *
 * @author Mark Masone
 */
public interface SelectionInputListener extends InputListener {
    void characterSelected();
    void highlightNextRight();
    void highlightNextLeft();
    void highlightCharacter();
}
