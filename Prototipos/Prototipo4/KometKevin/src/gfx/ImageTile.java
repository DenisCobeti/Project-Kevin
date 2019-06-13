package gfx;

/**
 * Matriz de imagenes para dar soporte a Sprites.
 * 
 * @author Arturo
 */
public class ImageTile extends Image {
    
    private int tileW, tileH, tileD;
    
    public ImageTile(String path, int tileW, int tileH) {
        super(path);
        this.tileH = tileH;
        this.tileW = tileW;
        
        tileD = (int) Math.sqrt(tileH * tileH + tileW * tileW);
    }

    public int getTileW() {return tileW;}
    public int getTileH() {return tileH;}
    public int getTileD() {return tileD;}
    
    public void setTileW(int tileW) {this.tileW = tileW;}
    public void setTileH(int tileH) {this.tileH = tileH;}
    public void setTileD(int tileD) {this.tileD = tileD;}
}
