package game.objects.ships.bartleby;

import game.GameManager;
import game.colliders.BoxCollider;
import game.objects.Player;
import gfx.ImageTile;

/**
 *
 * @author
 */
public class Bartleby extends Player {
    
    public Bartleby(int x, int y, GameManager gm) {
        super(x, y);
        image = new ImageTile("/ships/bartlebyShip.png",56,48);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 48, 30);
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
