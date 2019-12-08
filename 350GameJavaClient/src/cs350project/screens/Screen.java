/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.Settings;
import cs350project.communication.CommunicationException;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Mark Masone
 */
public abstract class Screen extends JComponent {
    
    protected abstract class Loader {
        protected abstract void load() throws CommunicationException;
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        setLayout(null);
        Settings settings = Settings.getSettings();
        Rectangle bounds = settings.getScreenBounds();
        
        JPanel jPanel = getJPanel();
        jPanel.setBounds(bounds);
        
        add(jPanel);
        
        BackgroundImage backgroundImage = getBackgroundImage();
        if(backgroundImage != null) {
            backgroundImage.setBounds(bounds);
            add(backgroundImage);
        }
    }
    
    public void loadingDialog(Loader loader, String message) {
        JComponent jComponent = this;
        new Thread() {
            @Override
            public void run() {
                LoadingDialog loadingDialog = new LoadingDialog(message);
                loadingDialog.open();
                
                try {
                    loader.load();
                    loadingDialog.close();
                } catch (CommunicationException ex) {
                    loadingDialog.close();
                    MessageDialog.showErrorMessage(jComponent,ex.getMessage(),getClass(),false);
                }
            }
        }.start();
    }
    
    public abstract BackgroundImage getBackgroundImage();
    public abstract JPanel getJPanel();
    public abstract KeyMap getKeyMap();
}
