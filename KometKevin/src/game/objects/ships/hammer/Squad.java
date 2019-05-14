package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 * Escuadra de naves que lanza la hammer
 * @author Project Kevin
 */
public class Squad extends GameObject{

    private ImageTile image = new ImageTile("/projectiles/squad.png",22,22);
    
    public Squad() {

    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void effect(GameObject go) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
