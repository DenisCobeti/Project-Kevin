package game.objects.ships.sekul;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 *
 * @author 
 */
 
public class Mine extends GameObject {
    
    private boolean active = false;
    
    private DetonationWave wave;
    private int explosionRange;
    private Mining mineManager;
    private boolean exploding;
    private int damage;
    private float anim;
    
    public Mine(Mining mineManager, int explosionRange){
        this.mineManager = mineManager;
        this.explosionRange = explosionRange;
        wave = new DetonationWave(this, explosionRange,10,0,1);
        damage = 1;
        exploding = false;
        anim = 0;
        
        image = new ImageTile("/projectiles/load.png",60,60);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        position = mineManager.getOwner().getPosition().clone();
        center = mineManager.getOwner().getCenter().clone();
        aiming = mineManager.getOwner().getAiming().clone();
        
        //Se puede verificar el angulo del jugador y posicionar la mina detras de la nave
        //position.subtract(new Vector2(0,0));
        this.collider = new CircleCollider(this,68);
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
    }
    
    @Override 
    public void update(GameContainer gc, GameManager gm, float dt){
        while(!collisions.empty() && !exploding) {
            effect(collisions.pop());
            exploding = true;
            System.out.println("Pum");
        }
        if(exploding){
            anim = (anim + dt * 4) % 6;
            System.out.println(anim);
            if (anim>5){
                exploding = false;
                this.setHealthPoints(0);
            }
        }
    }
    
    @Override
    public void effect(GameObject go){
        go.setHealthPoints(go.getHealthPoints() - damage);
        mineManager.destroyMine(this);
        this.dispose = true;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if(!exploding){
            r.drawRotatedImageTile((ImageTile)image, (int)position.x - 8, (int)position.y - 36, (int)0, 0, aiming.getAngle());
        }else{
            r.drawRotatedImageTile((ImageTile)image, (int)position.x - 8, (int)position.y - 36, (int)anim, 0, aiming.getAngle());
        }
        
    }
    
}
