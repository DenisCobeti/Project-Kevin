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
 * @author Ramiro
 */
 
public class Mine extends GameObject {
    
    
    private boolean active = false;
    
    private int explosionRange;
    private Mining mineManager;
    private boolean exploding;
    private int damage;
    private float anim;
    
    public Mine(Mining mineManager, int explosionRange){
        this.mineManager = mineManager;
        this.explosionRange = explosionRange;
        
        damage = 10;
        exploding = false;
        anim = 0;
        
        image = new ImageTile("/projectiles/load.png",60,60);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        
        position = mineManager.getOwner().getPosition().clone();
        center = mineManager.getOwner().getCenter().clone();
        aiming = mineManager.getOwner().getAiming().clone();
        
        this.collider = new CircleCollider(this,38);
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        
        healthPoints = 1000;
        
    }
    
    @Override 
    public void update(GameContainer gc, GameManager gm, float dt){
        while(!collisions.empty() && !exploding) {
            exploding = true;
        }
        while(!collisions.empty() && exploding && anim>=1.2) {
            effect(collisions.pop());
        }
        
        if(exploding){
            anim = (anim + dt * 4) % 6;
            if (anim>5){
                exploding = false;
                mineManager.destroyMine(this);
                this.setHealthPoints(0);
            }
        }
    }
    
    public void activate(){
        exploding = true;
    }
    
    @Override
    public void effect(GameObject go){
        go.setHealthPoints(go.getHealthPoints() - damage);
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
