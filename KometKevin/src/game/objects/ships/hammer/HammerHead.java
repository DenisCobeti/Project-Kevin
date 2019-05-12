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
    private int dual = 1;
    
    private static final int FIRE2_SPEED = 14;
    private static final int FIRE2_LONG = 28;
    private static final double FIRE2_ANGLE = 2.4;
    private static final double MAX_FLAK_ANGLE = Math.PI / 4;
    private static final double FLAK_RATE = MAX_FLAK_ANGLE / 6;
    private double flakCounter = 0;
    private double flakAngle = 0;
    
    private Image artillery = new Image("/projectiles/fire.png");
    private ImageTile flak = new ImageTile("/projectiles/flak.png",28,28);


    private Shield shield;
       
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
        healthPoints = 10;
        
        rotationSpeed = 0.015;
        rotationTolerance = 0.01;
        
        cdValues[0] = 0.17;
        cdValues[1] = 1;//5;
        cdValues[2] = 0.25;
        cdValues[3] = 5;
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {  
        // Primera Habilidad
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
        
        // Segunda Habilidad
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 && !isActive[1]) {
            isActive[1] = true;
            flakCounter = 0;
            flakAngle = 0;
        }
        if (isActive[1]) {
            flakCounter += dt;
            
            if (flakCounter >= MAX_FLAK_ANGLE) {
                isActive[1] = false;
                cds[1] = cdValues[1];
            } else if(flakCounter > (flakAngle * FLAK_RATE) - dt) {
                Vector2 spawn1 = Vector2.toCartesian(FIRE2_LONG, aiming.getAngle() + FIRE2_ANGLE);
                Vector2 spawn2 = Vector2.toCartesian(FIRE2_LONG, aiming.getAngle() + FIRE2_ANGLE * -1);
                spawn1.add(center);
                spawn2.add(center);
                
                Flak fire1 = new Flak((int)spawn1.x, (int)spawn1.y, flak);
                Flak fire2 = new Flak((int)spawn2.x, (int)spawn2.y, flak);
                
                fire1.setVelocity(velocity.getAdded(Vector2.toCartesian(
                        FIRE2_SPEED, aiming.getAngle() + flakAngle * FLAK_RATE)));            
                fire2.setVelocity(velocity.getAdded(Vector2.toCartesian(
                        FIRE2_SPEED, aiming.getAngle() - flakAngle * FLAK_RATE)));
                fire1.setAiming(fire1.getVelocity().getNormalized());    
                fire2.setAiming(fire2.getVelocity().getNormalized());
                gm.getObjects().add(0,fire1);
                gm.getObjects().add(0,fire2);
                flakAngle++;
            }
        }
        
        // Tercera Habilidad
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[2] = cdValues[2];
        }
        
        // Cuarta Habilidad
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            shield.activate(cdValues, isActive, 3);
        }  
    }
}
