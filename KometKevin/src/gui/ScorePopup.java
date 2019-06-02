/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import IO.Score;
import IO.ScoreManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Denis Florin Cobeti
 */
public class ScorePopup extends GamePopup {

    private static final String RESTART_TEXT = "Restart";
    private static final String EXIT_TEXT = "Exit game";
    private static final String EXIT_MENU = "Exit to menu";
    private static final String NAME_TEXT = "Enter your name";
    private static final String YOUR_SCORE= "Final score: ";
    
    public JTextField name;
    public JLabel restart;
    public JLabel exit;
    public JLabel exitMenu;
    
    private static final int SPACE_BETWEEN_BUTTONS = 10;
    private ScoreManager scoreManager;
    
    public ScorePopup(int score, ScoreManager scoreManager) {
        super();
        this.setPopupSize(SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
        this.setLayout(new BorderLayout(100, 100));
        this.scoreManager = scoreManager;
        Box box = Box.createHorizontalBox();
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/10, SCREEN_HEIGHT/20);
        
        JLabel scoreText = initMenuButton(YOUR_SCORE + score,  buttonSize, false);
        restart = initMenuButton(RESTART_TEXT,  buttonSize, true);
        exit = initMenuButton(EXIT_TEXT,  buttonSize, true);
        exitMenu = initMenuButton(EXIT_MENU,  buttonSize, true);
        
        name = initNameField(NAME_TEXT);
        
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                //menuSelect.play();
                scoreManager.addScore(name.getText(), score);
                System.exit(0);
            }
        });
        
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        box.add(restart);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        box.add(exit);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        box.add(exitMenu);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_BUTTONS));
        
        this.add(box, BorderLayout.SOUTH);
        this.add(scoreText, BorderLayout.NORTH);
        this.add(name, BorderLayout.CENTER);
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
