package game.objects.ships.hammer;

import game.GameManager;
import game.colliders.BoxCollider;
import game.objects.Player;
import game.objects.ships.Projectile;
import game.objects.ships.Ships;
import gfx.Image;
import gfx.ImageTile;

/**
 * Nave de tipo HammerHead
 * H: 111, 11   112, 52     rot 0.015
 * @author ProjectKevin
 */
public class HammerHead extends Player {
    private static final int FIRE1_SPEED = 14;
    private static final int FIRE2_SPEED = 14;
    private static final int ABILITY1_SPEED = 14;
    
    private Image artillery = new Image("/projectiles/fire.png");
    
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
        
        fire1Cd = 0.25;
        fire2Cd = 0.25;
        ability1Cd = 0.25;
        ability2Cd = 0;
    }

    @Override
    public double fire1(GameManager gm) {
        Projectile fire = new Projectile((int)center.x, (int)center.y, artillery);
        fire.setVelocity(velocity.getAdded(aiming.getMultiplied(FIRE1_SPEED)));
        fire.setAiming(aiming.clone());
        gm.getObjects().add(0,fire);
        return fire1Cd;
    }

    @Override
    public double fire2(GameManager gm) {
        // TODO: Se activa el flakswarm
        return fire2Cd;
    }

    @Override
    public double hability1(GameManager gm) {
        // TODO: se lanzan las naves
        return ability1Cd;
    }

    @Override
    public double hability2(GameManager gm) {
        return shield.activate(ability2Cd);
    }
}
