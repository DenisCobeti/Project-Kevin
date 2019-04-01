package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Objecto que carga una imagen y la transforma en un vector de RGB para su 
 * carga a la Window mediante el Renderer.
 * 
 * @author Project Kevin
 */
public class Image {
    private int w, h;
    private int[] p;
    
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
        p = image.getRGB(0, 0, w, h, null, 0, w);
        
        image.flush();
    }

    public int getW() {return w;}
    public int getH() {return h;}
    public int[] getP() {return p;}

    public void setW(int w) {this.w = w;}
    public void setH(int h) {this.h = h;}
    public void setP(int[] p) {this.p = p;}
}
