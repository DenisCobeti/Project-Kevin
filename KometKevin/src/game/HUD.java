package game;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 * Esto sera un gameObjet especial como es el caso de Camera
 * @author 
 */
public class HUD {
    private static final int MARGIN_X = 1;
    private static final int MARGIN_Y = 1;
    private static final int ICON_SEPARATION = 1;
    private static final int BARS_SEPARATION = 1;
    private static final int ICON_BARS_SEPARATION = 1;
    private static final int TEXT_OFFSET_X = 1;
    private static final int TEXT_OFFSET_Y = 1;
    
    private static final int BAR_LENGHT = 1;
    
    private GameObject target = null;
    private ImageTile image = new ImageTile("/ships/hammerIcon.png",1,1);
    
    /**
     * Constructor de la clase
     * @param target Objeto del que mostrar el interfaz
     */
    public HUD(GameObject target){
        this.target = target;
    }

    public void update(GameContainer gc, GameManager gm, float dt) {

    }
    
    public void render(GameContainer gc, Renderer r) {
        // Barra de Salud
        r.drawFillRect(10, 10, 180, 10, 0xffff7d00);
        r.drawFillRect(10, 10, 180,  6, 0xffffd660);
        
        // Barra de Energia
        r.drawFillRect(10, 30, 100, 10, 0xff0000ff);
        r.drawFillRect(10, 30, 100,  6, 0xff6060ff);
    }   
}
