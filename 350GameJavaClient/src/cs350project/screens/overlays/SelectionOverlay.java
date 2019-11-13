package cs350project.screens.overlays;

import cs350project.Settings;
import cs350project.screens.listeners.SelectionInputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class SelectionOverlay extends JComponent {

    private final ArrayList<SelectionInputListener> selectionInputListeners;
    private final Rectangle[] characterBorders;
    private int selectedCharacter;

    public SelectionOverlay(Rectangle[] characterBorders) {
        selectionInputListeners = new ArrayList<>();
        Settings settings = Settings.getSettings();
        int screenW = settings.getScreenWidth();
        int screenH = settings.getScreenHeight();
        setBounds(0,0,screenW,screenH);
        selectedCharacter = 0;
        this.characterBorders = characterBorders;
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                for(int i = 0; i < characterBorders.length; i++) {
                    if(characterBorders[i].contains(e.getPoint())) {
                        selectedCharacter = i;
                        repaint();
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Rectangle characterBorder : characterBorders) {
                    if (characterBorder.contains(e.getPoint())) {
                        for(SelectionInputListener selectionInputListener : selectionInputListeners) {
                            selectionInputListener.characterSelected();
                        }
                    }
                }
            }
        });
    }

    public void addSelectionInputListener(SelectionInputListener selectionInputListener) {
        selectionInputListeners.add(selectionInputListener);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        for(int i = 0; i < characterBorders.length; i++) {
            if(i == selectedCharacter)
                g2d.setColor(Color.red);
            else
                g2d.setColor(Color.white);
            g2d.draw(characterBorders[i]);
        }
    }
    
    public int getSelectedCharacter() {
        return selectedCharacter;
    }

    public void highlightNextRight() {
        if(selectedCharacter < characterBorders.length - 1) {
            selectedCharacter++;
            repaint();
        }
    }

    public void highlightNextLeft() {
        if(selectedCharacter > 0) {
            selectedCharacter--;
            repaint();
        }
    }
}
