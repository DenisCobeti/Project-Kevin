package game.objects.ships.hammer;

import engine2D.GameContainer;
import game.GameManager;
import game.Vector2;
import game.colliders.BoxCollider;
import game.objects.Player;
import game.objects.ships.Projectile;
import game.objects.ships.Ships;
import gfx.Image;
import gfx.ImageTile;

/**
 * Nave de tipo HammerHead
 * @author ProjectKevin
 */
public class HammerHead extends Player {
    private static final int FIRE1_SPEED = 14;
    private static final int FIRE1_LONG = 37;
    private static final double FIRE1_ANGLE = 0.52;
    
    private static final int FIRE2_SPEED = 14;
    private static final int FIRE2_LONG = 37;
    private static final double FIRE2_ANGLE = 2;
    
    private static final int ABILITY1_SPEED = 14;
    
    private Image artillery = new Image("/projectiles/fire.png");
    
    private int dual = 1;
    
    private Shield shield;
    private FlakSwarm swarm;
       
    public HammerHead(int x, int y, GameManager gm) {
        super(x, y);
        image = new ImageTile(Ships.Hammer.getSprite(),Ships.Hammer.getSizeX(),
                                                       Ships.Hammer.getSizeY());
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 112, 52);
        shield = new Shield(this);
        gm.getObjects().add(shield);
        
        fowardsAccel = 2.9;
        backwardsAccel = 1.9;
        lateralAccel = 1.1;
        
        rotationSpeed = 0.015;
        rotationTolerance = 0.01;
        
        cdValues[0] = 0.17;
        cdValues[1] = 5;
        cdValues[2] = 0.25;
        cdValues[3] = 5;
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {  
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            dual *= -1; 
            
            Vector2 spawn = Vector2.toCartesian(FIRE1_LONG, aiming.getAngle() + FIRE1_ANGLE * dual);
            spawn.add(center);
            
            Projectile fire = new Projectile((int)spawn.x, (int)spawn.y, artillery);
            fire.setVelocity(velocity.getAdded(aiming.getMultiplied(FIRE1_SPEED)));
            fire.setAiming(aiming.clone());
            gm.getObjects().add(0,fire);
            cds[0] = cdValues[0];
        }
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 && !isActive[1]) {
            isActive[1] = !isActive[1];
            cds[1] = cdValues[1];
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[2] = cdValues[2];
        }
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            cds[3] = shield.activate(cdValues[3]);
        }  
    }
}
