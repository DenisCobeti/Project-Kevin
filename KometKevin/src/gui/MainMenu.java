package gui;

import audio.SoundClip;
import engine2D.Window;
import engine2D.Config;
import game.objects.Ships;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


/**
 *
 * @author Project Kevin
 */
public class MainMenu extends JPanel {

    private final String START_TEXT = "START"; 
    private final String OPTIONS_TEXT = "CONTROLS"; 
    private final String SCORES_TEXT = "SCORES";
    private final String EXIT_TEXT = "EXIT";
    
    private final String NEXT_SHIP = ">";
    private final String PREVIOUS_SHIP = "<";
    
    private static final Font FONT = Config.getInstance().getFont();
    
    private static final int SCREEN_HEIGHT = Config.getInstance().getScreenHeight();
    private static final int SCREEN_WIDTH = Config.getInstance().getScreenWidth();
    
    private static final int SPACE_BETWEEN_MENUS = SCREEN_HEIGHT/50;
    private static final int MENU_TOP_SPACE = SCREEN_HEIGHT/3;
    // hay que cambiarlo para qeu sea relativo a la resolucion.
    private static final float FONT_SIZE = 50.0f;
    
    private static final String BG = "resources/menu/background.png";
    private static final String menuSound = "menu/menuHover.ogg";
    private SoundClip menuSelect;
    
    private Window window; 
    private JLabel start, options, scores, exit, startSelect, next, previous, ship;
    
    private  Image[] shipIcons;
    private int shipsIterator;
    
    /**
     * Creates new form MainMenu
     */
   
    public MainMenu(Window window)  {
        this.window = window;
        
        shipIcons = new Image[Ships.values().length];
        
        //Estos valoren deberan cambian en getScaledInstance, ya que no son genericos,
        for (int i = 0; i < shipIcons.length; i++){
            try {
                shipIcons[i] = (ImageIO.read(new File(Ships.values()[i].getSprite()))
                        .getSubimage(0, 0, Ships.values()[i].getSizeX(), Ships.values()[i].getSizeY())
                        .getScaledInstance(Ships.values()[i].getSizeX()*3, 
                                            Ships.values()[i].getSizeY()*3, 
                                            Image.SCALE_AREA_AVERAGING));
            } catch (IOException ex) {}
        }
        shipsIterator = 0;
        try {
            menuSelect = new SoundClip(menuSound);
        } catch (IOException ex) {}
        
        initMenu();
    }                     

