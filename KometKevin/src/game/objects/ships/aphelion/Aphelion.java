package game.objects.ships.aphelion;

import game.GameManager;
import game.colliders.BoxCollider;
import game.objects.Player;
import gfx.ImageTile;

/**
 *
 * @author ProjectKevin
 */
public class Aphelion extends Player {
    
    public Aphelion(int x, int y, GameManager gm) {
        super(x, y);
        image = new ImageTile("/ships/aphelionShip.png",90,92);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 72, 46);
    }

    @Override
    public double fire1(GameManager gm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double fire2(GameManager gm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double hability1(GameManager gm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double hability2(GameManager gm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
