package game;

import engine2D.GameContainer;
import game.objects.GameObject;

/**
 * Camara a mostrar en la Window. Busca un objecto en el GameManager con el tag
 * determinado e informa al Renderer y al Input de los offsets de la misma.
 * 
 * @author Project Kevin
 */
public class Camera {
    private float camX, camY;
    private GameObject target = null;
    
    /**
     * Constructor de la clase
     * @param target Objeto al que sigue la camara
     */
    public Camera(GameObject target){
        this.target = target;
    }
    
    /**
     * Método para gestionar los calculos para el manejo de la cámara
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param gm GameManager, permite acceso a los objetos del juego
     * @param dt deltaTime, referencia al tiempo de simulación 
     */
    public void update(GameContainer gc, GameManager gm, float dt) {
        if(target == null) {
            return;
        }
        
        camX = (float) (target.getCenter().x - gc.getConfig().getScreenWidth() / 2);
        camY = (float) (target.getCenter().y - gc.getConfig().getScreenHeight() / 2);
               
        if (camX < 0) camX = 0;
        if (camY < 0) camY = 0;
        if (camX > gm.getBackground().getW() - gc.getConfig().getScreenWidth()) 
            camX = gm.getBackground().getW() - gc.getConfig().getScreenWidth();
        if (camY > gm.getBackground().getH() - gc.getConfig().getScreenHeight()) 
            camY = gm.getBackground().getH() - gc.getConfig().getScreenHeight();
        
        gc.getRenderer().setCamX((int) camX);
        gc.getRenderer().setCamY((int) camY);
        
        gc.getInput().setCamX((int) camX);
        gc.getInput().setCamY((int) camY);
    }

    // Getters & Setters
    public float getOffX() {return camX;}
    public float getOffY() {return camY;}
    
    public void setOffX(float offX) {this.camX = offX;}
    public void setOffY(float offY) {this.camY = offY;}
}
