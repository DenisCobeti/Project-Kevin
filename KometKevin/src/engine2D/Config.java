package engine2D;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que aglutina todos los parametros a personalizar por el usuario.
 * 
 * @author Arturo y Denis
 */
public class Config {
    private static final Config SINGLETON = new Config();
    private static Properties properties;
    private static final String CONFIG_FILE = "config.prop";
    
    public static final String KEY_FORWARD = "ForwardKey";
    public static final String KEY_BACK = "BackKey";
    public static final String KEY_RIGHT = "RightKey";
    public static final String KEY_LEFT = "LeftKey";
    
    public static final String KEY_ABILITY_1 = "Ability1Key";
    public static final String KEY_ABILITY_2 = "Ability2Key";
    
    public static final String KEY_BRAKE = "BreakKey";
    public static final String KEY_DUMPERS = "DumpersKey";
    
    // Configuraciones de la ventana
    private boolean fullScreen;
    private float scale;
    private static  int screenWidth, screenHeight;
    
    // Controles por teclado
    private static int keyFoward, keyBack, keyRight, keyLeft;
    private static int keyAbility1, keyAbility2;
    
    private static int keyBrake, keyDumpers, keyPause;
    
    // Botones del raton
    private int primaryFire, secondaryFire;
   
    private Font font;
    /**
     * Constructor Singleton de la clase
     */
    private Config() {
        setDefaultValues();
        try{
            load();
        } catch(FileNotFoundException e){
            try {
                initialSave();
            } catch (IOException ex) { /*nunca deberia pasar */}
        } catch (IOException ex) {/*nunca deberia pasar*/}
    }
    
    public static Config getInstance() {
        return SINGLETON;
    }
    
