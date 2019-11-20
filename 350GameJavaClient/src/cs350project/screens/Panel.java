/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 * @param <E>
 */
public class Panel<E extends InputListener> extends JPanel {
    
    protected final ArrayList<E> inputListeners;
    
    public Panel() {
        inputListeners = new ArrayList<>();
    }
    
    public void addInputListener(E inputListener) {
        inputListeners.add(inputListener);
    }
}
