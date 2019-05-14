package engine2D;

import audio.SoundClip;
import game.GameManager;
import game.objects.Player;
import gui.MainMenu;
import gui.ScorePopup;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Clase con un JFrame para la representación de la ventana de la aplicación.
 * Contiene los menus iniciales y el canvas para el dibujado
 * 
 * @author Project Kevin
 */
public class Window extends JFrame{
    private static final String GAME_NAME = "Kommet Kevin v.0.2.1";
    
    private SoundClip menuClip;
    private SoundClip gameClip;        
    
    private Config config;
    private GameContainer gc;
    
    private JPanel cards;
    private MainMenu menu;
    private Canvas canvas;
    
    private BufferedImage image;
    private BufferStrategy bs;
    private Graphics g;
    
    private int screenWidth;
    private int screenHeight;
    
    public Window(GameContainer gc) { 
        super(GAME_NAME);
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        
        try {
            menuClip = new SoundClip("music/ObservingTheStar.ogg");
            gameClip = new SoundClip("music/IDoKnow.ogg");
        } catch (IOException ex) {
            //necesitamos mejor tratamiento de errores
            ex.printStackTrace();
        }
        
        this.gc = gc;
        cards = new JPanel();
        menu = new MainMenu(this);
        canvas = new Canvas();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
  
        cards.setLayout(new CardLayout());
        cards.add(menu);
        cards.add(canvas);
        add(cards);
        
        menuClip.loop();
        
        screenWidth = gc.getConfig().getScreenWidth();
        screenHeight = gc.getConfig().getScreenHeight();
        
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Se crea el juego con las propiedades establecidas y se dimensiona el 
     * canvas para el motor en el JFrame
     */
    public void execGame(){       
        image = new BufferedImage(screenWidth, screenHeight, 
                                  BufferedImage.TYPE_INT_RGB);
        Dimension s = new Dimension((int)(screenWidth  * gc.getConfig().getScale()), 
                                    (int)(screenHeight * gc.getConfig().getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
         
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    
    /**
     * Indica al canvas que muestre un frame
     */
    public void update(){
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }
    
    public static void main(String args[]) {
        GameContainer gc = new GameContainer();
        gc.start(new GameManager());
    }
    
    public void deadPLayer(Player player){
        ScorePopup popup = new ScorePopup("boom", player.getScore());
        
        
        popup.setEnabled(true);
        popup.show(this, screenWidth/3, screenHeight/3);
        getGameClip().stop();
    }
    
    // Getters
    public GameContainer getGameContainer() {return gc;}
    public BufferedImage getImage() {return image;}
    public Canvas getCanvas() {return canvas;}
    public JPanel getCards() {return cards;}
    public CardLayout getCardLayout() {return (CardLayout) cards.getLayout();}

    public SoundClip getMenuClip() {return menuClip;}
    public SoundClip getGameClip() {return gameClip;}
}