    /**
     * Establece los valores por defecto para la aplicación
     */
    private void setDefaultValues() {
        fullScreen = true;
        scale = 1f;
        screenWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / scale);
        screenHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / scale);

        /*
        // Controles por teclado
        keyFoward = KeyEvent.VK_W;
        keyBack = KeyEvent.VK_S;
        keyRight = KeyEvent.VK_D;
        keyLeft = KeyEvent.VK_A;
        */
        primaryFire = MouseEvent.BUTTON1;
        secondaryFire = MouseEvent.BUTTON3;
        keyPause = KeyEvent.VK_P;
        
        /*
        keyHability1 = KeyEvent.VK_Q;
        keyHability2 = KeyEvent.VK_E;
        
        keyBrake = KeyEvent.VK_SPACE;
        keyDumpers = KeyEvent.VK_X;
        */
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/bgothl.ttf"));
        } catch (Exception ex) {
            font = Font.getFont(Font.SERIF);
        }
    }
    
    private static void load() throws FileNotFoundException, IOException  {
        properties = new Properties();
        
        FileInputStream in = new FileInputStream(CONFIG_FILE);
        properties.load(in);
        
        keyFoward = Integer.parseInt(((String)properties.get(KEY_FORWARD)));
        keyRight = Integer.parseInt((String)properties.get(KEY_RIGHT));
        keyBack = Integer.parseInt((String)properties.get(KEY_BACK));
        keyLeft = Integer.parseInt((String)properties.get(KEY_LEFT));
        
        keyAbility1 = Integer.parseInt((String)properties.get(KEY_ABILITY_1));  
        keyAbility2 = Integer.parseInt((String)properties.get(KEY_ABILITY_2)); 
        
        keyBrake = Integer.parseInt((String)properties.get(KEY_BRAKE));  
        keyDumpers = Integer.parseInt((String)properties.get(KEY_DUMPERS));  
    }
    
    private static void initialSave() throws  IOException  {
        properties = new Properties();
        FileOutputStream out = new FileOutputStream(CONFIG_FILE);
        
        //steamos todas las propiedades con su llave adecuada
        properties.setProperty(KEY_FORWARD, Integer.toString(KeyEvent.VK_W));
        properties.setProperty(KEY_BACK, Integer.toString(KeyEvent.VK_S));
        properties.setProperty(KEY_RIGHT, Integer.toString(KeyEvent.VK_D));
        properties.setProperty(KEY_LEFT, Integer.toString(KeyEvent.VK_A));
        
        properties.setProperty(KEY_ABILITY_1, Integer.toString(KeyEvent.VK_Q));
        properties.setProperty(KEY_ABILITY_2, Integer.toString(KeyEvent.VK_E));
        
        properties.setProperty(KEY_BRAKE, Integer.toString(KeyEvent.VK_SPACE));
        properties.setProperty(KEY_DUMPERS, Integer.toString(KeyEvent.VK_X));
        
        //guardamos el fichero
        properties.store(out, null);
        
        //ponemos todos los valores por defecto
        keyFoward = KeyEvent.VK_W;
        keyBack = KeyEvent.VK_S;
        keyRight = KeyEvent.VK_D;
        keyLeft = KeyEvent.VK_A;
        
        keyAbility1 = KeyEvent.VK_Q;
        keyAbility2 = KeyEvent.VK_E;
        
        keyBrake = KeyEvent.VK_SPACE;
        keyDumpers = KeyEvent.VK_X;
    }
    public static void save(){
        try {
            FileOutputStream out = new FileOutputStream(CONFIG_FILE);
            //guardamos el fichero
            properties.store(out, null);
        } catch (FileNotFoundException ex) { /*Nunca pasara, dado que ya controlamos esto al principio*/
        } catch (IOException ex) {}
    }
    public static void changeKey(String id, int key){
        
        switch(id){
            case KEY_FORWARD:
                keyFoward = key;
                break;
            case KEY_RIGHT:
                keyRight = key;
                break;
            case KEY_BACK:
                keyBack = key;
                break;
            case KEY_LEFT:
                keyLeft = key;
                break;
            case KEY_ABILITY_1:
                keyAbility1 = key;
                break;
            case KEY_ABILITY_2:
                keyAbility2 = key;
                break;
            case KEY_BRAKE:
                keyBrake = key;
                break;
            case KEY_DUMPERS:
                keyDumpers = key;
                break;
        }
        properties.setProperty(id, Integer.toString(key));
    }
    
    // Getters & Setters
    public boolean getFullScreen() {return fullScreen;}
    public float getScale() {return scale;}
    public static int getScreenWidth() {return screenWidth;}
    public static int getScreenHeight() {return screenHeight;}
    public int getKeyFoward() {return keyFoward;}
    public int getKeyBackward() {return keyBack;}
    public int getKeyRight() { return keyRight;}
    public int getKeyLeft() {return keyLeft;}   
    public int getKeyHability1() {return keyAbility1;}
    public int getKeyHability2() {return keyAbility2;}
    public int getKeybrake() {return keyBrake;}
    public int getKeyDumpers() {return keyDumpers;}
    public int getPrimaryFire() {return primaryFire;}
    public int getSecondaryFire() {return secondaryFire;}
    public static int getKeyPause() {return keyPause;}
    
    public Font getFont() {return font;}
    
    public void setFullScreen(boolean fullScreen) {this.fullScreen = fullScreen;}
    public void setScale(float scale) {this.scale = scale;}
    public void setScreenWidth(int screenWidth) {this.screenWidth = screenWidth;}
    public void setScreenHeight(int screenHeight) {this.screenHeight = screenHeight;}
    public void setKeyFoward(int keyFoward) {this.keyFoward = keyFoward;}
    public void setKeyBackward(int keyBackward) {this.keyBack = keyBackward;}
    public void setKeyRight(int keyRight) {this.keyRight = keyRight;}
    public void setKeyLeft(int keyLeft) {this.keyLeft = keyLeft;}   
    public void setKeyHability1(int keyAbility1) {this.keyAbility1 = keyAbility1;}
    public void setKeyHability2(int keyAbility2) {this.keyAbility2 = keyAbility2;}
    public void setKeybrake(int keybrake) {this.keyBrake = keybrake;}
    public void setKeyDumpers(int keyDumpers) {this.keyDumpers = keyDumpers;}
    public void setPrimaryFire(int primaryFire) {this.primaryFire = primaryFire;}
    public void setSecondaryFire(int secondaryFire) {this.secondaryFire = secondaryFire;}
    
}
