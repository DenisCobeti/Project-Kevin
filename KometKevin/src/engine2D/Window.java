package engine2D;

import game.GameManager;
import gui.MainMenu;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
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
    
    private Config config;
    private GameContainer gc;
    
    private JPanel cards;
    private MainMenu menu;
    private Canvas canvas;
    
    private BufferedImage image;
    private BufferStrategy bs;
    private Graphics g;
    
    public Window(GameContainer gc) { 
        super(GAME_NAME);
        
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
        
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Se crea el juego con las propiedades establecidas y se dimensiona el 
     * canvas para el motor en el JFrame
     */
    public void execGame(){       
        image = new BufferedImage(gc.getConfig().getScreenWidth(), 
                                  gc.getConfig().getScreenHeight(), 
                                  BufferedImage.TYPE_INT_RGB);
        Dimension s = new Dimension((int)(gc.getConfig().getScreenWidth()  * 
                                                gc.getConfig().getScale()), 
                                    (int)(gc.getConfig().getScreenHeight() *
                                                gc.getConfig().getScale()));
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
    
    // Getters
    public GameContainer getGameContainer() {return gc;}
    public BufferedImage getImage() {return image;}
    public Canvas getCanvas() {return canvas;}
    public JPanel getCards() {return cards;}
    public CardLayout getCardLayout() {return (CardLayout) cards.getLayout();}
}
