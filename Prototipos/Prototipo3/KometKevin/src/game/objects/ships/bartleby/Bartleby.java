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
        
        
        fowardsAccel = 2.9;
        backwardsAccel = 1.9;
        lateralAccel = 1.1;
        
        rotationSpeed = 0.015;
        rotationTolerance = 0.01;
        
        cdValues[0] = 0.17;
        cdValues[1] = 5;
        cdValues[2] = 0.25;
        cdValues[3] = 0;
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {
        if(gc.getInput().isButtonDown(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            cds[0] = 0;
        }
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 ) {
            cds[1] = 0;
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[2] = 0;
        }
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            cds[3] = 0;
        }  
    }
}
