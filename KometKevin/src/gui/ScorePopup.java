/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import engine2D.Config;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Denis Florin Cobeti
 */
public class ScorePopup extends JPopupMenu {

    private static final String SUBMIT_TEXT = "Submit score";
    private static final String EXIT_TEXT = "Exit game";
    private static final String EXIT_MENU = "Exit to menu";
    private static final String NAME_TEXT = "Enter your name";
    private static final String YOUR_SCORE= "Final score: ";
    
    private int SCREEN_WIDTH = Config.getInstance().getScreenWidth();
    private int SCREEN_HEIGHT = Config.getInstance().getScreenHeight();
    
    public JTextField name;
    public JLabel submit;
    public JLabel exit;
    public JLabel exitMenu;
    
    private static final int SPACE_BETWEEN_BUTTONS = 10;
    
    public ScorePopup(String label, int score) {
        super(label);
        this.setPopupSize(SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
        this.setLayout(new BorderLayout(100, 100));
        
        Box box = Box.createHorizontalBox();
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/10, SCREEN_HEIGHT/20);
        
        JLabel scoreText = initMenuButton(YOUR_SCORE + score,  buttonSize, false);
        submit = initMenuButton(SUBMIT_TEXT,  buttonSize, true);
        exit = initMenuButton(EXIT_TEXT,  buttonSize, true);
        exitMenu = initMenuButton(EXIT_MENU,  buttonSize, true);
        
        name = initNameField(NAME_TEXT);
        
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        box.add(submit);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        box.add(exit);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        box.add(exitMenu);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        
        this.add(box, BorderLayout.SOUTH);
        this.add(scoreText, BorderLayout.NORTH);
        this.add(name, BorderLayout.CENTER);
    }
    
    private JLabel initMenuButton(String text, Dimension size, boolean hover){
        
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
    
    private JTextField initNameField(String text){
        
        JTextField label = new JTextField(text);
        //Dimension dimension = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        //Color labelBackground = new Color(0, 0, 0, 220);
        /*
        label.setPreferredSize(size);
        label.setMaximumSize(size);
        label.setMinimumSize(size);
        */
        label.setFont(MainMenu.FONT.deriveFont(MainMenu.FONT_SIZE/2)); // NOI18N
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        return label;
    }
}