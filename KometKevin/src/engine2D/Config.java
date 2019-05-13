package engine2D;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Clase que aglutina todos los parametros a personalizar por el usuario.
 * 
 * @author Project Kevin
 */
public class Config {
    private static final Config SINGLETON = new Config();
    

    // Configuraciones de la ventana
    private int screenWidth;
    private int screenHeight;
    private boolean fullScreen;
    private float scale;
    
    // Controles por teclado
    private int keyFoward;
    private int keyBackward;
    private int keyRight;
    private int keyLeft;
    
    private int keyHability1;
    private int keyHability2;
    
    private int keybrake;
    private int keyDumpers;
    
    // Botones del raton
    private int primaryFire;
    private int secondaryFire;
    
    private Font font;
    /**
     * Constructor Singleton de la clase
     */
    private Config() {
        setDefaultValues();
    }
    
    public static Config getInstance() {
        return SINGLETON;
    }
    
    /**
     * Establece los valores por defecto para la aplicación
     */
    public void setDefaultValues() {
        scale = 1f;
        screenWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / scale);
        screenHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / scale);
        fullScreen = true;
        

        // Controles por teclado
        
        keyFoward = KeyEvent.VK_W;
        keyBackward = KeyEvent.VK_S;
        keyRight = KeyEvent.VK_D;
        keyLeft = KeyEvent.VK_A;

        primaryFire = MouseEvent.BUTTON1;
        secondaryFire = MouseEvent.BUTTON3;
        keyHability1 = KeyEvent.VK_Q;
        keyHability2 = KeyEvent.VK_E;
        keybrake = KeyEvent.VK_SPACE;
        keyDumpers = KeyEvent.VK_X;
        
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/bgothl.ttf"));
        } catch (Exception ex) {
            font = Font.getFont(Font.SERIF);
        }
    }

    // Getters & Setters
    public int getScreenWidth() {return screenWidth;}
    public int getScreenHeight() {return screenHeight;}
    public boolean getFullScreen() {return fullScreen;}
    public float getScale() {return scale;}
    public int getKeyFoward() {return keyFoward;}
    public int getKeyBackward() {return keyBackward;}
    public int getKeyRight() { return keyRight;}
    public int getKeyLeft() {return keyLeft;}   
    public int getKeyHability1() {return keyHability1;}
    public int getKeyHability2() {return keyHability2;}
    public int getKeybrake() {return keybrake;}
    public int getKeyDumpers() {return keyDumpers;}
    public int getPrimaryFire() {return primaryFire;}
    public int getSecondaryFire() {return secondaryFire;}
    public Font getFont() {return font;}
    
    public void setScreenWidth(int screenWidth) {this.screenWidth = screenWidth;}
    public void setScreenHeight(int screenHeight) {this.screenHeight = screenHeight;}
    public void setFullScreen(boolean fullScreen) {this.fullScreen = fullScreen;}
    public void setScale(float scale) {this.scale = scale;}
    public void setKeyFoward(int keyFoward) {this.keyFoward = keyFoward;}
    public void setKeyBackward(int keyBackward) {this.keyBackward = keyBackward;}
    public void setKeyRight(int keyRight) {this.keyRight = keyRight;}
    public void setKeyLeft(int keyLeft) {this.keyLeft = keyLeft;}   
    public void setKeyHability1(int keyHability1) {this.keyHability1 = keyHability1;}
    public void setKeyHability2(int keyHability2) {this.keyHability2 = keyHability2;}
    public void setKeybrake(int keybrake) {this.keybrake = keybrake;}
    public void setKeyDumpers(int keyDumpers) {this.keyDumpers = keyDumpers;}
    public void setPrimaryFire(int primaryFire) {this.primaryFire = primaryFire;}
    public void setSecondaryFire(int secondaryFire) {this.secondaryFire = secondaryFire;}
    
}
