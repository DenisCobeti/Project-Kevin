package game;

import engine2D.Config;
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
    private final static int BORDER = 100; 
    private GameObject target = null;
    
    private static final int SCREEN_WIDTH = Config.getScreenWidth();
    private static final int SCREEN_HEIGHT = Config.getScreenHeight();
    
    /**
     * Constructor de la clase
     * @param target Objeto al que sigue la camara
     */
    public Camera(GameObject target) {
        this.target = target;
    }
    
    public Camera() {}
    
    /**
     * Método para gestionar los calculos para el manejo de la cámara
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param gm GameManager, permite acceso a los objetos del juego
     * @param dt deltaTime, referencia al tiempo de simulación 
     */
    public void update(GameContainer gc, GameManager gm, float dt) {
        if (target != null) {
            camX = (float) (target.getCenter().x - SCREEN_WIDTH / 2);
            camY = (float) (target.getCenter().y - SCREEN_HEIGHT / 2);

            if (camX < BORDER ) camX = BORDER ;
            if (camY < BORDER ) camY = BORDER ;
            if (camX > gm.getBackground().getW() - SCREEN_WIDTH - BORDER ) 
                camX = gm.getBackground().getW() - SCREEN_WIDTH - BORDER ;
            if (camY > gm.getBackground().getH() - SCREEN_HEIGHT - BORDER ) 
                camY = gm.getBackground().getH() - SCREEN_HEIGHT- BORDER ;

            gc.getRenderer().setCamX((int) camX);
            gc.getRenderer().setCamY((int) camY);

            gc.getInput().setCamX((int) camX);
            gc.getInput().setCamY((int) camY);
        }
    }

    // Getters & Setters
    public GameObject getTarget() {return target;}
    public float getOffX() {return camX;}
    public float getOffY() {return camY;}

    public void setTarget(GameObject target) {this.target = target;}
    public void setOffX(float offX) {this.camX = offX;}
    public void setOffY(float offY) {this.camY = offY;}
}
