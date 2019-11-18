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
        // This has to go away for now. #pout
        // Don't call it from addNotify in the future.
        /*JOptionPane.showMessageDialog(
                null, 
                message,
                c.getSimpleName(), 
                JOptionPane.ERROR_MESSAGE
        );*/
        // This will do for now.
        System.err.println(c.getSimpleName() + ": " + message);
    }
}
