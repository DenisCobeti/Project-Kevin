package game.objects.ships.aphelion;

import audio.SoundClip;
import engine2D.GameContainer;
import game.GameManager;
import game.Vector2;
import game.colliders.BoxCollider;
import game.objects.Player;
import game.objects.ships.Ships;
import gfx.ImageTile;
import java.io.IOException;

/**
 * Segunda nave del juego. Agilidad.
 * 
 * @author Denis
 */
public class Aphelion extends Player {
    public static final int FIRES_LONG = 28;
    public static final double FIRES_ANGLE = 2.4;
    
    private static final int FIRE2_SPEED = 14;
    
    private static final int ABIL2_JUMP = 210;
    
    private ImageTile bomb = new ImageTile("/projectiles/load.png",60,60);
    private SoundClip laserSound;
    private SoundClip bombSound;
    private SoundClip tpSound;
    private SoundClip demonSound;
    
    private Load load;
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
        try {
            tpSound = new SoundClip("./sfx/aphelion/tp.ogg");
            laserSound = new SoundClip("./sfx/aphelion/laser.ogg");
            bombSound = new SoundClip("./sfx/aphelion/explosion.ogg");
        } catch (IOException ex) {}
        
        collider = new BoxCollider(this, 72, 46);
        laser = new Laser(this, 0);
        gm.getObjects().add(0, laser);
        
        fowardsAccel = 4;
        backwardsAccel = 4;
        lateralAccel = 4;
        maxHealthPoints = Ships.Aphelion.getHealth();
        healthPoints = maxHealthPoints;
        
        //hay que pulir
        rotationSpeed = 0.06;
        rotationTolerance = 0.026;
        
        energyCost[0] = 1.25;
        energyCost[1] = 0.3;
        energyCost[2] = 0.3;
        energyCost[3] = 0.3;
        
        cdValues[0] = 0.5;
        cdValues[1] = 3;
        cdValues[2] = 6;
        cdValues[3] = 2;
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {
        // Primera Habilidad
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            isActive[0] = true;
        } else {
            if (isActive[0]) cds[0] = cdValues[0];
            isActive[0] = false;
        }
        // Segunda Habilidad
        if(gc.getInput().isButtonDown(gc.getConfig().getSecondaryFire()) && cds[1] <=0 && !isActive[1]) {
            Vector2 spawn = Vector2.toCartesian(FIRES_LONG, aiming.getAngle() + FIRES_ANGLE);
            spawn.add(center);

            load = new Load((int)spawn.x, (int)spawn.y, bomb);

            load.setVelocity(velocity.getAdded(Vector2.toCartesian(FIRE2_SPEED, aiming.getAngle())));            
            load.setAiming(load.getVelocity().getNormalized());    
            gm.getObjects().add(0,load);
            
            isActive[1] = true;
        }
        if(gc.getInput().isButtonUp(gc.getConfig().getSecondaryFire())) {
            if (isActive[1]){
                load.setDetonated(true);
                bombSound.play();
                load = null;
                isActive[1] = false;
                cds[1] = cdValues[1];
            }
        }
        
        // Tercera Habilidad
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[1] = 0;
            cds[3] = 0;
            energyPoints = 1;
            cds[2] = cdValues[2];
        }
        // Cuarta Habilidad
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            tpSound.play();
            position.add(aiming.getMultiplied(ABIL2_JUMP));
            center.set(position.x + width/2, position.y + height/2);
            cds[3] = cdValues[3];
        }  
    }
}
