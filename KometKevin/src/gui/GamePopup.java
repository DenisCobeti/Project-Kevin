/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import engine2D.Config;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 *
 * @author Denis Florin Cobeti
 */
public abstract class GamePopup extends JPopupMenu{
    
    protected int SCREEN_WIDTH = Config.getInstance().getScreenWidth();
    protected int SCREEN_HEIGHT = Config.getInstance().getScreenHeight();

    public GamePopup() {
        super();
    }
    
    protected JLabel initMenuButton(String text, Dimension size, boolean hover){
        
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
        
        //Hover listeners
        if(hover){
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent evt) {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }
                @Override
                public void mouseExited(MouseEvent evt) {
                    label.setBackground(Color.BLACK);
                    label.setForeground(Color.WHITE);
                }
            });
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        return label;
    }
}
