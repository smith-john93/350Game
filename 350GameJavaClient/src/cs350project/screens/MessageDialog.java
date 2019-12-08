/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Mark Masone
 */
public abstract class MessageDialog {
    
    public static void showInfoMessage(Component component, String message, Class c) {
        JOptionPane.showMessageDialog(
                component,
                message, 
                c.getSimpleName(), 
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public static void showErrorMessage(String message, Class c) {
        showErrorMessage(null,message,c,false);
    }
    
    public static void showErrorMessage(Component component, String message, Class c) {
        JOptionPane.showMessageDialog(
                component, 
                message,
                c.getSimpleName(), 
                JOptionPane.ERROR_MESSAGE
        );
    }
    
    public static void showErrorMessage(Component component, String message, Class c, boolean modal) {
        if(modal) {
            showErrorMessage(component,message,c);
        } else {
            new Thread(){
                @Override
                public void run() {
                    showErrorMessage(component,message,c);
                }
            }.start();
        }
    }
}
