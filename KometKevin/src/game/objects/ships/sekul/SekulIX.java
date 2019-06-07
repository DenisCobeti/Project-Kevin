package game.objects.ships.sekul;

import engine2D.GameContainer;
import game.GameManager;
import game.colliders.BoxCollider;
import game.objects.Player;
import game.objects.ships.Projectile;
import game.objects.ships.Ships;
import gfx.Image;
import gfx.ImageTile;

/**
 *
 * @author
 */
public class SekulIX extends Player {
    private static final int FIRE1_SPEED = 10;
    private static final int FIRE2_SPEED = 80;
    
    private Image artillery = new Image("/projectiles/fire.png");
    
    private DetonationWave wave;
    private Mining mining;
    private RepulsorWave repulsor;
	
    public SekulIX(int x, int y, GameManager gm) {
        super(x, y);
        tag = "sekul";
        id = 3;
        color = 0xff5cff52;
		
        collider = new BoxCollider(this, 100, 48);
	image = new ImageTile(Ships.Sekul.getSprite(),Ships.Sekul.getSizeX(),
                                                       Ships.Sekul.getSizeY());
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
		
        //wave = new DetonationWave(this,250,1,20,1);
        //gm.getObjects().add(wave);
        
        mining = new Mining(this,4,gm);
        
        //repulsor = new RepulsorWave(this, 1);
        //gm.getObjects().add(repulsor);
        
        fowardsAccel = 2.9;
        backwardsAccel = 1.9;
        lateralAccel = 1.9;
        rotationSpeed = 0.015;
        
        cdValues[0] = 0.25;
        cdValues[1] = 2.5;
        cdValues[2] = 2.8;
        cdValues[3] = 1.25;
        
        /*
        energyCost[0] = 0.2;
        energyCost[1] = 0.5;
        energyCost[2] = 0.1;
        energyCost[3] = 0.3;
        */
        
        energyCost[0] = 0;
        energyCost[1] = 0;
        energyCost[2] = 0;
        energyCost[3] = 0;
        
        healthPoints = 10;
        maxHealthPoints = 10;
    }
    
    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {  
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 && energyPoints - energyCost[0] >= -55550) {
            Projectile fire = new Projectile((int)center.x, (int)center.y, artillery);
            fire.setVelocity(velocity.getAdded(aiming.getMultiplied(FIRE1_SPEED)));
            fire.setAiming(aiming.clone());
            gm.getObjects().add(0,fire);
            cds[0] = cdValues[0];
        }
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 && energyPoints - energyCost[1] >= -55550 ) {
            wave = new DetonationWave(this,8,20,1);
            gm.getObjects().add(wave);
            cds[1] = cdValues[1];
            wave.activate();
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 && energyPoints - energyCost[2] >= -55550 ) {
            mining.activate();
            cds[2] = cdValues[2];
        }
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 && energyPoints - energyCost[3] >= -55550 ) {
            repulsor = new RepulsorWave(this, 1);
            gm.getObjects().add(repulsor);
            cds[3] = cdValues[3];
            repulsor.activate();
        }  
    }
    
}
