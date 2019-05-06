package game.objects.ships.aphelion;

import engine2D.GameContainer;
import game.GameManager;
import game.colliders.BoxCollider;
import game.objects.Player;
import game.objects.ships.Ships;
import gfx.ImageTile;

/**
 *
 * @author ProjectKevin
 */
public class Aphelion extends Player {
    
    public Aphelion(int x, int y, GameManager gm) {
        super(x, y);
        image = new ImageTile(Ships.Aphelion.getSprite(),Ships.Aphelion.getSizeX(),
                                Ships.Aphelion.getSizeY());
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 72, 46);
        
        fowardsAccel = 4;
        backwardsAccel = 4;
        lateralAccel = 4;
        
        //hay que pulir
        rotationSpeed = 0.06;
        rotationTolerance = 0.027;
        
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
