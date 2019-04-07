package engine2D;

import gfx.Image;
import gfx.ImageTile;
import java.awt.image.DataBufferInt;

/**
 * Clase que dibuja en el Canvas. TODA MEJORA ES BIEN RECIBIDA.
 * Esta clase tiene gran impacto en el rendimiento, es el cuello de botella.
 * 
 * Recomendaciones:
 * Repetir codigo.
 * No usar constructores en los metodos.
 * 
 * @author Project Kevin
 */
public class Renderer {
    private int screenWidth, screenHeight;
    private int[] pixels;
    
    private int camX, camY;
    private Image background = null;
    
    /**
     * Constructor de la clase.
     * @param gc GameContainer que contiene la ventana en la que dibujar y las 
     * dimensiones de su Canvas
     */
    public Renderer(GameContainer gc) {
        screenWidth = gc.SCREEN_WIDTH;
        screenHeight = gc.SCREEN_HEIGHT;
        
        pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }
    
    /**
     * Limpia la pantalla redibujando el fondo
     */
    public void clear() {
        if (background == null) {
            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = 0xff000000;
            }
        } else {
            drawImage(background, 0, 0);
        }
    }
    
    /**
     * Metodo básico para dibujar un punto. Soporta transparencias
     * @param x Posición x del canvas
     * @param y Posición y del canvas
     * @param value color. Formato Alpha-RGB: 0xAARRGGBB 
     */
    public void setPixel(int x, int y, int value) {
        int alpha = ((value >> 24) & 0xff);
        
        // No se dibuja fuera del canvas o pixeles totalmente transparentes
        if ((x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) || (alpha == 0x00))
            return;
        
        // Pixel opaco, muy eficiente
        if (alpha == 0xff) {
            pixels[x + y * screenWidth] = value;
            
        // Pixel con cualquier grado de transparencia, a excepción de completa
        } else {
            int pixelColor = pixels[x + y * screenWidth];
            
            int newRed = ((pixelColor >> 16) & 0xff) - (int)((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int newGreen = ((pixelColor >> 8) & 0xff) - (int)((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int newBlue = (pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value & 0xff)) * (alpha / 255f));
            
            pixels[x + y * screenWidth] = (255 << 24 | newRed << 16 | newGreen << 8 | newBlue);
        }
    }
    
    /**
     * Dibuja una Image tomando como referencia su esquina superior izquierda
     * @param image Imagen con un vector de pixeles de Alpha-RGB
     * @param offX offset de la posición X
     * @param offY offset de la posición Y
     */
    public void drawImage(Image image, int offX, int offY) {    
        offX -= camX;
        offY -= camY;
        
        // Se comprueba que parte de la imagen quede dentro de la ventana
        if (offX < -image.getW()) return;
        if (offY < -image.getH()) return;
        if (offX >= screenWidth)  return;
        if (offY >= screenHeight) return;
        
        int newWidth = image.getW();
        int newHeight = image.getH();
        int newX = 0;
        int newY = 0;
        
        // Se recorta la parte de la imagen que no entre en la ventana
        if (offX < 0) {newX -= offX;}
        if (offY < 0) {newY -= offY;}
        if (newWidth + offX >= screenWidth) {newWidth = screenWidth - offX;}
        if (newHeight + offY >= screenHeight) {newHeight = screenHeight - offY;}
        
        // Se recorre y dibuja la imagen en el vector de pixeles
        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
            }
        }
    }
    
    /**
     * Dibuja una Image tomando como referencia su esquina superior izquierda y
     * rotada
     * @param image Imagen con un vector de pixeles de Alpha-RGB
     * @param offX offset de la posición X
     * @param offY offset de la posición Y
     * @param angle angulo en radianes de rotación 
     */
    public void drawRotatedImage(Image image, int offX, int offY, double angle) {             
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);
        int pWidth = image.getW()/2;
        int pHeight = image.getH()/2;
        
        int x, y;
        int transX, transY;
        int newX, newY;
        
        offX -= camX;
        offY -= camY;

        for (y = 0; y < image.getH(); y++) {
            for (x = 0; x < image.getW(); x++) {
                newX = x - pWidth;
                newY = y - pHeight;
                transX = (int)(newX * cos - newY * sin);
                transY = (int)(newX * sin + newY * cos);
                newX = transX + pWidth;
                newY = transY + pHeight;
                
                if (newX >= 0 && newX < image.getW() && newY >= 0 && newY < image.getH()) {
                    setPixel(x + offX, y + offY, image.getP()[newX + newY * image.getW()]);
                }
            }
        }
    }
    
    /**
     * Dibuja una de las casillas de una matriz de imagenes
     * @param image Imagen con un vector de pixeles de Alpha-RGB
     * @param offX offset de la posición X
     * @param offY offset de la posición Y
     * @param tileX posición x en la matriz del ImageTile
     * @param tileY posición y en la matriz del ImageTile
     */
    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        offX -= camX;
        offY -= camY;
                
        // Se comprueba que la imagen quede dentro de la ventana
        if (offX < -image.getTileW()) {return;}
        if (offY < -image.getTileH()) {return;}
        if (offX >= screenWidth) {return;}
        if (offY >= screenHeight) {return;}
        
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();
        
        // Se Comprueba y recorta si parte de la imagen no entra en la ventana
        if (offX < 0) {newX -= offX;}
        if (offY < 0) {newY -= offY;}
        if (newWidth + offX >= screenWidth) {newWidth = screenWidth - offX;}
        if (newHeight + offY >= screenHeight) {newHeight = screenHeight - offY;}
        
        // Dibujamos la imagen
        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
            }
        }
    }
    
    /**
     * Dibuja una de las casillas de una matriz de imagenes rotada.
     * @param image Imagen con un vector de pixeles de Alpha-RGB
     * @param offX offset de la posición X
     * @param offY offset de la posición Y
     * @param tileX posición x en la matriz del ImageTile
     * @param tileY posición y en la matriz del ImageTile
     * @param angle angulo en radianes de rotación 
     */
    public void drawRotatedImageTile(ImageTile image, int offX, int offY, int tileX, int tileY, double angle) {
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);
        int pWidth = image.getTileW()/2 + tileX * image.getTileW();
        int pHeight = image.getTileH()/2 + tileY * image.getTileH();
        
        int x, y;
        int transX, transY;
        int newX, newY;
        
        offX -= camX;
        offY -= camY;
        
        for (y = 0; y < image.getTileH(); y++) {
            for (x = 0; x < image.getTileW(); x++) {
                newX = x - image.getTileW()/2;
                newY = y - image.getTileH()/2;
                transX = (int)(newX * cos - newY * sin);
                transY = (int)(newX * sin + newY * cos);
                newX = transX + pWidth;
                newY = transY + pHeight;
                
                if (newX >= tileX * image.getTileW() && newX < (tileX + 1) * image.getTileW() && 
                    newY >= tileY * image.getTileH() && newY < (tileY + 1) * image.getTileH()) {
                    setPixel(x + offX, y + offY, image.getP()[newX + newY * image.getW()]);
                }
            }
        }
    }  
    
    /**
     * Dibuja una linea recta entre 2 puntos
     * @param offX posición X del punto de origen
     * @param offY posición Y del punto de origen
     * @param offX2 posición X del punto de destino
     * @param offY2 posición Y del punto de destino
     * @param color color de la recta. Formato Alpha-RGB: 0xAARRGGBB 
     */
    public void drawLine(int offX, int offY, int offX2, int offY2, int color) {
        offX -= camX; offX2 -= camX;
        offY -= camY; offY2 -= camY;

        int dx = offX2 - offX;
        int dy = offY2 - offY;   
        
        for (int x = offX; x < offX2; x++) {
            int y = offY + dy * (x - offX) / dx;
            setPixel(x, y, color);
        }
        for (int y = offY; y < offY2; y++) {
            int x = offX + dx * (y - offY) / dy;
            setPixel(x, y, color);
        }
        for (int x = offX2; x < offX; x++) {
            int y = offY + dy * (x - offX) / dx;
            setPixel(x, y, color);
        }
        for (int y = offY2; y < offY; y++) {
            int x = offX + dx * (y - offY) / dy;
            setPixel(x, y, color);
        }
    }
    
    /**
     * Dibuja un rectangulo hueco indicando su esquina superior izquieda
     * @param offX offset x
     * @param offY offset y
     * @param width anchura del rectangulo
     * @param height altura del rectangulo
     * @param color color del rectangulo. Formato Alpha-RGB: 0xAARRGGBB 
     */
    public void drawRect(int offX, int offY, int width, int height, int color) {
        offX -= camX;
        offY -= camY;
        
        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }
        for (int x = 0; x <= width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }
   
    /**
     * Dibuja un rectangulo solido indicando su esquina superior izquieda
     * @param offX offset x
     * @param offY offset y
     * @param width anchura del rectangulo
     * @param height altura del rectangulo
     * @param color color del rectangulo. Formato Alpha-RGB: 0xAARRGGBB 
     */
    public void drawFillRect(int offX, int offY, int width, int height, int color) {
        offX -= camX;
        offY -= camY;
        
        for(int y = 0; y <= height; y++) {
            for(int x = 0;x <= width; x++) {
                setPixel(x + offX, y + offY, color);
            }
        }
    }
    
    /**
     * Dibuja un cirulo solido, de radio y color determinados.
     * @param offX offset x
     * @param offY offset y
     * @param radius radio del circulo
     * @param color color del circulo. Formato Alpha-RGB: 0xAARRGGBB 
     */
    public void drawFillCircle (int offX, int offY, int radius, int color) {               
        int x, y;
        
        offX -= camX;
        offY -= camY;
        
        for (x = -radius; x < radius; x++) {
            // Mitad de arriba
            for (y = 0; y*y + x*x <= radius*radius; y--) {
                setPixel(x + offX, y + offY, color);
            }
            // Mitad de abajo
            for (y = 1; y*y + x*x <= radius*radius; y++) {
                setPixel(x + offX, y + offY, color);
            }
        }
    }
    
    // Getters & Setters
    public void setCamX(int camX) {this.camX = camX;}
    public void setCamY(int camY) {this.camY = camY;}
    public void setBackground(Image background) {this.background = background;}
    
    public int getCamX() {return camX;}
    public int getCamY() {return camY;}
}