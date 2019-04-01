package engine2D;

import java.awt.BorderLayout;
import java.awt.Canvas;
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
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;
    
    public Window(GameContainer gc) {
        image = new BufferedImage(gc.SCREEN_WIDTH, gc.SCREEN_HEIGHT, 
                                  BufferedImage.TYPE_INT_RGB);
        
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.SCREEN_WIDTH * gc.SCALE), 
                                    (int)(gc.SCREEN_HEIGHT * gc.SCALE));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        
        frame = new JFrame("Kevin Engine v.0.1");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
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
