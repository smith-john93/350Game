/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.title;

import cs350project.ImageResource;
import cs350project.Settings;
import cs350project.menu.MenuItemFactory;
import cs350project.screens.MessageDialog;
import cs350project.screens.Panel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Mark Masone
 */
public class TitlePanel extends Panel<TitleInputListener> {

    private int frameIndex = 0;
    private Image[] frames;
    private Image titleImage;
    private final Timer animationTimer;
    
    public TitlePanel(String continueKeyText) {
        
        JLabel startLabel = MenuItemFactory.createLabel(
                "Press " + continueKeyText + " or click to start."
        );
        
        animationTimer = new Timer(0,new ActionListener(){
            
            private boolean colorToggle = false;
            private final Color foregroundColor = startLabel.getForeground();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(frames != null && frameIndex < frames.length - 1) {
                    frameIndex++;
                } else if(frames != null) {
                    frames = null;
                    JPanel startLabelPanel = new JPanel();
                    double screenHeight = Settings.getSettings().getScreenDimension().height;
                    Dimension startLabelPanelSize = new Dimension(
                            0,
                            (int)(screenHeight * 0.45)
                    );
                    startLabelPanel.setPreferredSize(startLabelPanelSize);
                    startLabelPanel.setBackground(Settings.TRANSPARENT);
                    startLabelPanel.add(startLabel);
                    add(startLabelPanel, BorderLayout.PAGE_END);
                    animationTimer.setDelay(500);
                    updateUI();
                } else {
                    if(colorToggle) {
                        startLabel.setForeground(Color.red);
                    } else {
                        startLabel.setForeground(foregroundColor);
                    }
                    colorToggle = !colorToggle;
                }
                repaint();
            }  
        });
        animationTimer.setDelay(50);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        setLayout(new BorderLayout());
        setBackground(Settings.TRANSPARENT);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("click");
                for(TitleInputListener titleInputListener : inputListeners) {
                    titleInputListener.start();
                }
            }
        });
        
        ImageResource gameResource;
        Rectangle bounds = getBounds();
        
        try {
            gameResource = new ImageResource(
                    Settings.TITLE_IMAGE_FILE,
                    ImageResource.Type.STILL,
                    bounds.width,
                    bounds.height
            );
            titleImage = gameResource.getFrames()[0];
        } catch (IOException e) {
            MessageDialog.showErrorMessage("Unable to load title animation.", getClass());
        }
        
        try {
            gameResource = new ImageResource(
                    Settings.TITLE_ANIMATION_FILE,
                    ImageResource.Type.PLAYS_ONCE,
                    bounds.width,
                    bounds.height
            );
            frames = gameResource.getFrames();
            animationTimer.start();
        } catch (IOException e) {
            MessageDialog.showErrorMessage("Unable to load title animation.", getClass());
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        if(frames != null) {
            g2D.drawImage(frames[frameIndex], 0, 0, this);
        } else if(titleImage != null) {
            g2D.drawImage(titleImage,0,0,this);
        }
    }
}
