package gui;

import audio.SoundClip;
import engine2D.Window;
import engine2D.Config;
import game.GameManager;
import game.objects.Player;
import game.objects.ships.ShipFactory;
import game.objects.ships.Ships;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;


/**
 *
 * @author Project Kevin
 */
public class MainMenu extends JPanel {

    private final String NEW_GAME_TEXT = "NEW GAME"; 
    private final String OPTIONS_TEXT = "CONTROLS"; 
    private final String SCORES_TEXT = "SCORES";
    private final String EXIT_TEXT = "EXIT";
    
    private final String CONTROL_UP = "Up:  ";
    private final String CONTROL_DOWN = "Down:  ";
    private final String CONTROL_RIGHT = "Right:  ";
    private final String CONTROL_LEFT = "Left:  ";
    private final String CONTROL_ABILITY1 = "Ability 1:  ";
    private final String CONTROL_ABILITY2 = "Ability 2:  ";
    
    private final String CONTROL_BACK = "Back";
    
    private final String START_TEXT = "START"; 
    private final String NEXT_SHIP = ">";
    private final String PREVIOUS_SHIP = "<";
    
    protected static final Font FONT = Config.getInstance().getFont();
    
    private static final int SCREEN_HEIGHT = Config.getInstance().getScreenHeight();
    private static final int SCREEN_WIDTH = Config.getInstance().getScreenWidth();
    
    private static final int SPACE_BETWEEN_MENUS = SCREEN_HEIGHT/50;
    private static final int MENU_TOP_SPACE = SCREEN_HEIGHT/3;
    private static final int CONTROLS_TOP_SPACE = SCREEN_HEIGHT/5;
    private static final int STANDARD_FONT_SIZE = 50;
    private static final int STANDARD_SCREEN_SIZE = 1080;
    
    public enum Controls{UP, DOWN, RIGHT, LEFT, ABILITY1, ABILITY2}
    protected static final float FONT_SIZE = (float)((SCREEN_HEIGHT *
                                    STANDARD_FONT_SIZE) / STANDARD_SCREEN_SIZE);
    
    private static final String BG = "resources/menu/backgroundHammer.png";
    private static final String menuSound = "menu/menuHover.ogg";
    
    private Player player;
    private SoundClip menuSelect;
    
    private Window window;
    private JLabel start, options, scores, exit, startSelect, next, previous, ship;
    private JLabel controlUp, controlDown, controlRight, controlLeft, 
                     controlAbility1, controlAbility2, backControls, backSelect;
    private Box controlBox, selectBox;
    
    private  Image[] shipIcons;
    private int shipsIterator = 0;

    /**
     * Creates new form MainMenu
     */
   
    public MainMenu(Window window)  {
        this.window = window;
        
        shipIcons = new Image[Ships.values().length];
        
        //Estos valoren deberan cambian en getScaledInstance, ya que no son genericos,
        for (int i = 0; i < shipIcons.length; i++){
            try {
                shipIcons[i] = ImageIO.read(new File(Ships.values()[i].getSelectMenu()))
                            .getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, 1);
            } catch (IOException ex) {}
        }
        
        //fontSize = (float)((SCREEN_HEIGHT * STANDARD_FONT_SIZE) / STANDARD_SCREEN_SIZE);
        shipsIterator = 0;
        
        try {
            menuSelect = new SoundClip(menuSound);
        } catch (IOException ex) {}
        
