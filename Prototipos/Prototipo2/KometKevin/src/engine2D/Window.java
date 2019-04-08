package engine2D;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * Clase con un JFrame para la representación de la ventana de la aplicación.
 * La configuración de la ventana reside en GameContainer
 * 
 * @author Project Kevin
 */
public class Window {
    private static final String GAME_NAME = "Kommet Kevin v.0.2.0";
   
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;
    
    public Window(GameContainer gc) {
        image = new BufferedImage(gc.getConfig().getScreenWidth(), 
                                  gc.getConfig().getScreenHeight(), 
                                  BufferedImage.TYPE_INT_RGB);
        
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.getConfig().getScreenWidth()  * 
                                                gc.getConfig().getScale()), 
                                    (int)(gc.getConfig().getScreenHeight() *
                                                gc.getConfig().getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        
        frame = new JFrame(GAME_NAME);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ocupa toda la pantalla
        frame.setUndecorated(true);                    // Fuera la barra
        frame.setLocationRelativeTo(null);             // Ventana centrada  

        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public void loadCanvas() {
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
         
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    
    /**
     * Indica al canvas que muestre un frame
     */
    public void update(){
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }
    
    // Getters
    public BufferedImage getImage() {return image;}
    public Canvas getCanvas() {return canvas;}
    public JFrame getFrame() {return frame;}
}
