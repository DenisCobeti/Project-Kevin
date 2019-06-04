package game.objects.ships.aphelion;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 * @author Denis Florin Cobeti
 */
public class Load extends GameObject{ 
    private boolean detonated = false;
    
    private ImageTile image; 
    private double anim;
    
    private double timer = 0.4;
    private double damage = 0.2;
    
    public Load(int x, int y, ImageTile image) {
        this.tag = "projectile";
        this.image = image;
        
        this.width = image.getTileH();
        this.height = image.getTileH();
        this.anim = 0;

        this.position = new Vector2(x - width/2, y - height/2);
        this.center = new Vector2(x, y);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        this.collider = new CircleCollider(this, width/2);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if(!detonated) {
            position.add(velocity);
            center.set(position.x + width/2, position.y + height/2);
            aiming.set(velocity.getNormalized());
        }
        while(!collisions.empty()) {
            if (detonated) effect(collisions.pop());
            else collisions.pop();
        }
        if (detonated) {
            anim += dt * 60;
            if (anim >= (image.getW() / width)) dispose = true;
        }
        timer -= dt;
    }
    
    @Override
    public boolean isDead() {
        return dispose;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile(image, (int)position.x, (int)position.y, (int)anim, 0, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }

    public void setDetonated(boolean detonated) {
        this.detonated = detonated;
    }   
}
