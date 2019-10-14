/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.menu;

import cs350project.Settings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Mark Masone
 */
public class Menu extends JComponent {
    
    private final ArrayList<MenuMouseListener> menuMouseListeners;
    private int menuW;
    private int menuH;
    private final int menuPadding = 20;
    private final int menuSpacing = 10;
    private final float borderWidth = 6f;
    private final Color borderColor = Color.WHITE;
    private final Color backgroundColor = Color.BLACK;
    private final int fieldWidth = 300;
    private final int fieldHeight = 30;
    private final int buttonWidth = 500;
    private final int buttonHeight = 100;
    private final Font buttonFont;
    
    public Menu() {
        menuMouseListeners = new ArrayList<>();
        buttonFont = new Font(Font.MONOSPACED,Font.BOLD,32);
    }
    
    public void addMenuMouseListener(MenuMouseListener menuMouseListener) {
        menuMouseListeners.add(menuMouseListener);
    }
    
    public void addField(JComponent field) {
        field.setSize(fieldWidth, fieldHeight);
        add(field);
    }
    
    public void addButton(JButton button) {
        button.setSize(buttonWidth, buttonHeight);
        button.setFont(buttonFont);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (MenuMouseListener menuMouseListener : menuMouseListeners) {
                    menuMouseListener.buttonClicked(button);
                }
            }
        });
        add(button);
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        Settings settings = Settings.getSettings();
        int screenW = settings.getScreenWidth();
        int screenH = settings.getScreenHeight();
        int w = 0;
        int h = 0;
        Component[] components = this.getComponents();
        for(Component component : components) {
            int componentW = component.getWidth();
            if(componentW > w) {
                w = componentW;
            }
        }
        int componentY = menuPadding;
        menuW = w + menuPadding * 2;
        for(Component component : components) {
            int jComponentW = component.getWidth();
            int jComponentH = component.getHeight();
            int jComponentX = menuW / 2 - jComponentW / 2;
            component.setBounds(jComponentX,componentY,jComponentW,jComponentH);
            componentY += jComponentH + menuSpacing;
            if(h != 0) {
                h += menuSpacing;
            }
            h += jComponentH;
        }
        menuH = h + menuPadding * 2;
        int menuX = screenW / 2 - menuW / 2;
        int menuY = screenH / 2 - menuH / 2;
        setBounds(menuX,menuY,menuW,menuH);
        setLayout(null);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(backgroundColor);
        g2d.fillRect(0,0,menuW,menuH);
        float offset = borderWidth / 2;
        int trim = (int)(offset * 2);
        Stroke defaultStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(borderWidth));
        g2d.setColor(borderColor);
        g2d.drawRect((int)offset,(int)offset,menuW - trim,menuH - trim);
        g2d.setStroke(defaultStroke);
    }
}
