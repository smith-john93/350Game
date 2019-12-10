/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;

import cs350project.screens.MessageDialog;
import java.awt.Component;

/**
 *
 * @author Mark Masone
 */
public class Validate {
    
    public static final int MAX_CHARS_LENGTH = 255;
    
    public static boolean chars(Component component, String fieldName, char[] chars) {
        Class c = Validate.class;
        if(chars.length > 0 && chars.length < MAX_CHARS_LENGTH) {
            return true;
        } else if(chars.length > MAX_CHARS_LENGTH) {
            MessageDialog.showErrorMessage(
                    component, 
                    fieldName + " cannot contain more than " + MAX_CHARS_LENGTH + " characters.", 
                    c, 
                    false
            );
        } else if(chars.length == 0) {
            MessageDialog.showErrorMessage(
                    component, 
                    fieldName + " cannot be empty.", 
                    c, 
                    false
            );
        }
        return false;
    }
}
