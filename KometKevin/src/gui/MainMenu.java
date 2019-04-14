package gui;

import engine2D.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JLabel;
import engine2D.Config;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingConstants;

/**
 *
 * @author Project Kevin
 */
public class MainMenu extends javax.swing.JPanel {

    private final String START_TEXT = "START"; 
    private final String OPTIONS_TEXT = "OPTIONS"; 
    private final String SCORES_TEXT = "SCORES";
    private final String EXIT_TEXT = "EXIT";
    
    private static final Font FONT = Config.getInstance().getFont();
    
    private static final int SCREEN_HEIGHT = Config.getInstance().getScreenHeight();
    private static final int SCREEN_WIDTH = Config.getInstance().getScreenWidth();
    
    private static final int SPACE_BETWEEN_MENUS = SCREEN_HEIGHT/50;
    private static final int MENU_TOP_SPACE = SCREEN_HEIGHT/3;
    private static final float FONT_SIZE = 50.0f;
    
    private static final String BG = "./resources/menu/background.png";
    
    private Window window;
    
    private JLabel start, options, scores, exit; 

    /**
     * Creates new form MainMenu
     */
    public MainMenu(Window window) {
        this.window = window;
        initMenu();
    }                     

    private void initMenu() {
        
        
        start = initMenuButton(START_TEXT);
        options = initMenuButton(OPTIONS_TEXT);
        scores = initMenuButton(SCORES_TEXT);
        exit = initMenuButton(EXIT_TEXT);
        
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                startLabelMouseClicked(evt);
            }
        });
        
        options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                optionsLabelMouseClicked(evt);
            }
        });
        
        scores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                scoresLabelMouseClicked(evt);
            }
        });
        
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                exitLabelMouseClicked(evt);
            }
        });
        
        Box box = Box.createVerticalBox();
        
        box.add(Box.createRigidArea(new Dimension(0,MENU_TOP_SPACE)));
        box.add(start);
        box.add(Box.createRigidArea(new Dimension(0,SPACE_BETWEEN_MENUS)));
        box.add(options);
        box.add(Box.createRigidArea(new Dimension(0,SPACE_BETWEEN_MENUS)));
        box.add(scores);
        box.add(Box.createRigidArea(new Dimension(0,SPACE_BETWEEN_MENUS * 3)));
        box.add(exit);
        
        this.setLayout(new BorderLayout(100,100));
        this.add(box, BorderLayout.EAST);
    }
    
    private void startLabelMouseClicked(MouseEvent evt) {//GEN-FIRST:event_startLabelMouseClicked
        window.getCardLayout().next(window.getCards());
        window.getGameContainer().resume();
    }
    
    private void optionsLabelMouseClicked(MouseEvent evt) {                                        
        
    } 
    private void scoresLabelMouseClicked(MouseEvent evt) {                                        
        
    }
    private void exitLabelMouseClicked(MouseEvent evt) {
        window.dispose();
        System.exit(0);
    }
   
    private JLabel initMenuButton(String text){
        
        JLabel label = new javax.swing.JLabel(text);
        Dimension dimension = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        Color labelBackground = new Color(0, 0, 0, 220);
        
        label.setPreferredSize(dimension);
        label.setMaximumSize(dimension);
        label.setMinimumSize(dimension);
        
        label.setFont(MainMenu.FONT.deriveFont(FONT_SIZE)); // NOI18N
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(labelBackground);
        
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                label.setBackground(labelBackground);
                label.setForeground(Color.WHITE);
            }
        });
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File(BG)).getScaledInstance
                             (SCREEN_WIDTH, SCREEN_HEIGHT, 1), 0, 0, null);
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                 
}
