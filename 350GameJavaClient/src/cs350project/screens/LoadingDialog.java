/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens;

import cs350project.GameFrame;
import cs350project.ImageResource;
import cs350project.Settings;
import cs350project.menu.MenuItemFactory;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Mark Masone
 */
public class LoadingDialog {
    
    private final JDialog jDialog;
    private final Thread dialogThread;
    private Image[] frames;
    private int frameIndex = 0;
    private final Timer animationTimer;
    private final int animationSize;
    private final int animationRate = 50;
    
    public LoadingDialog(String text) {
        GameFrame gameFrame = GameFrame.getInstance();
        jDialog = new JDialog(gameFrame,true);
        JLabel jLabel = MenuItemFactory.createHeadingLabel(text);
        FontMetrics fontMetrics = gameFrame.getGraphics().getFontMetrics(jLabel.getFont());
        animationSize = fontMetrics.getAscent();
        
        // Call pack before getInsets
        jDialog.pack(); 
        Insets insets = jDialog.getInsets();
        
        int width = fontMetrics.stringWidth(text) 
                + Settings.PADDING_MENU * 2 
                + animationSize
                + insets.left
                + insets.right;
        
        int height = animationSize 
                + Settings.PADDING_MENU * 2 
                + insets.top 
                + insets.bottom;
        
        jDialog.setSize(new Dimension(width,height));
        jDialog.setLocationRelativeTo(gameFrame);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("loading dialog closed");
            }
        });
        jDialog.setResizable(false);
        
        JPanel jPanel = new JPanel();
        jPanel.setBorder(Settings.BORDER_MENU);
        jPanel.setLayout(new FlowLayout());
        jPanel.setBackground(Settings.MENU_BACKGROUND_COLOR);
        
        JPanel animationPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2D = (Graphics2D)g;
                g2D.drawImage(frames[frameIndex++], 0, 0, this);
                if(frameIndex == frames.length) {
                    frameIndex = 0;
                }
            }
        };
        animationPanel.setPreferredSize(
                new Dimension(animationSize,animationSize)
        );
        
        animationTimer = new Timer(0,new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.repaint();
                
            }
        });
        animationTimer.setDelay(animationRate);
        
        try {
            ImageResource i = new ImageResource(
                    "menu/loading.gif",
                    ImageResource.Type.LOOPS,
                    animationSize,
                    animationSize
            );
            frames = i.getFrames();
        } catch (IOException ex) {
            MessageDialog.showErrorMessage("Unable to load animation", getClass());
        }
        
        jPanel.add(jLabel);
        jPanel.add(animationPanel);
        
        jDialog.setContentPane(jPanel);
        
        dialogThread = new Thread() {
            @Override
            public void run() {
                jDialog.setVisible(true);
            }
        };
    }
    
    public void open() {
        dialogThread.start();
        while(true) {
            if(jDialog.isActive()) {
                break;
            }
        }
        animationTimer.start();
    }
    
    public void close() {
        jDialog.setVisible(false);
    }
}
