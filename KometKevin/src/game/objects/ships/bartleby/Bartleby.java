package game.objects.ships.bartleby;

import engine2D.GameContainer;
import game.GameManager;
import game.Vector2;
import game.colliders.BoxCollider;
import game.objects.CollisionCodes;
import game.objects.Player;
import game.objects.ships.Projectile;
import game.objects.ships.Ships;
import gfx.Image;
import gfx.ImageTile;

/**
 *
 * @author
 */
public class Bartleby extends Player {
    private static final int FIRE1_SPEED = 14;
    private static final int FIRE1_LONG = 37;
    
    private static final int FIRE2_SPEED = 14;
    private static final int FIRE2_LONG = 28;
    private static final double FIRE2_ANGLE = 2.4;
    private static final double MAX_FLAK_ANGLE = Math.PI / 4;
    private static final double FLAK_RATE = MAX_FLAK_ANGLE / 6;
    private static Vector2 otherVelocity;
    
    private Image artillery = new Image("/projectiles/fire.png");
    private ImageTile littleBoy = new ImageTile("/projectiles/nuke.png",170,170);
 
    public Bartleby(int x, int y, GameManager gm) {
        super(x, y);
        tag = "bartleby";
        id = 2;
        color = 0xffffffff;
        
        image = new ImageTile(Ships.Bartleby.getSprite(),Ships.Bartleby.getSizeX(),
                                Ships.Bartleby.getSizeY());
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        collider = new BoxCollider(this, 48, 30);
        
        fowardsAccel = 2.9;
        backwardsAccel = 1.9;
        lateralAccel = 1.1;
        
        healthPoints = 5;
        maxHealthPoints = 5;
        
        rotationSpeed = 0.055;
        rotationTolerance = 0.01;
        
        energyCost[0] = 0.2;
        energyCost[1] = 0.4;
        energyCost[2] = 2;
        energyCost[3] = 2;
        
        cdValues[0] = 0.17;
        cdValues[1] = 0.2;
        cdValues[2] = 10.00;
        cdValues[3] = 5;
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {
        //Arma principal
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 && energyPoints - energyCost[0] >= 0) {
            Vector2 spawn = Vector2.toCartesian(FIRE1_LONG, aiming.getAngle());
            spawn.add(center);
            Projectile fire = new Projectile((int)spawn.x, (int)spawn.y, artillery);
            fire.setVelocity(velocity.getAdded(aiming.getMultiplied(FIRE1_SPEED)));
            fire.setAiming(aiming.clone());
            gm.getObjects().add(0,fire);
            energyPoints -= energyCost[0];
            cds[0] = cdValues[0];
        }
        //Arma secundaria
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 && energyPoints - energyCost[1] >= 0 ) {  
            Vector2 spawn = Vector2.toCartesian(FIRE1_LONG, aiming.getAngle());
            spawn.add(center);
            Nuke fatMan = new Nuke((int)spawn.x, (int)spawn.y,littleBoy);
            fatMan.setVelocity(velocity.getAdded(aiming.getMultiplied(FIRE1_SPEED)));
            fatMan.setAiming(aiming.clone());
            gm.getObjects().add(0,fatMan);
            energyPoints -= energyCost[1];    
            cds[1] = cdValues[1];
        }
        //Habilidad 1 Q Disparo
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability1()) && cds[2] <=0 && energyPoints - energyCost[2] >= 0) {
            isActive[2]=!isActive[2];
            
            if(isActive[2]){
                animX = 1;
                collides=0; 
            }else{
                animX = 0;
                collides= CollisionCodes.TEAM1_COL.getValue();;
                cds[2] = cdValues[2];
            }
        }
        if (isActive[2]) {
            energyPoints -= energyCost[2]*dt;
            if(energyPoints <= 0){
                animX = 0;
                isActive[2]=false;
                collides= CollisionCodes.TEAM1_COL.getValue();;
                cds[2] = cdValues[2];
            }
         }
        //Habilidad 2 E
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 && energyPoints - energyCost[3] >= 0 ) {
            isActive[3]=!isActive[3];
            otherVelocity=this.getVelocity();
            this.velocity.divide(5.0);
        } 
        if (isActive[3]) {
            energyPoints-= energyCost[3]*dt;
            healthPoints+=dt*energyCost[3]/2;
            if(energyPoints <= 0){
                isActive[3]=false;
                collides= CollisionCodes.TEAM1_COL.getValue();
                velocity=otherVelocity.getMultiplied(2.0);
                cds[3] = cdValues[3];
            }

         }
    }
    
}
