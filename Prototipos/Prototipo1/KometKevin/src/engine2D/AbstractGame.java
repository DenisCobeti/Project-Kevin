package engine2D;

import gfx.Image;
import java.util.ArrayList;

/**
 * Conecta el motor gráfico con el codigo del juego.
 * 
 * @author Project Kevin
 */
public abstract class AbstractGame {
    protected ArrayList<Object> objects;
    protected Image background;
    
    /**
     * Metodo para gestionar los calculos de la simulación del programa
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param dt deltaTime, referencia al tiempo de simulación 
     */
    public abstract void update(GameContainer gc, float dt);
    
    /**
     * Metodo para dibujar en pantalla
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param r Renderer, contiene todos los metodos de dibujado del motor
     */
    public abstract void render(GameContainer gc, Renderer r);
    
    // Getters
    public ArrayList<Object> getObjects() {return objects;}
    public Image getBackground() {return background;}    
}
