package gfx;

/**
 * Matriz de imagenes para dar soporte a Sprites.
 * 
 * @author Arturo
 */
public class ImageTile extends Image {
    
    private int tileW, tileH;
    
    public ImageTile(String path, int tileW, int tileH) {
        super(path);
        this.tileH = tileH;
        this.tileW = tileW;
    }

    public int getTileW() {return tileW;}
    public int getTileH() {return tileH;}
    
    public void setTileW(int tileW) {this.tileW = tileW;}
    public void setTileH(int tileH) {this.tileH = tileH;}
}
