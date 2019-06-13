package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Objecto que carga una imagen y la transforma en un vector de RGB para su 
 * carga a la Window mediante el Renderer.
 * 
 * @author Arturo
 */
public class Image {
    private int w, h, d;    // Ancho, Alto y Diagonal
    private int[] p;        // Matriz de pixeles
    
    /**
     * Constructor
     * @param path ruta de la imagen en la carpeta /resources 
     */
    public Image(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        w = image.getWidth();
        h = image.getHeight();
        d = (int) Math.sqrt(w * w + h * h);
        p = image.getRGB(0, 0, w, h, null, 0, w);
        
        image.flush();
    }

    public int getW() {return w;}
    public int getH() {return h;}
    public int getD() {return d;}    
    public int[] getP() {return p;}

    public void setW(int w) {this.w = w;}
    public void setH(int h) {this.h = h;}
    public void setD(int d) {this.d = d;}
    public void setP(int[] p) {this.p = p;}
}
