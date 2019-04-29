package game.objects.ships.bartleby;

import engine2D.GameContainer;
import game.GameManager;
import game.colliders.BoxCollider;
import game.objects.Player;
import game.objects.ships.Ships;
import gfx.ImageTile;

/**
 *
 * @author
 */
public class Bartleby extends Player {
    
    public Bartleby(int x, int y, GameManager gm) {
        super(x, y);
        image = new ImageTile(Ships.Bartleby.getSprite(),Ships.Bartleby.getSizeX(),
                                Ships.Bartleby.getSizeY());
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 48, 30);
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {
        if(gc.getInput().isButtonDown(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            cds[0] = fire1Cd;
        }
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 ) {
            cds[1] = fire2Cd;
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[2] = ability1Cd;
        }
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            cds[3] = ability2Cd;
        }  
    }
}
