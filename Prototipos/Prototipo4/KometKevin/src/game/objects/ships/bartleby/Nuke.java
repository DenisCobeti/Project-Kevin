package game.objects.ships.bartleby;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 * 
 * @author Sergio
 */
public class Nuke extends GameObject{ 
    private int radius = 4;
    private int explosionRadius = width;

    private boolean detonated = false;
    
    private ImageTile image; 
    private double anim;
    
    private double damage = 1000;
    
    public Nuke(int x, int y, ImageTile image) {
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
        collides = CollisionCodes.GRAVPOOL_COL.getValue();//0b10111111100;
        this.collider = new CircleCollider(this, radius);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if(!detonated){
            position.add(velocity);
            center.set(position.x + width/2, position.y + height/2);
            aiming.set(velocity.getNormalized());
        }
        while(!collisions.empty()) {
            if (detonated) effect(collisions.pop());
            else collisions.pop();
        }
        if (healthPoints <= 0 && !detonated) {
            detonated = true;
            ((CircleCollider)collider).setRadius(explosionRadius);
        }
        if (detonated) {
            anim += dt*20;
            if (anim >= (image.getW() / width)) dispose = true;
        }
    }
    
    @Override
    public boolean isDead() {
        return dispose;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        //r.drawFillCircle((int)position.x, (int)position.y, explosionRadius, 0xffff0000);
        r.drawRotatedImageTile(image, (int)position.x, (int)position.y, (int)anim, 0, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
}
