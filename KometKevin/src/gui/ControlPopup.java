/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import engine2D.Config;
import gui.MainMenu.Controls;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 *
 * @author Neblis
 */
public class ControlPopup extends JPopupMenu{
    private int SCREEN_WIDTH = Config.getInstance().getScreenWidth();
    private int SCREEN_HEIGHT = Config.getInstance().getScreenHeight();
    private static final String CHANGE_TEXT = "Press a key...";
    private Controls control;

    public ControlPopup(Controls control) {
        super();
        this.setPopupSize(SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
        this.setLayout(new BorderLayout(100, 100));
        
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/10, SCREEN_HEIGHT/20);
        JLabel change = initMenuButton(CHANGE_TEXT, buttonSize);
        this.control = control;
        this.add(change, BorderLayout.CENTER);
    }
    
    
    private JLabel initMenuButton(String text, Dimension size){
        
        JLabel label = new JLabel(text);
        //Dimension dimension = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        //Color labelBackground = new Color(0, 0, 0, 220);
        
        label.setPreferredSize(size);
        label.setMaximumSize(size);
        label.setMinimumSize(size);
        
        label.setFont(MainMenu.FONT.deriveFont(MainMenu.FONT_SIZE/2)); // NOI18N
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        return label;
    }
}
