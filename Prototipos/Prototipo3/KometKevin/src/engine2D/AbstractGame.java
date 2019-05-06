package engine2D;

import game.objects.GameObject;
import gfx.Image;
import java.util.ArrayList;

/**
 * Conecta el motor gráfico con el codigo del juego.
 * 
 * @author Project Kevin
 */
public abstract class AbstractGame {
    protected ArrayList<GameObject> objects;
    protected Image background;
    
    /**
     * Metodo para gestionar los calculos de la simulación del programa.
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param dt deltaTime, referencia al tiempo de simulación 
     */
    public abstract void update(GameContainer gc, float dt);
    
    /**
     * Metodo para dibujar en pantalla.
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param r Renderer, contiene todos los metodos de dibujado del motor
     */
    public abstract void render(GameContainer gc, Renderer r);
    
    // Getters
    public ArrayList<GameObject> getObjects() {return objects;}
    public Image getBackground() {return background;}
    
}
