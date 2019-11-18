/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import javax.swing.JOptionPane;

/**
 *
 * @author Mark Masone
 */
public abstract class MessageDialog {
    public static void showErrorMessage(String message, Class c) {
        JOptionPane.showMessageDialog(
                null, 
                message,
                c.getSimpleName(), 
                JOptionPane.ERROR_MESSAGE
        );
    }
}
