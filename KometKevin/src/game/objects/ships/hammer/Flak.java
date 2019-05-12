package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 * Proyectil de la segunda habilidad de la HammerHead.
 * Provoca una explosión al chocar o tras un tiempo.
 * @author
 */
public class Flak extends GameObject{ 
    private int radius = 4;
    private int explosionRadius = 5;

    private boolean detonated = false;
    
    private ImageTile image; 
    private int anim;
    
    private double timer = 0.3;
    private double damage = 1;
    
    public Flak(int x, int y, ImageTile image) {
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
        this.collider = new CircleCollider(this, radius);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
        aiming.set(velocity.getNormalized());
        while(!collisions.empty()) {
            if (detonated) effect(collisions.pop());
            else collisions.pop();
        }
        if (healthPoints < 0 || timer < 0) {
            detonated = true;
            ((CircleCollider)collider).setRadius(width);
        }
        if (detonated) {
            anim += dt * 60;
            if (anim >= (image.getW() / width)) dispose = true;
        }
        timer -= dt;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile(image, (int)position.x, (int)position.y, anim, 0, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
}
