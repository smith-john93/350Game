/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JComponent;

/**
 *
 * @author Mark Masone
 */
public abstract class MatchObject extends JComponent {
    public abstract void receiveData(DataInputStream dataInputStream) throws IOException;
}
