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
    
    private Laser laser;
    
    public Aphelion(int x, int y, GameManager gm) {
        super(x, y);
        tag = "aphelion";
        id = 1;
        color = 0xff53c0f9;
        
        image = new ImageTile(Ships.Aphelion.getSprite(),Ships.Aphelion.getSizeX(),
                                Ships.Aphelion.getSizeY());
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 72, 46);
        laser = new Laser(this, 0);
        gm.getObjects().add(0, laser);
        
        fowardsAccel = 4;
        backwardsAccel = 4;
        lateralAccel = 4;
        
        //hay que pulir
        rotationSpeed = 0.06;
        rotationTolerance = 0.027;
        
        energyCost[0] = 1.25;
        energyCost[1] = 0.3;
        energyCost[2] = 0.3;
        energyCost[3] = 0.3;
        
        cdValues[0] = 0.17;
        cdValues[1] = 5;
        cdValues[2] = 0.25;
        cdValues[3] = 0;
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            isActive[0] = true;
        } else {
            if (isActive[0]) cds[0] = cdValues[0];
            isActive[0] = false;
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
