/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Neblis
 */
public class ControlPopup extends GamePopup{
    private static final String CHANGE_TEXT = "Press a key...";
    

    public ControlPopup() {
        super();
        this.setPopupSize(SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
        this.setLayout(new BorderLayout(100, 100));
        
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/10, SCREEN_HEIGHT/20);
        JLabel change = initMenuButton(CHANGE_TEXT, buttonSize, false);
        this.add(change, BorderLayout.CENTER);
        
    }
}