    private void initMenu() {
 
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        
        start = initMenuButton(START_TEXT, buttonSize);
        startSelect = initMenuButton(START_TEXT, buttonSize);
        options = initMenuButton(OPTIONS_TEXT, buttonSize);
        scores = initMenuButton(SCORES_TEXT, buttonSize);
        exit = initMenuButton(EXIT_TEXT, buttonSize);
        
        Dimension selectSize = new Dimension(SCREEN_WIDTH/20, SCREEN_HEIGHT/20);
        
        next = initMenuButton(NEXT_SHIP, selectSize);
        previous = initMenuButton(PREVIOUS_SHIP, selectSize);
        
        //Mouse Listeners de cada elemento mdel menu al hacer click
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                menuSelect.play();
                startLabelMouseClicked(evt);
            }
        });
        
        startSelect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                menuSelect.play();
                startSelectLabelMouseClicked(evt);
            }
        });
        
        options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                menuSelect.play();
                optionsLabelMouseClicked(evt);
            }
        });
        
        scores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                menuSelect.play();
                scoresLabelMouseClicked(evt);
            }
        });
        
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                menuSelect.play();
                exitLabelMouseClicked(evt);
            }
        });
        
        next.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                //menuSelect.play();
                nextLabelMouseClicked(evt);
            }
        });
        
        previous.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                //menuSelect.play();
                previousLabelMouseClicked(evt);
            }
        });
        
        
        Box box = Box.createVerticalBox();
        
        //elments of the initial menu and spaces between the buttons
        box.add(Box.createVerticalStrut(MENU_TOP_SPACE));
        box.add(start);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        box.add(options);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        box.add(scores);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS * 3));
        box.add(exit);
        
        this.setLayout(new BorderLayout(100, 100));
        this.add(box, BorderLayout.EAST);
    }
    
    private void startLabelMouseClicked(MouseEvent evt) {
        setVisibleMenu(false);
        
        Box box = Box.createHorizontalBox();
        JPanel panel = new JPanel();
        JPanel panelShips = new JPanel();
        Dimension shipSize = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/2);
        Dimension shipSizeIcon = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/2);
        
        ship = new JLabel(new ImageIcon(shipIcons[shipsIterator]));
        ship.setOpaque(false);
        ship.setVisible(true);
        
        panel.setBackground(new Color(0,0,0,0));
        panel.setPreferredSize(shipSize);
        panel.setMaximumSize(shipSize);
        panel.setMinimumSize(shipSize);
        panel.setLayout(new BorderLayout(100,100));
        
        panelShips.setBackground(new Color(0,0,0,255));
        
        panelShips.setPreferredSize(shipSizeIcon);
        panelShips.setMaximumSize(shipSizeIcon);
        panelShips.setMinimumSize(shipSizeIcon);
        panelShips.setLayout(new BorderLayout(0,0));
        
        panel.add(startSelect, BorderLayout.SOUTH);
        panelShips.add(ship, BorderLayout.NORTH);
        panel.add(panelShips, BorderLayout.NORTH);
        
        box.add(Box.createHorizontalStrut(MENU_TOP_SPACE));
        box.add(previous);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_MENUS));
        box.add(panel);
        box.add(Box.createHorizontalStrut(SPACE_BETWEEN_MENUS));
        box.add(next);
        
        this.add(box, BorderLayout.CENTER);
    }
    
    private void startSelectLabelMouseClicked(MouseEvent evt) {
        window.getCardLayout().next(window.getCards());
        window.getCanvas().requestFocus();
        window.getMenuClip().stop();
        window.getGameClip().loop();
        window.getGameContainer().resume();
    }
    
    private void nextLabelMouseClicked(MouseEvent evt) {
        shipsIterator = (++shipsIterator) % shipIcons.length;
        ship.setIcon(new ImageIcon(shipIcons[shipsIterator]));
                    
    } 
    
    private void previousLabelMouseClicked(MouseEvent evt) {
        shipsIterator = (--shipsIterator) % shipIcons.length;
        if(shipsIterator == -1) shipsIterator = shipIcons.length - 1;
        ship.setIcon(new ImageIcon(shipIcons[shipsIterator]));
    } 
    
    private void optionsLabelMouseClicked(MouseEvent evt) {
        setVisibleMenu(false);
        Dimension size = new Dimension(SCREEN_HEIGHT, SCREEN_WIDTH/3);
        Dimension sizeItem = new Dimension(SCREEN_HEIGHT/10, SCREEN_WIDTH/3);
        
        JMenu optionsMenu = new JMenu("Controls");
        JPanel panel = new JPanel();
        
        optionsMenu.setPreferredSize(size);
        optionsMenu.setMaximumSize(size);
        optionsMenu.setMinimumSize(size);
        
        optionsMenu.add(initOptionsControlMenu("Up", sizeItem));
        optionsMenu.add(initOptionsControlMenu("Up", sizeItem));
        optionsMenu.add(initOptionsControlMenu("Up", sizeItem));
        optionsMenu.add(initOptionsControlMenu("Up", sizeItem));
        
        optionsMenu.setVisible(true);
        this.setLayout(new BorderLayout(100,100));
        this.add(optionsMenu, BorderLayout.EAST);
    } 
    private void scoresLabelMouseClicked(MouseEvent evt) {                                        
        
    }
    private void exitLabelMouseClicked(MouseEvent evt) {
        window.dispose();
        System.exit(0);
    }
   
    private JLabel initMenuButton(String text, Dimension size){
        
        JLabel label = new JLabel(text);
        //Dimension dimension = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        //Color labelBackground = new Color(0, 0, 0, 220);
        
        label.setPreferredSize(size);
        label.setMaximumSize(size);
        label.setMinimumSize(size);
        
        label.setFont(MainMenu.FONT.deriveFont(FONT_SIZE)); // NOI18N
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        
        //Hover listeners
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
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        return label;
    }
    
    private JMenuItem initOptionsControlMenu(String text, Dimension size){
        
        JMenuItem item = new JMenuItem(text);
        
        item.setPreferredSize(size);
        item.setMaximumSize(size);
        item.setMinimumSize(size);
        
        item.setFont(MainMenu.FONT.deriveFont(FONT_SIZE)); // NOI18N
        item.setForeground(Color.WHITE);
        item.setOpaque(true);
        item.setBackground(Color.BLACK);
        
        //Hover listeners
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                item.setBackground(Color.WHITE);
                item.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                item.setBackground(Color.BLACK);
                item.setForeground(Color.WHITE);
            }
        });
        
        return item;
    }
    
    private void setVisibleMenu(Boolean visible){
        start.setVisible(visible);
        options.setVisible(visible);
        scores.setVisible(visible);
        exit.setVisible(visible);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        //paint the background image
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File(BG)).getScaledInstance
                             (SCREEN_WIDTH, SCREEN_HEIGHT, 1), 0, 0, null);
        } catch (IOException ex) {
            //mejor tratamiento de error
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                 
}
