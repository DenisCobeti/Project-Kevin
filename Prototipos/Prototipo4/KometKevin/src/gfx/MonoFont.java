package gfx;

/**
 * Matriz de caracteres para escribir cadenas de texto.
 * Fuentes monoespaciadas de Unicode U0020 a U005A. Números y mayusculas.
 * 
 * @author Arturo
 */
public class MonoFont extends ImageTile{
    // Fuentes prefabricadas
    public static final MonoFont STANDARD = new MonoFont("/fonts/joystix.png",19,16);
    
    // Caracteres soportados por la fuente
    public static final int UTF8_INI = 32;
    public static final int UTF8_FIN = 91;
    
    public MonoFont(String path, int tileW, int tileH) {
        super(path, tileW, tileH);
    }
}
