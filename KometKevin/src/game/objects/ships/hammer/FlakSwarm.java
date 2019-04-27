package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.objects.GameObject;
import game.objects.ships.AdvancedHability;

/**
 * Segunda habilidad de la HammerHead
 * @author
 */
public class FlakSwarm extends AdvancedHability{
    
    @Override
    public double activate(double cd) {
        return 0.0;
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