        initMenu();
    }                     

    private void initMenu() {
        
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/4, SCREEN_HEIGHT/20);
        Dimension buttonControl = new Dimension(SCREEN_WIDTH/4, SCREEN_HEIGHT/20);
        Dimension shipNameSize = new Dimension(SCREEN_WIDTH/7, SCREEN_HEIGHT/20);
        
        start = initMenuButton(NEW_GAME_TEXT, buttonSize, true);
        startSelect = initMenuButton(START_TEXT, buttonSize, true);
        options = initMenuButton(OPTIONS_TEXT, buttonSize, true);
        scores = initMenuButton(SCORES_TEXT, buttonSize, true);
        exit = initMenuButton(EXIT_TEXT, buttonSize, true);
        
        controlUp = initMenuButton(CONTROL_UP + KeyEvent.getKeyText
                      (Config.getInstance().getKeyFoward()), buttonControl, true);
        controlDown = initMenuButton(CONTROL_DOWN + KeyEvent.getKeyText
                       (Config.getInstance().getKeyBackward()), buttonControl, true);
        controlRight = initMenuButton(CONTROL_RIGHT+ KeyEvent.getKeyText
                       (Config.getInstance().getKeyRight()), buttonControl, true);
        controlLeft = initMenuButton(CONTROL_LEFT + KeyEvent.getKeyText
                       (Config.getInstance().getKeyLeft()), buttonControl, true);
        controlAbility1 = initMenuButton(CONTROL_ABILITY1 + KeyEvent.getKeyText
                       (Config.getInstance().getKeyHability1()), buttonControl, true);
        controlAbility2 = initMenuButton(CONTROL_ABILITY2 + KeyEvent.getKeyText
                       (Config.getInstance().getKeyHability2()), buttonControl, true);
        
        backControls = initMenuButton(CONTROL_BACK, buttonControl, true);
        backSelect = initMenuButton(CONTROL_BACK, buttonControl, true);
        ship = initMenuButton(Ships.values()[shipsIterator].getName(), shipNameSize, false);
        Dimension selectSize = new Dimension(SCREEN_WIDTH/20, SCREEN_HEIGHT/20);
        
        next = initMenuButton(NEXT_SHIP, selectSize, true);
        previous = initMenuButton(PREVIOUS_SHIP, selectSize, true);
        
        initActionLIsteners();
        
        Box box = Box.createVerticalBox();
        
        //elments of the initial menu and spaces between the buttons
        box.add(Box.createVerticalStrut(MENU_TOP_SPACE));
        box.add(start);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        box.add(options);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        box.add(scores);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS * 4));
        box.add(exit);
        
        this.setLayout(new BorderLayout(100, 100));
        this.add(box, BorderLayout.EAST);
    }
    
    private void startLabelMouseClicked(MouseEvent evt) {
        setVisibleMenu(false);
        
        selectBox = Box.createVerticalBox();
        Box nextPreviousBox = Box.createHorizontalBox();
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/4, SCREEN_HEIGHT/20);
        
        nextPreviousBox.add(previous);
        nextPreviousBox.add(Box.createHorizontalStrut(SPACE_BETWEEN_MENUS-10));
        nextPreviousBox.add(ship);
        nextPreviousBox.add(Box.createHorizontalStrut(SPACE_BETWEEN_MENUS-10));
        nextPreviousBox.add(next);
        
        nextPreviousBox.setMaximumSize(buttonSize);
        nextPreviousBox.setMinimumSize(buttonSize);
        nextPreviousBox.setPreferredSize(buttonSize);
        
        nextPreviousBox.setAlignmentX(0);
        selectBox.add(Box.createVerticalStrut(MENU_TOP_SPACE));
        selectBox.add(startSelect);
        selectBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        selectBox.add(nextPreviousBox);
        selectBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS * 4));
        selectBox.add(backSelect);
        
        selectBox.validate();
        this.setLayout(new BorderLayout(100,100));
        this.add(selectBox, BorderLayout.EAST);
    }
    
    private void startSelectLabelMouseClicked(MouseEvent evt) {
        //player = new HammerHead(0,0, (GameManager)window.getGameContainer().getGame());
        player = ShipFactory.getPlayer(Ships.values()[shipsIterator], 
                    (GameManager)window.getGameContainer().getGame());
        
        if(player != null){
            ((GameManager)(window.getGameContainer().getGame())).addPlayer(player);
            window.getCardLayout().next(window.getCards());
            window.getCanvas().requestFocus();
            window.getMenuClip().stop();
            window.getGameClip().loop();
            window.getGameContainer().resume();
        }
    }
    
    private void nextLabelMouseClicked(MouseEvent evt) {
        shipsIterator = (++shipsIterator) % shipIcons.length;
        //ship.setIcon(new ImageIcon(shipIcons[shipsIterator]));
        ship.setText(Ships.values()[shipsIterator].getName());
        this.repaint();
                    
    } 
    
    private void previousLabelMouseClicked(MouseEvent evt) {
        shipsIterator--;
        if(shipsIterator == -1) shipsIterator = shipIcons.length - 1;
        //ship.setIcon(new ImageIcon(shipIcons[shipsIterator]));
        ship.setText(Ships.values()[shipsIterator].getName());
        this.repaint();
    } 
    
    private void optionsLabelMouseClicked(MouseEvent evt) {                                        
        setVisibleMenu(false);
        Dimension size = new Dimension(SCREEN_HEIGHT, SCREEN_WIDTH/3);
        Dimension sizeItem = new Dimension(SCREEN_HEIGHT/10, SCREEN_WIDTH/30);
        
        controlBox = Box.createVerticalBox();
        
        //elments of the initial menu and spaces between the buttons
        controlBox.add(Box.createVerticalStrut(CONTROLS_TOP_SPACE));
        controlBox.add(controlUp);
        controlBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        controlBox.add(controlDown);
        controlBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        controlBox.add(controlRight);
        controlBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        controlBox.add(controlLeft);
        controlBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        controlBox.add(controlAbility1);
        controlBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        controlBox.add(controlAbility2);
        controlBox.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS * 4));
        controlBox.add(backControls);
        this.setLayout(new BorderLayout(100,100));
        this.add(controlBox, BorderLayout.EAST);
    } 
    private void scoresLabelMouseClicked(MouseEvent evt) {                                        
        
    }
    private void exitLabelMouseClicked(MouseEvent evt) {
        window.dispose();
        System.exit(0);
    }
   
    private JLabel initMenuButton(String text, Dimension size, boolean hover){
        
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
    
    private  JMenuItem initOptionsControlMenu(String text, Dimension size){
        
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
    private void initActionLIsteners(){
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
        
        //Key Listeners de cada elemento de controles
        controlUp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                changeControl(evt, Controls.UP);
            }
        });
        
        controlDown.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                changeControl(evt, Controls.DOWN);
            }
        });
        controlRight.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                changeControl(evt, Controls.RIGHT);
            }
        });
        controlLeft.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                changeControl(evt, Controls.LEFT);
            }
        });
        controlAbility1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                changeControl(evt, Controls.ABILITY1);
            }
        });
        controlAbility2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                changeControl(evt, Controls.ABILITY2);
            }
        });
        backControls.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                backMenuControls();
            }
        });
        backSelect.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                backMenuSelect();
            }
        });
    }
    
    private void changeControl(MouseEvent evt, Controls control){
        ControlPopup popup = new ControlPopup(control);
        
        popup.setEnabled(true);
        popup.show(this, SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
    }
    
    private void backMenuControls(){
        controlBox.setVisible(false);
        controlBox.removeAll();
        setVisibleMenu(true);
    }
    private void backMenuSelect(){
        selectBox.setVisible(false);
        selectBox.removeAll();
        setVisibleMenu(true);
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
        g.drawImage(shipIcons[shipsIterator], 0, 0, null);
    }                 
}
