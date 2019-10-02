/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.panels;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 */
public class Panel extends JPanel {
    
    protected final ArrayList<PanelListener> panelListeners;
    
    public Panel() {
        panelListeners = new ArrayList();
    }
    
    public void addPanelListener(PanelListener panelListener) {
        panelListeners.add(panelListener);
    }
}
